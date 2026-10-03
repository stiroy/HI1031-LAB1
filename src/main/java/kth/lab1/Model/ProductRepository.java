package kth.lab1.Model;


import java.util.List;
import java.util.Optional;
import kth.lab1.Model.exceptions.DataAccessException;

public interface ProductRepository {

    List<Product> searchByName(String productName);
    List<Product> retrieveProducts();
    Optional<Product> searchByID(int productID);
    void addProduct(Product product) throws DataAccessException;
    void removeProduct(int productID) throws DataAccessException;
    void updateProduct(Product product) throws DataAccessException;
    void updateQuantity(int productID, int quantity) throws DataAccessException;
}