package kth.lab1.UI.controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.UI.DTO.UserDTO;
import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.ProductDTO;

public class AdminController {

    private final ModelFacade modelFacade = new ModelFacade();

    /**
     * Renders the administrative dashboard view and populates it with summary metrics.
     * 
     * @param request  the HTTP servlet request containing client request attributes
     * @param response the HTTP servlet response
     * @return the logical view name resolving to the admin home JSP page
     * @throws Exception if an error occurs while fetching metrics from the model facade
     */
    public String handleAdminHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pageTitle", "Admin Control Center");
        
        // Pass high-level metrics if supported by facade
        request.setAttribute("totalProducts", modelFacade.getProducts().size());
        request.setAttribute("totalUsers", modelFacade.fetchUsers().size());

        return "admin/index"; // Resolves to /WEB-INF/admin/index.jsp
    }

    /**
     * Fetches all registered users and sets the request attributes to display the user management page.
     * 
     * @param request  the HTTP servlet request used to pass user list and page title to the view
     * @param response the HTTP servlet response
     * @return the logical view name for the admin user management page
     * @throws Exception if an error occurs while retrieving user data
     */
    public String handleListUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("users", modelFacade.fetchUsers());
        request.setAttribute("pageTitle", "Admin - User Management");
        return "adminUsers";
    }

    /**
     * Updates an existing user's assigned role based on form submission parameters.
     * 
     * @param request  the HTTP servlet request containing 'userName' and 'role' parameters
     * @param response the HTTP servlet response
     * @return a redirect path string back to the user management view
     * @throws Exception if an unhandled exception propagates
     */
    public String handleUpdateUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        String userNameParam = request.getParameter("userName");
        String newRole = request.getParameter("role");

        if (userNameParam != null && userNameParam.isBlank()) {
            UserDTO userToUpdate = new UserDTO(0, userNameParam, null, newRole.trim());
            modelFacade.changeRole(userToUpdate);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/adminUsers";
    }

    /**
     * Retrieves all product catalog entries and prepares the administration catalog view.
     * 
     * @param request  the HTTP servlet request carrying catalog items and page metadata
     * @param response the HTTP servlet response
     * @return the logical view name for the admin catalog management page
     * @throws Exception if an error occurs while retrieving catalog products
     */
    public String handleManageCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("products", modelFacade.getProducts());
        request.setAttribute("pageTitle", "Admin - Catalog Management");
        return "adminCatalog";
    }

    /**
     * Parses incoming form parameters to create and persist a new product in the catalog.
     * 
     * @param request  the HTTP servlet request containing 'name', 'description', 'category', 'price', and 'stock'
     * @param response the HTTP servlet response
     * @return a redirect path string back to the catalog management view
     */
    public String handleAddProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            ProductDTO newProduct = new ProductDTO(0, name, description, category, price, stock);
            modelFacade.addProduct(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/adminCatalog";
    }

    /**
     * Extracts form data to update properties of an existing product by its unique identifier.
     * 
     * @param request  the HTTP servlet request containing product attributes and 'id'
     * @param response the HTTP servlet response
     * @return a redirect path string back to the catalog management view
     */
    public String handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            ProductDTO updatedProduct = new ProductDTO(id, name, description, category, price, stock);
            modelFacade.updateProduct(updatedProduct);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/adminCatalog";
    }
}
