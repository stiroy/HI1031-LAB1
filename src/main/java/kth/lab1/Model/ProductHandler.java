package kth.lab1.Model;
import java.util.ArrayList;
import kth.lab1.UI.ProductDTO;
import kth.lab1.DB.ProductDAO;
public class ProductHandler {

    //private final var dbManager = new DBManager();
    private final ProductDAO productDAO = new ProductDAO();
    /**
     * Dummy fetch method for testing UI-to-Model communication.
     * Bypasses dbmanager and returns a hardcoded DTO.
     */
    public ProductDTO getItemById(int id) {
        // Return dummy data based on the requested ID
        if (id == 101) {
            return new ProductDTO(101, "Test Webshop Laptop", "great for web browsing", "computers", 1299.99, 1);
        } else {
            return new ProductDTO(id, "Generic Test Product", "product","test", 49.50, 2);
        }
 
    }

    public ArrayList<ProductDTO> getItemByName(String name){
        ArrayList<ProductDTO> products = new ArrayList<>();
        for (kth.lab1.DB.ProductDTO product : productDAO.searchProducts(name)) {
            products.add(new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity()
            ));
        }
        return products;
    }
}
