package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeController {
    
    /**
     * Determines the home page view or redirect target based on the authenticated user's role.
     * 
     * @param request  the HTTP servlet request used to check the user's assigned security roles
     * @param response the HTTP servlet response
     * @return a redirect URL string for administrators or employees, or the default home view for customers and guests
     */
    public String handleHome(HttpServletRequest request, HttpServletResponse response) {
        // Retrieve current logged-in user if session exists
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/index";
        } else if (request.isUserInRole("EMPLOYEE")) {
            return "redirect:/employee/employeeOrders";
        } else {
            // Defaults to CUSTOMER or unauthenticated guest view
            return "index";
        }

    }
}