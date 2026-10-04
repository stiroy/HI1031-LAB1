package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.ProductRepository;
import kth.lab1.Model.records.Product;

import java.util.List;
import java.util.Optional;


public class ProductHandler {
    private final ProductRepository products;

    public ProductHandler(ProductRepository products) {
        this.products = products;
    }

    public List<Product> getProducts() throws DataAccessException {
        return products.retrieveProducts();
    }
    
    public List<Product> searchProduct(String productName) throws DataAccessException{
        return products.searchByName(productName);
    }

    public Optional<Product> searchProductByID(int productID) throws DataAccessException{
        if(productID>0){
        return products.searchByID(productID);
    }else{throw new IllegalArgumentException("Product ID cannot be zero or below");}
    }

    public void addProduct(Product product) throws DataAccessException{
    if(!product.name().isBlank())
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
        if(!product.name().isBlank()){
            products.updateProduct(product);
        }
        else{throw new IllegalArgumentException("Product must have a explict name");}
    }

    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        if(quantity>=0){
            products.updateQuantity(productID, quantity);
        }
        else{throw new IllegalArgumentException("Quantity cannot be negative");}
    }

}
