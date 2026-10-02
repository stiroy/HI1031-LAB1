package kth.lab1.DB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;



//Hanterar Products 
public class ProductDAO{

    //Commits current transaction
    public void commit(Connection connection) throws ProductDBException{
        try{
            connection.commit();
        }
        catch(SQLException e){handleException(connection, "Failed to commit", e);}
    }

    //Rollbacks current transaction
    public void rollback(Connection connection){
        try{
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Problem when rollback: " + e.getMessage());
        }
    }

    //Retrieves all products in product table, could be used to present product catalog
    public ArrayList<ProductDTO> retrieveProducts(){
        ArrayList<ProductDTO> retrievedProducts = new ArrayList<>();
        String closeMessage = "Could not close result set for all product fetch";
        try {
            Connection connection = DBManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet retrieveSet = st.executeQuery("SELECT * FROM T_products ORDER BY T_products.name DESC");
            while (retrieveSet.next()){
                ProductDTO product = new ProductDTO(
                    retrieveSet.getInt("id"),
                    retrieveSet.getString("name"),
                    retrieveSet.getString("description"),
                    retrieveSet.getString("category"),
                    retrieveSet.getInt("quantity"),
                    retrieveSet.getDouble("price")
                );
                retrievedProducts.add(product);
            }
            closeResultSet(closeMessage, retrieveSet);            
        } catch(SQLException | ClassNotFoundException | ProductDBException e ){e.printStackTrace();}
        return retrievedProducts;
    }

    //Searches Product table for product's name, supports productsearch by either customer or employee, not case sensitive
    public ArrayList<ProductDTO> searchProducts(String productName) {
        ArrayList<ProductDTO> searchProductResult = new ArrayList<>();
        String closeMessage = "Could not close result set for search product fetch";

        try{
            Connection connection = DBManager.getConnection();
            String searchProductQuery = "SELECT * FROM T_products WHERE name ILIKE ?";
            PreparedStatement ps = connection.prepareStatement(searchProductQuery);
            ps.setString(1,"%"+ productName + "%");
            ResultSet searchResultSet = ps.executeQuery();

            while (searchResultSet.next()){
                ProductDTO product = new ProductDTO(
                    searchResultSet.getInt("id"),
                    searchResultSet.getString("name"),
                    searchResultSet.getString("description"),
                    searchResultSet.getString("category"),
                    searchResultSet.getInt("quantity"),
                    searchResultSet.getDouble("price")    
                );
                searchProductResult.add(product);
            }
            closeResultSet(closeMessage, searchResultSet);
        }catch(SQLException | ClassNotFoundException | ProductDBException e ){e.printStackTrace();}
        return searchProductResult;
    }
    //Insert Info about product name, description, category and quantity, price product id is created upon insert by database, for employee use only
    public void addProduct(ProductDTO productDTO) throws ProductDBException {
        String failureMsg = "Could not insert new product " + productDTO.getName();
        Connection connection = null;
        try{   
        connection = DBManager.getConnection();
        String createProductStatement = "INSERT INTO T_products(name, description, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(createProductStatement);
            ps.setString(1, productDTO.getName());
            ps.setString(2, productDTO.getDescription());
            ps.setString(3, productDTO.getCategory());
            ps.setInt(4, productDTO.getQuantity());
            ps.setDouble(5, productDTO.getPrice());

        int updatedRows = ps.executeUpdate();
        if(updatedRows == 0){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//Updates information about an products, such as its name, description, category, price
    public void updateProduct(ProductDTO productDTO)throws ProductDBException{
        String failureMsg = "Could not update product " + productDTO.getName();
        Connection connection = null;
        try{   
        connection = DBManager.getConnection();
        String createProductStatement = "UPDATE T_products SET name = ?, description = ?, category = ?, quantity = ?, price = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(createProductStatement);
            ps.setString(1, productDTO.getName());
            ps.setString(2, productDTO.getDescription());
            ps.setString(3, productDTO.getCategory());
            ps.setInt(4, productDTO.getQuantity());
            ps.setDouble(5,productDTO.getPrice());
            ps.setInt(6,productDTO.getId());

        int updatedRows = ps.executeUpdate();
        if(updatedRows != 1){
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
//updates the quantity of a given product
    public void updateQuantity(ProductDTO productDTO, int quantity)throws ProductDBException{
        String failureMsg = "Could not update " + productDTO.getName() + " to: " + quantity;
        Connection connection = null;
        try{
        connection = DBManager.getConnection();
        String updateQuantityStatement = "UPDATE T_products SET quantity = ? WHERE id = ?";    
         PreparedStatement ps = connection.prepareStatement(updateQuantityStatement);
        ps.setInt(1, quantity); 
        ps.setString(2, productDTO.getName());

        int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
                handleException(connection ,failureMsg, null);
            }
            commit(connection);
        } catch (SQLException | ClassNotFoundException e) {
            handleException(connection ,failureMsg, e);
        }
    }

    public void placeOrder(ArrayList<ProductDTO> orderedProducts){

    }

    private void handleException(Connection connection, String failureMsg, Exception cause) throws ProductDBException {
        String completeFailureMsg = failureMsg;
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackExc) {
                completeFailureMsg = completeFailureMsg +
                        ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
            }
        }
        throw new ProductDBException(completeFailureMsg, cause);
    }    

    private void closeResultSet(String failureMsg, ResultSet result) throws ProductDBException {
        try {
            result.close();
        } catch (Exception e) {
            throw new ProductDBException(failureMsg + " Could not close result set.", e);
        }
    }    
}
