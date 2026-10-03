package kth.lab1.UI.controller;

import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductController {

    private final ModelFacade Handler = new ModelFacade();

    public String handleDetail(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException {
        String idParam = req.getParameter("id");
        
        if (idParam == null || idParam.isBlank()) {
            throw new IllegalArgumentException("Product ID parameter 'id' is required.");
        }

        int itemId;
        try {
            itemId = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Product ID format: " + idParam);
        }

        ProductDTO product = Handler.getProductByID(itemId);

        req.setAttribute("product", product);
        return "productDetail"; // Forwards to /WEB-INF/views/productDetail.jsp
    }

    public String handleSearch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String query = req.getParameter("q");
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query 'q' cannot be empty.");
        }
        
        //req.setAttribute("searchResults", productHandler.searchProducts(query));
        return "searchResults";
    }
}