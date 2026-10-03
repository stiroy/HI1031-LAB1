package kth.lab1.Model.handlers;

import kth.lab1.Model.Product;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.ProductRepository;

import java.util.List;
import java.util.Optional;

import kth.lab1.UI.ProductDTO;
import org.postgresql.ds.PGSimpleDataSource;
public class ProductHandler {
    private final ProductRepository products;

    public ProductHandler(ProductRepository products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products.retrieveProducts();
    }
    
    public List<Product> searchProduct(String productName){
        return products.searchByName(productName);
    }

    public Optional<Product> searchProductByID(int productID){
        return products.searchByID(productID);
    }

    public void addProduct(Product product) throws DataAccessException{
        products.addProduct(product); 
    }

    public void removeProduct(int productID) throws DataAccessException{
        products.removeProduct(productID);
    }

    public void updateProduct(Product product) throws DataAccessException{
        products.updateProduct(product);
    }

    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        products.updateQuantity(productID, quantity);
    }

}
