package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.Cart;
import kth.lab1.UI.DTO.ProductDTO;
import kth.lab1.UI.DTO.OrderDTO;

import java.util.UUID;// maybe temorary
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class CartController {

    ModelFacade Handler = new ModelFacade();

    /**
     * Retrieves the existing shopping cart from the HTTP session or creates a new one if it does not exist.
     * 
     * @param request the HTTP servlet request used to access or create the session
     * @return the active {@link Cart} instance stored in the session
     */
    private Cart getOrCreateCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * Prepares the shopping cart view by ensuring a cart session exists and populates request metadata.
     * 
     * @param request  the HTTP servlet request used to pass page title metadata
     * @param response the HTTP servlet response
     * @return the logical view name for the shopping cart page
     */
    public String handleViewCart(HttpServletRequest request, HttpServletResponse response) {
        getOrCreateCart(request); // Ensure cart exists in session
        request.setAttribute("pageTitle", "Your Shopping Cart");
        return "cart";
    }

    /**
     * Adds a specified quantity of a product to the user's shopping cart and redirects back to the previous page or cart view.
     * 
     * @param request  the HTTP servlet request containing 'productId', optional 'quantity', and 'Referer' header
     * @param response the HTTP servlet response
     * @return a redirect path string pointing to the referring URL or the main cart endpoint
     * @throws Exception if an error occurs while fetching product details from the handler
     */
    public String handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");

        if (productIdParam != null && !productIdParam.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdParam.trim());
                int quantity = (quantityParam != null && !quantityParam.trim().isEmpty()) 
                                ? Integer.parseInt(quantityParam.trim()) 
                                : 1;

                ProductDTO selected = Handler.getProductByID(productId);

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

    /**
     * Updates the item quantity for a specific product inside the active session shopping cart.
     * 
     * @param request  the HTTP servlet request containing 'productId' and updated 'quantity' parameters
     * @param response the HTTP servlet response
     * @return a redirect path string back to the shopping cart view
     */
    public String handleUpdateCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Cart cart = getOrCreateCart(request);
            cart.updateQuantity(productId, quantity);
        } catch (NumberFormatException ignored) {}

        return "redirect:/app/cart";
    }

    /**
     * Removes an item entirely from the active session shopping cart based on its product ID.
     * 
     * @param request  the HTTP servlet request containing the 'productId' parameter to remove
     * @param response the HTTP servlet response
     * @return a redirect path string back to the shopping cart view
     */
    public String handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Cart cart = getOrCreateCart(request);
            cart.removeItem(productId);
        } catch (NumberFormatException ignored) {}

        return "redirect:/app/cart";
    }

    /**
     * Processes the checkout operation by creating an order snapshot, persisting it, clearing the session cart, and rendering confirmation.
     * 
     * @param request  the HTTP servlet request carrying user authentication details and target view attributes
     * @param response the HTTP servlet response
     * @return the logical view name for checkout success or a redirect URL if cart is empty or user is unauthenticated
     * @throws Exception if an error occurs while persisting the order
     */
    public String handleCheckout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Cart cart = getOrCreateCart(request);
    
        // If cart is empty, redirect back to index
        if (cart.getItems().isEmpty()) {
            return "redirect:/app/index";
        }
    
        String customerName = request.getRemoteUser();
        if (customerName == null || customerName.isEmpty()) {
            return "redirect:/login"; // send to login
        }
    
        OrderDTO order = new OrderDTO(
            "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            customerName,
            new ArrayList<>(cart.getItems()), // snapshot copy
            cart.getTotalAmount(),
            LocalDateTime.now(),
            "PENDING"
        );
        Handler.placeOrder(order);

        request.setAttribute("completedOrder", order);
        request.setAttribute("pageTitle", "Order Confirmation");
        cart.clear();
        
        return "checkoutSuccess";
    }
}