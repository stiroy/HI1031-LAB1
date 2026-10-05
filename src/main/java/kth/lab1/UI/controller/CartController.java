package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.UI.DTO.Cart;
import kth.lab1.UI.DTO.ProductDTO;

import java.util.List;

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
}