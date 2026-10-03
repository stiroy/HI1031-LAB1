package kth.lab1.Model.interfaces;


import java.util.List;
import java.util.Optional;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.Product;

public interface ProductRepository {

    List<Product> searchByName(String productName) throws DataAccessException;
    List<Product> retrieveProducts() throws DataAccessException; 
    Optional<Product> searchByID(int productID) throws DataAccessException;

    //Employee actions
    void addProduct(Product product) throws DataAccessException;
    void removeProduct(int productID) throws DataAccessException;
    void updateProduct(Product product) throws DataAccessException;
    void updateQuantity(int productID, int quantity) throws DataAccessException;
}