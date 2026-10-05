package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.OrderDTO;
import java.util.List;

public class UserController {

    private final ModelFacade modelFacade = new ModelFacade();

    public String handleProfile(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getRemoteUser();
        if (username == null) {
            return "redirect:/login.jsp";
        }

        request.setAttribute("username", username);
        request.setAttribute("pageTitle", "My Account Profile");
        return "userProfile";
    }

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
