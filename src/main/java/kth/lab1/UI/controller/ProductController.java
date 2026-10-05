package kth.lab1.UI.controller;

import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors; // ONLY TEMPORARY

public class ProductController {

    private final ModelFacade Handler = new ModelFacade();

    public String handleDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        
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
        request.setAttribute("product", product);

       return "productDetail"; // Forwards to /WEB-INF/views/productDetail.jsp
    }

    public String handleSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String query = request.getParameter("q");
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query 'q' cannot be empty.");
        }
        
        request.setAttribute("searchResults", Handler.getProductByName(query));
        return "searchResults";
    }


    public String handleCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //List<ProductDTO> products = Handler.getProducts();
        List<ProductDTO> products = getSampleProducts();  
        
        // Programmatic search filtering (fallback until DB is operational)
        String searchQuery = request.getParameter("query");
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            String q = searchQuery.trim().toLowerCase();
            products = products.stream()
                .filter(p -> p.name().toLowerCase().contains(q) ||
                             p.category().toLowerCase().contains(q) ||
                             p.description().toLowerCase().contains(q))
                .collect(Collectors.toList());
            
            request.setAttribute("searchQuery", searchQuery.trim());
        }

        request.setAttribute("products", products);
        request.setAttribute("pageTitle", "Product Catalog");
        
        return "catalog";
    }
    // dummy data for testing
    public List<ProductDTO> getSampleProducts() {
        List<ProductDTO> list = new ArrayList<>();
        list.add(new ProductDTO(1, "Mechanical Keyboard", "RGB backlighting with linear switches", "Electronics", 129.99, 15));
        list.add(new ProductDTO(2, "Ergonomic Chair", "High-back mesh chair with lumbar support", "Furniture", 249.50, 4));
        list.add(new ProductDTO(3, "Wireless Mouse", "Ultra-lightweight gaming mouse", "Electronics", 79.95, 0));
        list.add(new ProductDTO(4, "Coffee Mug", "Ceramic 350ml heat-insulated mug", "Kitchenware", 14.99, 42));
        return list;
    }
}