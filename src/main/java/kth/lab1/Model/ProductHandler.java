package kth.lab1.Model;

import kth.lab1.UI.ProductDTO;
import kth.lab1.DB.testing.DBManager;
import kth.lab1.Model.exceptions.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

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

    public void updateProduct(Product product) throws DataAccessException{
        products.updateProduct(product);
    }

    public void updateQuantity(int productID, int quantity) throws DataAccessException{
        products.updateQuantity(productID, quantity);
    }


    //private final var dbManager = new DBManager();

    /**
     * Dummy fetch method for testing UI-to-Model communication.
     * Bypasses dbmanager and returns a hardcoded DTO.
     */
    public ProductDTO getProductById(int id) {
        var dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"10.89.0.2"});
        dataSource.setPortNumbers(new int[]{5432});
        dataSource.setDatabaseName("milkyway");
        dataSource.setUser("sol");
        dataSource.setPassword("terra");

        var db = new DBManager(dataSource);

        return db.products().findById(id)
             .map(p -> new ProductDTO(p.id(), p.name(), "nothing", "unknown", p.price(), p.quantity()))
             .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
 
    }

}
