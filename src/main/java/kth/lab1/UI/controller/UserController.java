package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserController {

    public String handleProfile(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getRemoteUser();
        if (username == null) {
            return "redirect:/login.jsp";
        }

        request.setAttribute("username", username);
        request.setAttribute("pageTitle", "My Account Profile");
        return "userProfile";
    }
}
