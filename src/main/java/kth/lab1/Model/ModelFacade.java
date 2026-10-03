package kth.lab1.Model;

import kth.lab1.DB.DAO.OrderDAO;
import kth.lab1.DB.DAO.ProductDAO;
import kth.lab1.DB.DAO.UserDAO;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.handlers.OrderHandler;
import kth.lab1.Model.handlers.ProductHandler;
import kth.lab1.Model.handlers.UserHandler;
import kth.lab1.Model.interfaces.OrderRepository;
import kth.lab1.UI.DTO.ProductDTO;
/*
 * This is a entry point in to the Model layer
 * Hides all subsystem handlers from the UI layer
 */
public class ModelFacade {
    private final ProductHandler productHandler = new ProductHandler(new ProductDAO());
    private final UserHandler userHandler = new UserHandler(new UserDAO());
    private final OrderHandler orderHandler = new OrderHandler(new OrderDAO());

    


    public ProductDTO getProductByID(int id) throws DataAccessException{
        try{
        return productHandler.searchProductByID(id).map(p -> new ProductDTO(p.id(), p.name(), "nothing", "unknown", p.price(), p.stockQuantity()))
             .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
             }catch(DataAccessException e){throw new DataAccessException("oops"); }
    }


}
