package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeController {

    public String handleHome(HttpServletRequest request, HttpServletResponse response) {
        // Retrieve current logged-in user if session exists
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/index";
        } else if (request.isUserInRole("EMPLOYEE")) {
            return "redirect:/Employee/index";
        } else {
            // Defaults to CUSTOMER or unauthenticated guest view
            return "index";
        }

    }
}