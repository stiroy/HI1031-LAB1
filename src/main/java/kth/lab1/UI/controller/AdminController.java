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

    public String handleAdminHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pageTitle", "Admin Control Center");
        
        // Pass high-level metrics if supported by facade
        request.setAttribute("totalProducts", modelFacade.getProducts().size());
        request.setAttribute("totalUsers", modelFacade.fetchUsers().size());

        return "admin/index"; // Resolves to /WEB-INF/admin/index.jsp
    }

    // List all users
    public String handleListUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("users", modelFacade.fetchUsers());
        request.setAttribute("pageTitle", "Admin - User Management");
        return "adminUsers";
    }

    // Update user role
    public String handleUpdateUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String newRole = request.getParameter("role");

            //TODO
            modelFacade.changeRole(null);
        } catch (Exception ignored) {}

        return "redirect:/admin/adminUsers";
    }

    // Render Admin Catalog Page
    public String handleManageCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("products", modelFacade.getProducts());
        request.setAttribute("pageTitle", "Admin - Catalog Management");
        return "adminCatalog";
    }

    // Handle Add New Product
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

    // Handle Edit Existing Product
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
