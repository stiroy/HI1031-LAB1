package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.exceptions.NotFoundException;
import kth.lab1.Model.interfaces.ProductRepository;
import kth.lab1.Model.records.Product;

import java.util.List;


public class ProductHandler {
    private final ProductRepository products;

    public ProductHandler(ProductRepository products) {
        this.products = products;
    }
    /**
     * Fetches all products in database
     *
     * <p>Business rules:
     * <ul>
     *   <li>Products must exist.</li>
     * </ul>
     *
     * @return A list of all products in database
     * @throws NotFoundException If no product is found.
     * @throws DataAccessException If the database operation fails.
     */
    public List<Product> getProducts() throws DataAccessException {
        List<Product> foundProducts =  products.retrieveProducts();
        if(foundProducts.isEmpty()){
            throw new NotFoundException("No products found in inventory");
        }
        return foundProducts;
    }
    /**
     * Searches database for products who's name matches the query, not case sensitive
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     * </ul>
     *
     * @param productName Name of product to be searched.
     * @return All products that match the query.
     * @throws NotFoundException If no product is found. 
     * @throws DataAccessException If the database operation fails.
     */    
    public List<Product> searchProduct(String productName) throws DataAccessException{
        List<Product> foundProducts = products.searchByName(productName);
        if(foundProducts.isEmpty()){
            throw new NotFoundException("No product found with name: "+ productName);
        }
        return foundProducts;
    }
    /**
     * Searches database for products who's name matches the query
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     *   <li>ID must be valid.</li>
     * </ul>
     *
     * @param productID ID of product to be searched.
     * @return The found product.
     * @throws NotFoundException If no product is found. 
     * @throws IllegalArgumentException If the ID is invalid.
     * @throws DataAccessException If the database operation fails.
     */   
    public Product searchProductByID(int productID) throws DataAccessException {
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID cannot be zero or below");
        }
        return products.searchByID(productID)
        .orElseThrow(() -> new NotFoundException("No product found with ID " + productID));
    }

    /**
    * Adds a given product to the database
    *
    * <p>Business rules:
    * <ul>
    *   <li>Product must exist.</li>
    *   <li>The product's fields must be valid.</li>
    * </ul>
    *
    * @param product The product to be added.
    * @throws IllegalArgumentException If fields are invalid. 
    * @throws DataAccessException If the database operation fails.
    */   
    public void addProduct(Product product) throws DataAccessException{
    if(product.name().isBlank() || product.price()<0 || product.stockQuantity()<0){
        throw new IllegalArgumentException("An added product's name must be explict, price and stock must be non-negative");
    }
        products.addProduct(product); 
    }
    /**
     * Removes a given product.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     * </ul>
     *
     * @param productID ID of product to be searched.
     * @throws IllegalArgumentException If no product is found. 
     * @throws DataAccessException If the database operation fails.
     */   
    public void removeProduct(int productID) throws DataAccessException{
    if(productID>0){
        products.removeProduct(productID);
    }else{
        throw new IllegalArgumentException("Product ID cannot be zero or below");
    }
    }
    /**
     * Updates the field of a given product.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     * </ul>
     * @param product ID of product to be searched.
     * @throws IllegalArgumentException If no product is found. 
     * @throws DataAccessException If the database operation fails.
     */   
    public void updateProduct(Product product) throws DataAccessException{
        if(!product.name().isBlank() && product.price()>=0 && product.stockQuantity()>=0){
            products.updateProduct(product);
        }
        else{throw new IllegalArgumentException("A modified product must have a explict name, and a non-negative price and stock");}
    }
    /**
     * Updates the quantity of a given product.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     *   <li>ID must be valid.</li>
     * </ul>
     *
     * @param productID ID of product to be searched.
     * @param quantity Quantity of product.
     * @throws IllegalArgumentException If no product is found. 
     * @throws DataAccessException If the database operation fails.
     */   
    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        if(quantity>=0){
            products.updateQuantity(productID, quantity);
        }
        else{throw new IllegalArgumentException("Quantity cannot be negative");}
    }

}
