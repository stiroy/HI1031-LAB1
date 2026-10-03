package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeController {

    public String handleHome(HttpServletRequest req, HttpServletResponse resp) {
        // Retrieve current logged-in user if session exists
        HttpSession session = req.getSession(false);
        String currentUser = (session != null && session.getAttribute("user") != null) 
                             ? (String) session.getAttribute("user") 
                             : "Guest";

        req.setAttribute("currentUser", currentUser);
        return "index"; 
    }
}