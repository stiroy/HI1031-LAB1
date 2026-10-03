package kth.lab1.DB;
import kth.lab1.Model.Product;
import kth.lab1.Model.ProductRepository;
import kth.lab1.Model.exceptions.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;




//Hanterar Products 
public class ProductDAO implements ProductRepository {

    //Commits current transaction
    private void commit(Connection connection) throws DataAccessException{
        try{
            connection.commit();
        }
        catch(SQLException e){handleException(connection, "Failed to commit", e);}
    }

    //Rollbacks current transaction
    private void rollback(Connection connection){
        try{
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Problem when rollback: " + e.getMessage());
        }
    }

    //Retrieves all products in product table, could be used to present product catalog
   
    public List<Product> retrieveProducts(){
        List<Product> retrievedProducts = new ArrayList<>();
        String closeMessage = "Could not close result set for all product fetch";
        try {
            Connection connection = DBManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet retrieveSet = st.executeQuery("SELECT * FROM T_products ORDER BY T_products.name DESC");
            while (retrieveSet.next()){
            Product product = new Product(
                retrieveSet.getInt("product_id"),
                retrieveSet.getString("name"),
                retrieveSet.getString("description"),
                retrieveSet.getString("category"),
                retrieveSet.getDouble("price"),
                retrieveSet.getInt("quantity")

            );
                retrievedProducts.add(product);
            }
            closeResultSet(closeMessage, retrieveSet);            
        } catch(SQLException | ClassNotFoundException | DataAccessException e ){e.printStackTrace();}
        return retrievedProducts;
    }

    //Searches Product table for product's name, supports productsearch by either customer or employee, not case sensitive
    public List<Product> searchByName(String productName) {
        List<Product> searchProductResult = new ArrayList<>();
        String closeMessage = "Could not close result set for search product fetch";

        try{
            Connection connection = DBManager.getConnection();
            String searchProductQuery = "SELECT * FROM T_products WHERE name ILIKE ?";
            PreparedStatement ps = connection.prepareStatement(searchProductQuery);
            ps.setString(1,"%"+ productName + "%");
            ResultSet searchResultSet = ps.executeQuery();

            while (searchResultSet.next()){
            Product product = new Product(
                searchResultSet.getInt("product_id"),
                searchResultSet.getString("name"),
                searchResultSet.getString("description"),
                searchResultSet.getString("category"),
                searchResultSet.getDouble("price"),
                searchResultSet.getInt("quantity")
            );
                searchProductResult.add(product);
            }
            closeResultSet(closeMessage, searchResultSet);
        }catch(SQLException | ClassNotFoundException | DataAccessException e ){e.printStackTrace();}
        return searchProductResult;
    }

    public Optional<Product> searchByID(int productID) {
        String closeMessage = "Could not close result set for search product fetch";

        try{
            Connection connection = DBManager.getConnection();
            String searchProductQuery = "SELECT * FROM T_products WHERE product_id = ?";
            PreparedStatement ps = connection.prepareStatement(searchProductQuery);
            ps.setInt(1, productID);
            ResultSet searchResultSet = ps.executeQuery();
            if(searchResultSet.next()){
                Product product = new Product(
                    searchResultSet.getInt("product_id"),
                    searchResultSet.getString("name"),
                    searchResultSet.getString("description"),
                    searchResultSet.getString("category"),
                    searchResultSet.getDouble("price"),
                    searchResultSet.getInt("quantity")
                );
            closeResultSet(closeMessage, searchResultSet);
            return Optional.of(product);
            }
            closeResultSet(closeMessage, searchResultSet);
            return Optional.empty();
            
        }catch(SQLException | ClassNotFoundException | DataAccessException e ){e.printStackTrace();}
        return Optional.empty();
    }
    //Insert Info about product name, description, category and quantity, price product id is created upon insert by database, for employee use only
    public void addProduct(Product product) throws DataAccessException {
        String failureMsg = "Could not insert new product " + product.name();
        Connection connection = null;
        try{   
        connection = DBManager.getConnection();
        String createProductStatement = "INSERT INTO T_products(name, description, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(createProductStatement);
            ps.setString(1, product.name());
            ps.setString(2, product.description());
            ps.setString(3, product.category());
            ps.setInt(4, product.stockQuantity());
            ps.setDouble(5, product.price());

        int updatedRows = ps.executeUpdate();
        if(updatedRows == 0){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }

    public void removeProduct(int productID) throws DataAccessException{
        String failureMsg = "Could not remove product " + productID;
        Connection connection = null;

        try{   
        connection = DBManager.getConnection();
        String removeProductStatement = "DELETE FROM T_products WHERE product_id = ?";
        PreparedStatement ps = connection.prepareStatement(removeProductStatement);
            ps.setInt(1, productID);
        int updatedRows = ps.executeUpdate();
        if(updatedRows == 0){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//Updates information about an products, such as its name, description, category, price
    public void updateProduct(Product product)throws DataAccessException{
        String failureMsg = "Could not update product " + product.name();
        Connection connection = null;
        try{   
        connection = DBManager.getConnection();
        String createProductStatement = 
        "UPDATE T_products SET name = ?, description = ?, category = ?, quantity = ?, price = ? WHERE product_id = ?";
        PreparedStatement ps = connection.prepareStatement(createProductStatement);
            ps.setString(1, product.name());
            ps.setString(2, product.description());
            ps.setString(3, product.category());
            ps.setInt(4, product.stockQuantity());
            ps.setDouble(5, product.price());
            ps.setInt(6, product.id());

        int updatedRows = ps.executeUpdate();
        if(updatedRows != 1){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//updates the quantity of a given product
    public void updateQuantity(int productID, int quantity)throws DataAccessException{
        String failureMsg = "Could not update " + productID + " to: " + quantity;
        Connection connection = null;
        try{
        connection = DBManager.getConnection();
        String updateQuantityStatement = "UPDATE T_products SET quantity = ? WHERE product_id = ?";    
         PreparedStatement ps = connection.prepareStatement(updateQuantityStatement);
        ps.setInt(1, quantity); 
        ps.setInt(2, productID);

        int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
                handleException(connection ,failureMsg, null);
            }
            commit(connection);
        } catch (SQLException | ClassNotFoundException e) {
            handleException(connection ,failureMsg, e);
        }
    }


    private void handleException(Connection connection, String failureMsg, Exception cause) throws DataAccessException {
        String completeFailureMsg = failureMsg;
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackExc) {
                completeFailureMsg = completeFailureMsg +
                        ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
            }
        }
        throw new DataAccessException(completeFailureMsg, cause);
    }    

    private void closeResultSet(String failureMsg, ResultSet result) throws DataAccessException {
        try {
            result.close();
        } catch (Exception e) {
            throw new DataAccessException(failureMsg + " Could not close result set.", e);
        }
    }    
}
