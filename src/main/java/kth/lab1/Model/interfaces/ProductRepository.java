package kth.lab1.Model.interfaces;


import java.util.List;
import java.util.Optional;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.Product;

public interface ProductRepository {
     /**
    * Search product by name query.
     * @param productName name of product to be searched.
     * @return List of product found in query.
     * @throws DataAccessException If the database query fails.
     */   
    List<Product> searchByName(String productName) throws DataAccessException;

    /**
     * Fetches all products from database.
     * @return A list of every product in database.
     * @throws DataAccessException If the database query fails.
     */       
    List<Product> retrieveProducts() throws DataAccessException; 

    /** 
     * Searches database for product with given ID, and returns it if found.
     * @param productID The ID for the product to be found.
     * @return The product found by the query.
     * @throws DataAccessException If the database query fails.
     */       
    Optional<Product> searchByID(int productID) throws DataAccessException;

    /** 
    * Adds a product to the database.
     * @param product Product to be added.
     * @throws DataAccessException If the database query fails.
     */   
    void addProduct(Product product) throws DataAccessException;


 /**
  * Removes a given product from database.
  * @param productID The product to be removed.
  * @throws DataAccessException If the database query fails.
  */   
    void removeProduct(int productID) throws DataAccessException;

 /** 
  * Updates the fields in a given product, persisting changes in database.
  * @param product The product that was changed.
  * @throws DataAccessException If the database query fails.
  */   
    void updateProduct(Product product) throws DataAccessException;

 /**
  * Updates the quantity of a given product.
  * @param productID Product to be updated.
  * @param quantity New quantity for the item.
  * @throws DataAccessException If the database query fails.
  */   
    void updateQuantity(int productID, int quantity) throws DataAccessException;
}