package kth.lab1.Model;

import kth.lab1.UI.ProductDTO;
import kth.lab1.DB.ProductDAO;
import kth.lab1.Model.handlers.ProductHandler;
/*
 * This is a entry point in to the Model layer
 * Hides all subsystem handlers from the UI layer
 */
public class ModelFacade {
    private final ProductHandler productHandler = new ProductHandler(new ProductDAO());

    public ProductDTO getProductByID(int id) {
        return productHandler.searchProductByID(id).map(p -> new ProductDTO(p.id(), p.name(), "nothing", "unknown", p.price(), p.stockQuantity()))
             .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }
}
