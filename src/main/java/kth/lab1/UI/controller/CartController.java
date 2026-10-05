package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.UI.DTO.Cart;
import kth.lab1.UI.DTO.ProductDTO;
import kth.lab1.UI.DTO.OrderDTO;

import java.util.UUID;// maybe temorary
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class CartController {

    private Cart getOrCreateCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public String handleViewCart(HttpServletRequest request, HttpServletResponse response) {
        getOrCreateCart(request); // Ensure cart exists in session
        request.setAttribute("pageTitle", "Your Shopping Cart");
        return "cart";
    }

    public String handleAddToCart(HttpServletRequest request, HttpServletResponse response) {
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");

        // TODO: database is currently broken solved temporarly
        if (productIdParam != null && !productIdParam.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdParam.trim());
                int quantity = (quantityParam != null && !quantityParam.trim().isEmpty()) 
                                ? Integer.parseInt(quantityParam.trim()) 
                                : 1;

                ProductController pc = new ProductController();
                List<ProductDTO> products = pc.getSampleProducts();
                
                ProductDTO selected = products.stream()
                    .filter(p -> p.id() == productId)
                    .findFirst()
                    .orElse(null);

                if (selected != null) {
                    Cart cart = getOrCreateCart(request);
                    cart.addProduct(selected, quantity);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // reads the page users was on and redirects back
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }

        return "redirect:/app/cart";
    }

    public String handleUpdateCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Cart cart = getOrCreateCart(request);
            cart.updateQuantity(productId, quantity);
        } catch (NumberFormatException ignored) {}

        return "redirect:/app/cart";
    }

    public String handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Cart cart = getOrCreateCart(request);
            cart.removeItem(productId);
        } catch (NumberFormatException ignored) {}

        return "redirect:/app/cart";
    }

    public String handleCheckout(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = getOrCreateCart(request);
    
        // If cart is empty, redirect back to index
        if (cart.getItems().isEmpty()) {
            return "redirect:/app/index";
        }
    
        String customerName = request.getRemoteUser();
        if (customerName == null || customerName.isEmpty()) {
            customerName = "Guest User";
        }
    
        OrderDTO order = new OrderDTO(
            "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            customerName,
            new ArrayList<>(cart.getItems()), // snapshot copy
            cart.getTotalAmount(),
            LocalDateTime.now()
        );
    
        request.setAttribute("completedOrder", order);
        request.setAttribute("pageTitle", "Order Confirmation");
    
        cart.clear();
        return "checkoutSuccess";
    }
}