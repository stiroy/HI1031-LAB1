package kth.lab1.Model;

import kth.lab1.UI.ProductDTO;

public class ProductHandler {

    //private final var dbManager = new DBManager();

    /**
     * Dummy fetch method for testing UI-to-Model communication.
     * Bypasses dbmanager and returns a hardcoded DTO.
     */
    public ProductDTO getItemById(int id) {
        // Return dummy data based on the requested ID
        if (id == 101) {
            return new ProductDTO(101, "Test Webshop Laptop", "great for web browsing", 1299.99, 1);
        } else {
            return new ProductDTO(id, "Generic Test Product", "product", 49.50, 2);
        }
    }
}
