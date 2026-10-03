package kth.lab1.DB.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.DB.DBManager;
import kth.lab1.Model.interfaces.OrderRepository;
import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;
import kth.lab1.Model.records.Product;

public class OrderDAO extends DAO implements OrderRepository {

//Places a new order in database for a given username and their cart of products, each product has a quantity that the customer has ordered
//and a total price is calculated for the order. Reduces quantity in stock.
    public void placeOrder(String username, List<OrderProduct> orderedProducts) throws DataAccessException{
        String failureMsg = "Could not place order for " + username;
        String closeMessage = "Could not close result set for placing order";
        Connection connection = null;
        try{   
            connection = DBManager.getConnection();
            String insertStatment = "INSERT INTO T_orders (customer_username) VALUES (?) RETURNING order_id";
            PreparedStatement ps =connection.prepareStatement(insertStatment);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            int orderId = rs.getInt("order_id");
            String orderSQL = "INSERT INTO T_order_product (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            double totalPrice = 0;
            PreparedStatement orderStatement = connection.prepareStatement(orderSQL);

            for (OrderProduct p : orderedProducts) {
                if (p.quantity() <= 0){
                    throw new IllegalArgumentException("Quantity must be greater than zero");
                }
                Product product = p.product();
                orderStatement.setInt(1, orderId);
                orderStatement.setInt(2, product.id());
                orderStatement.setInt(3, p.quantity());
                orderStatement.setDouble(4, product.price());
                orderStatement.executeUpdate();
                totalPrice += product.price() * p.quantity();

                PreparedStatement stockStmt =connection.prepareStatement("UPDATE T_products SET quantity = quantity - ? WHERE product_id = ? AND quantity >= ?");
                stockStmt.setInt(1, p.quantity());
                stockStmt.setInt(2, product.id());
                stockStmt.setInt(3, p.quantity());
                int stockRows = stockStmt.executeUpdate();
                if (stockRows != 1) {
                handleException(connection, "Insufficient stock for product " + product.id(), null);
            }

            PreparedStatement totalStmt =
            connection.prepareStatement("UPDATE T_orders SET total_price = ? WHERE order_id = ?");
            totalStmt.setDouble(1, totalPrice);
            totalStmt.setInt(2, orderId);

            int updatedRows = totalStmt.executeUpdate();
            closeResultSet(rs, closeMessage);
            if(updatedRows == 0 ){
                handleException(connection, failureMsg, null);
            }
            commit(connection);
            }
        }catch(SQLException | ClassNotFoundException | IllegalArgumentException e){handleException(connection, failureMsg, e);}  
    }

    //Employee packs given orderID
    public void packOrder(String username, int orderID) throws DataAccessException {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
            String sql ="UPDATE T_orders SET order_status = 'PACKED', packed_by = ?, packed_at = CURRENT_TIMESTAMP WHERE order_id = ? AND order_status = 'UNPACKED'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, orderID);
            int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
               handleException(connection, "Order " + orderID + " could not be packed.", null);
            }
            commit(connection);
        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ignored) {}
            }
            throw new DataAccessException("Failed to pack order: ", e);
        }
    }

    public List<CustomerOrder> viewOrders(String customerName) throws DataAccessException{
        Connection connection = null;
        String sql="";
        String closeMessage = "Could not close result set for order fetch";
        List<CustomerOrder> retrievedOrders = new ArrayList<>();
        try{
            connection = DBManager.getConnection();
            sql = "SELECT * FROM V_employee_orders;";
            PreparedStatement ps = connection.prepareStatement(sql);
            //Customer name provided
            if(customerName != null){
                sql = "SELECT * FROM V_employee_orders WHERE customer_username = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, customerName);
            }
            
            ResultSet retrieveSet = ps.executeQuery();
        while (retrieveSet.next()){
            CustomerOrder OrderTableRow = new CustomerOrder(
                retrieveSet.getInt("order_id"),
                retrieveSet.getString("customer_username"),
                retrieveSet.getString("product_name"),
                retrieveSet.getInt("quantity"),
                retrieveSet.getDouble("unit_price"),
                retrieveSet.getString("order_status"),
                retrieveSet.getDouble("total_price")
            );
                retrievedOrders.add(OrderTableRow);
            }
            closeResultSet(retrieveSet, closeMessage);            
        } catch(SQLException | ClassNotFoundException | DataAccessException e ){throw new DataAccessException("Fetch orders query failed: ", e);}
        return retrievedOrders;
    }

   
}
