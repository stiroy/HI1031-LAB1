package kth.lab1.DB.DAO;
import kth.lab1.DB.DBManager;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.ProductRepository;
import kth.lab1.Model.records.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;




//Hanterar Products 
public class ProductDAO extends DAO implements ProductRepository {

private Product mapProduct(ResultSet rs)throws SQLException {

    return new Product(
        rs.getInt("product_id"),
        rs.getString("name"),
        rs.getString("description"),
        rs.getString("category"),
        rs.getDouble("price"),
        rs.getInt("quantity")
    );
}

    //Retrieves all products in product table, could be used to present product catalog
        @Override 
    public List<Product> retrieveProducts() throws DataAccessException{
        List<Product> retrievedProducts = new ArrayList<>();
        String retrieveQuery = "SELECT * FROM T_products ORDER BY T_products.name ASC";
        try (
            Connection connection = DBManager.getConnection();
            Statement st = connection.createStatement();
        ){
            try (ResultSet retrieveSet = st.executeQuery(retrieveQuery);) {
                while (retrieveSet.next()){
                    retrievedProducts.add(mapProduct(retrieveSet));
            }
            }     
        } catch(SQLException e ){throw new DataAccessException("Fetch all products query failed: ", e);}
        return retrievedProducts;
    }

    //Searches Product table for product's name, supports productsearch by either customer or employee, not case sensitive
        @Override
    public List<Product> searchByName(String productName) throws DataAccessException {
        List<Product> searchProductResult = new ArrayList<>();
        String searchProductQuery = "SELECT * FROM T_products WHERE name ILIKE ?";        
        try(
            Connection connection = DBManager.getConnection();

            PreparedStatement ps = connection.prepareStatement(searchProductQuery);
            ){
            ps.setString(1,"%"+ productName + "%");
            try (ResultSet searchResultSet = ps.executeQuery();) {
                while (searchResultSet.next()){
                    searchProductResult.add(mapProduct(searchResultSet));
                }
            }
        }catch(SQLException e ){throw new DataAccessException("Search by name query failed: ", e);}
        return searchProductResult;
    }

    @Override
public Optional<Product> searchByID(int productID) throws DataAccessException {
    String sql ="SELECT * FROM T_products WHERE product_id = ?";
        try (
            Connection connection = DBManager.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql)
            ) {
            ps.setInt(1, productID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapProduct(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) { throw new DataAccessException("Search product by ID query failed", e);}
    }
    //Insert Info about product name, description, category and quantity, price product id is created upon insert by database, for employee use only
        @Override
    public void addProduct(Product product) throws DataAccessException {
        String failureMsg = "Could not insert new product " + product.name();
        String createProductStatement = "INSERT INTO T_products(name, description, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection connection = DBManager.getConnection();

            PreparedStatement ps = connection.prepareStatement(createProductStatement);
        ) {
            ps.setString(1, product.name());
            ps.setString(2, product.description());
            ps.setString(3, product.category());
            ps.setInt(4, product.stockQuantity());
            ps.setDouble(5, product.price());

            int updatedRows = ps.executeUpdate();
            if(updatedRows != 1){
                handleException(connection, failureMsg, null);
            }
            commit(connection);
        } catch(SQLException | DataAccessException e){throw new DataAccessException("Failed to add item "+product.name(),e);}
    }
        @Override
    public void removeProduct(int productID) throws DataAccessException{
        String failureMsg = "Could not remove product " + productID;
        String removeProductStatement = "DELETE FROM T_products WHERE product_id = ?";
        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(removeProductStatement);
        ) {
            ps.setInt(1, productID);
            int updatedRows = ps.executeUpdate();
            if(updatedRows == 0){
                handleException(connection, failureMsg, null);
            }
        commit(connection);
        } catch(SQLException e){throw new DataAccessException(failureMsg, e);}
    }
//Updates information about an products, such as its name, description, category, price
        @Override
    public void updateProduct(Product product)throws DataAccessException{
        String failureMsg = "Could not update product " + product.name();
        String createProductStatement = 
        "UPDATE T_products SET name = ?, description = ?, category = ?, quantity = ?, price = ? WHERE product_id = ?";
        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(createProductStatement);
        ) {
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
        }catch(SQLException | DataAccessException e){throw new DataAccessException(failureMsg, e);}
    }
//updates the quantity of a given product
        @Override
    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        String failureMsg = "Could not update " + productID + " to: " + quantity;
        String updateQuantityStatement = "UPDATE T_products SET quantity = ? WHERE product_id = ?";  
        try(
            Connection connection = DBManager.getConnection();
  
            PreparedStatement ps = connection.prepareStatement(updateQuantityStatement);
            ){
            ps.setInt(1, quantity); 
            ps.setInt(2, productID);

            int updatedRows = ps.executeUpdate();
            if (updatedRows != 1) {
                handleException(connection ,failureMsg, null);
            }
            commit(connection);

        } catch (SQLException e) {throw new DataAccessException(failureMsg,e);}
        
    }
}
