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

    public List<Product> getProducts() throws DataAccessException {
        List<Product> foundProducts =  products.retrieveProducts();
        if(foundProducts.isEmpty()){
            throw new NotFoundException("No products found in inventory");
        }
        return foundProducts;
    }
    
    public List<Product> searchProduct(String productName) throws DataAccessException{
        List<Product> foundProducts = products.searchByName(productName);
        if(foundProducts.isEmpty()){
            throw new NotFoundException("No product found with name: "+ productName);
        }
        return foundProducts;
    }

    public Product searchProductByID(int productID) throws DataAccessException {
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID cannot be zero or below");
        }
        return products.searchByID(productID)
        .orElseThrow(() -> new NotFoundException("No product found with ID " + productID));
    }


    public void addProduct(Product product) throws DataAccessException{
    if(product.name().isBlank() || product.price()<0 || product.stockQuantity()<0){
        throw new IllegalArgumentException("An added product's name must be explict, price and stock must be non-negative");
    }
        products.addProduct(product); 
    }

    public void removeProduct(int productID) throws DataAccessException{
    if(productID>0){
        products.removeProduct(productID);
    }else{
        throw new IllegalArgumentException("Product ID cannot be zero or below");
    }
    }

    public void updateProduct(Product product) throws DataAccessException{
        if(!product.name().isBlank() && product.price()>=0 && product.stockQuantity()>=0){
            products.updateProduct(product);
        }
        else{throw new IllegalArgumentException("A modified product must have a explict name, and a non-negative price and stock");}
    }

    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        if(quantity>=0){
            products.updateQuantity(productID, quantity);
        }
        else{throw new IllegalArgumentException("Quantity cannot be negative");}
    }

}
