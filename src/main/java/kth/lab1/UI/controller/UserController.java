package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserController {

    public String handleProfile(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        /*if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login.jsp";
        }*/

        Object user = session.getAttribute("user");
        request.setAttribute("pageTitle", "My Account Profile");
        request.setAttribute("accountDetails", user);
        return "userProfile";
    }
}
