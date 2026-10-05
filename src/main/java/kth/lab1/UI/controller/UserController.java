package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.OrderDTO;
import java.util.List;

public class UserController {

    private final ModelFacade modelFacade = new ModelFacade();

    /**
     * Retrieves the authenticated user's credentials and prepares the account profile view.
     * 
     * @param request  the HTTP servlet request used to fetch the user principal and set view attributes
     * @param response the HTTP servlet response
     * @return the logical view name for the user profile page, or a redirect URL to the login page if unauthenticated
     */
    public String handleProfile(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getRemoteUser();
        if (username == null) {
            return "redirect:/login.jsp";
        }

        request.setAttribute("username", username);
        request.setAttribute("pageTitle", "My Account Profile");
        return "userProfile";
    }
    
    /**
     * Fetches the order history for the currently logged-in customer and sets the orders attribute for rendering.
     * 
     * @param request  the HTTP servlet request carrying customer authentication metadata and view attributes
     * @param response the HTTP servlet response
     * @return the logical view name for the customer order history page
     * @throws Exception if an error occurs while retrieving order history from the model facade
     */
    public String handleOrderHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        
        List<OrderDTO> orderHistory = modelFacade.viewCustomerOrders(request.getRemoteUser());
        if (orderHistory == null) {
            orderHistory = new java.util.ArrayList<>();
        }
    
        request.setAttribute("orders", orderHistory);
        request.setAttribute("pageTitle", "My Orders");
    
        return "orders"; // Resolves to /WEB-INF/views/orders.jsp
    }
}
