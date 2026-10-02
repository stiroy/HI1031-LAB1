package kth.lab1.Model;

import kth.lab1.Model.ProductHandler;
import kth.lab1.UI.ProductDTO;
import kth.lab1.DB.ProductDAO;
/*
 * This is a entry point in to the Model layer
 * Hides all subsystem handlers from the UI layer
 */
public class ModelFacade {
    private final ProductHandler productHandler = new ProductHandler(new ProductDAO());

    public ProductDTO getProductById(int id) {
        return productHandler.getProductById(id);
    }
}
