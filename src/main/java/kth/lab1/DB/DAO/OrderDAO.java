package kth.lab1.DB.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kth.lab1.DB.DBManager;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.OrderRepository;
import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;
import kth.lab1.Model.records.Product;

public class OrderDAO extends DAO implements OrderRepository {
         @Override 
    public void placeOrder(String username, List<OrderProduct> orderedProducts, String orderID) throws DataAccessException{
        String failureMsg = "Could not place order for " + username;
        String insertIDSQL = "INSERT INTO T_orders (customer_username, order_id) VALUES (?, ?)";
        String orderSQL = "INSERT INTO T_order_product (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        String stockSQL = "UPDATE T_products SET quantity = quantity - ? WHERE product_id = ? AND quantity >= ?";
        try(
            Connection connection = DBManager.getConnection();
            PreparedStatement insertOrderStatement = connection.prepareStatement(insertIDSQL);
            PreparedStatement orderStatement = connection.prepareStatement(orderSQL);
            PreparedStatement stockStmt = connection.prepareStatement(stockSQL);
            ){
            insertOrderStatement.setString(1, username);
            insertOrderStatement.setString(2, orderID);
            int rows = insertOrderStatement.executeUpdate();
            if(rows != 1){
                handleException(connection, "Failed to create order: "+orderID, null);
            }
            double totalPrice = 0;
            for (OrderProduct p : orderedProducts) {
                if (p.quantity() <= 0){
                    throw new IllegalArgumentException("Quantity must be greater than zero");
                }
                Product product = p.product();
                stockStmt.setInt(1, p.quantity());
                stockStmt.setInt(2, product.id());
                stockStmt.setInt(3, p.quantity());
                int stockRows = stockStmt.executeUpdate();
                if (stockRows != 1) {
                    handleException(connection, "Insufficient stock for product " + product.id(), null);
                }

                orderStatement.setString(1, orderID);
                orderStatement.setInt(2, product.id());
                orderStatement.setInt(3, p.quantity());
                orderStatement.setDouble(4, product.price());
                int orderRows = orderStatement.executeUpdate();
                if(orderRows != 1){
                    handleException(connection,"Failed to create order item",null);
                }
                totalPrice += product.price() * p.quantity();
            }
            try(
            PreparedStatement totalStmt =
            connection.prepareStatement("UPDATE T_orders SET total_price = ? WHERE order_id = ?");
            ){
                totalStmt.setDouble(1, totalPrice);
                totalStmt.setString(2, orderID);
                int updatedRows = totalStmt.executeUpdate();
                if(updatedRows == 0 ){
                    handleException(connection, failureMsg, null);
                }
            }
        commit(connection);
        }catch(SQLException | IllegalArgumentException e){throw new DataAccessException("Failed to place order: "+orderID,e);}
    }



        @Override 
    public void packOrder(String employeeUsername, String orderID) throws DataAccessException {
         String sql =
         "UPDATE T_orders SET order_status = 'PACKED', packed_by = ?, packed_at = CURRENT_TIMESTAMP WHERE order_id = ? AND order_status = 'PENDING'";
        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, employeeUsername);
            ps.setString(2, orderID);
            int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
               handleException(connection, "Order " + orderID + " could not be packed.", null);
            }
            commit(connection);
        } catch (SQLException e) {throw new DataAccessException("Failed to pack order with id"+orderID,e);}
    }

        @Override 
    public List<CustomerOrder> viewOrders(String customerName) throws DataAccessException{
        List<CustomerOrder> retrievedOrders = new ArrayList<>();
        String orderHeaderSQL = "SELECT order_id, customer_username, total_price, created_at, order_status FROM T_orders ORDER BY created_at DESC";
        String orderProductSQL = 
        "SELECT p.product_id, p.name, p.description, p.category, p.quantity AS stock_quantity, oi.quantity AS ordered_quantity, oi.unit_price FROM T_order_product oi JOIN T_products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";
        if (customerName != null && !customerName.isBlank()) {
            orderHeaderSQL ="SELECT order_id, customer_username, total_price, created_at, order_status FROM T_orders WHERE customer_username = ? ORDER BY created_at DESC";
        }
        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement orderPS = connection.prepareStatement(orderHeaderSQL);
        ) {
            if (customerName != null && !customerName.isBlank()) {
                orderPS.setString(1, customerName);
            }
            try(
                ResultSet retrieveSet = orderPS.executeQuery();
            ){
                while (retrieveSet.next()){
                    List<OrderProduct> items = new ArrayList<>();
                    String currentOrderID = retrieveSet.getString("order_id");
                    try(
                        PreparedStatement orderProductPS = connection.prepareStatement(orderProductSQL)
                    ){
                        orderProductPS.setString(1, currentOrderID);
                        try(
                            ResultSet fetchedProduct = orderProductPS.executeQuery();
                        ){
                            while(fetchedProduct.next()){
                                Product product = new Product(
                                fetchedProduct.getInt("product_id"),
                                fetchedProduct.getString("name"),
                                fetchedProduct.getString("description"), 
                                fetchedProduct.getString("category"),
                                fetchedProduct.getDouble("unit_price"),
                                fetchedProduct.getInt("stock_quantity")
                                );
                                items.add(
                                    new OrderProduct(product, fetchedProduct.getInt("ordered_quantity"), currentOrderID)
                                );
                            }
                            CustomerOrder order = new CustomerOrder(
                                currentOrderID,
                                retrieveSet.getString("customer_username"),
                                items,
                                retrieveSet.getDouble("total_price"),
                                retrieveSet.getTimestamp("created_at").toLocalDateTime(),
                                retrieveSet.getString("order_status")
                            );
                            retrievedOrders.add(order);
                        }
                    }
                }
            }
        }    catch(SQLException e ){throw new DataAccessException("Fetch orders query failed: ", e);}
        return retrievedOrders;
    }  
}
