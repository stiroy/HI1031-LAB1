package kth.lab1.UI.controller;

import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

public class ProductController {

    private final ModelFacade Handler = new ModelFacade();

    /**
     * Parses the request for a product identifier, validates it, and forwards to the product detail view.
     * 
     * @param request  the HTTP servlet request containing the required 'id' parameter
     * @param response the HTTP servlet response
     * @return the logical view name resolving to the product detail page
     * @throws IllegalArgumentException if the 'id' parameter is missing, blank, or not a valid integer
     * @throws Exception                if an error occurs during processing
     */
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

    /**
     * Executes a product search query against the database and sets the result list as a request attribute.
     * 
     * @param request  the HTTP servlet request containing the optional search query parameter 'q'
     * @param response the HTTP servlet response
     * @return the logical view name for the search results page
     * @throws Exception if an error occurs while fetching product search results
     */
    public String handleSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
            String query = request.getParameter("q");
            if (query == null || query.isBlank()) {
                request.setAttribute("searchQuery", "");
                return "searchResults";
            }
            
            String trimmedQuery = query.trim();
            // Execute SQL search directly via DB Handler
            List<ProductDTO> results = Handler.getProductByName(trimmedQuery);
            
            request.setAttribute("searchResults", results);
            request.setAttribute("searchQuery", trimmedQuery);
            return "searchResults";
    }

    /**
     * Displays the general product catalog or filtered search results depending on the presence of a search query.
     * 
     * @param request  the HTTP servlet request containing the optional 'query' parameter
     * @param response the HTTP servlet response
     * @return the logical view name for the catalog page
     * @throws Exception if an error occurs while retrieving products from the database
     */
    public String handleCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String searchQuery = request.getParameter("query");
            List<ProductDTO> products;
        
            // Direct DB Delegation: Search DB if query exists, otherwise pull full catalog
            if (searchQuery != null && !searchQuery.isBlank()) {
                String q = searchQuery.trim();
                products = Handler.getProductByName(q);
                request.setAttribute("searchQuery", q);
            } else {
                products = Handler.getProducts();
            }
        
            request.setAttribute("products", products);
            request.setAttribute("pageTitle", "Product Catalog");
            
            return "catalog";
    }

    /**
     * Generates a collection of mock product items for unit testing and UI debugging purposes.
     * 
     * @return a {@link List} of sample {@link ProductDTO} instances
     */
    public List<ProductDTO> getSampleProducts() {
        List<ProductDTO> list = new ArrayList<>();
        list.add(new ProductDTO(1, "Mechanical Keyboard", "RGB backlighting with linear switches", "Electronics", 129.99, 15));
        list.add(new ProductDTO(2, "Ergonomic Chair", "High-back mesh chair with lumbar support", "Furniture", 249.50, 4));
        list.add(new ProductDTO(3, "Wireless Mouse", "Ultra-lightweight gaming mouse", "Electronics", 79.95, 0));
        list.add(new ProductDTO(4, "Coffee Mug", "Ceramic 350ml heat-insulated mug", "Kitchenware", 14.99, 42));

        return list;
    }
}