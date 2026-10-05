package kth.lab1.UI.controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.UI.DTO.UserDTO;
import kth.lab1.UI.DTO.ProductDTO;

public class AdminController {

    private List<UserDTO> getUsers(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<UserDTO> users = (List<UserDTO>) session.getAttribute("mockUsers");
        
        // dummy data
        if (users == null) {
            users = new ArrayList<>();
            users.add(new UserDTO(1, "john_doe", "john@example.com", "CUSTOMER"));
            users.add(new UserDTO(2, "admin_user", "admin@store.se", "ADMIN"));
            users.add(new UserDTO(3, "warehouse_worker", "warehouse@store.se", "WAREHOUSE"));
            session.setAttribute("mockUsers", users);
        }
        return users;
    }

    // List all users
    public String handleListUsers(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", getUsers(request));
        request.setAttribute("pageTitle", "Admin - User Management");
        return "adminUsers";
    }

    // Update user role
    public String handleUpdateUserRole(HttpServletRequest request, HttpServletResponse response) {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String newRole = request.getParameter("role");

            List<UserDTO> users = getUsers(request);
            for (UserDTO u : users) {
                if (u.getId() == userId) {
                    u.setRole(newRole);
                    break;
                }
            }
        } catch (Exception ignored) {}

        return "redirect:/app/adminUsers";
    }

    // Helper to get or initialize shared sample products in ServletContext
    private List<ProductDTO> getCatalog(HttpServletRequest request) {
        List<ProductDTO> products = (List<ProductDTO>) request.getServletContext().getAttribute("globalProducts");
        if (products == null) {
            products = new ArrayList<>();
            products.add(new ProductDTO(1, "Mechanical Keyboard", "RGB tactile switch keyboard", "Electronics", 99.99, 15));
            products.add(new ProductDTO(2, "Wireless Mouse", "Ergonomic 2.4GHz optical mouse", "Electronics", 29.99, 30));
            products.add(new ProductDTO(3, "Coffee Mug", "Ceramic 350ml desk mug", "Home", 12.50, 50));
            request.getServletContext().setAttribute("globalProducts", products);
        }
        return products;
    }

    // Render Admin Catalog Page
    public String handleManageCatalog(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("products", getCatalog(request));
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

            List<ProductDTO> products = getCatalog(request);
            int newId = products.stream().mapToInt(ProductDTO::getId).max().orElse(0) + 1;

            products.add(new ProductDTO(newId, name, description, category, price, stock));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/app/adminCatalog";
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

            List<ProductDTO> products = getCatalog(request);
            for (ProductDTO p : products) {
                if (p.getId() == id) {
                    p.setName(name);
                    p.setDescription(description);
                    p.setCategory(category);
                    p.setPrice(price);
                    p.setQuantity(stock);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/app/adminCatalog";
    }
}
