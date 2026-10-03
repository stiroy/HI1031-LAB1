package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.ServletException;

public class SessionController {

    /**
     * Renders the login page or redirects if already logged in.
     */
    public String showLoginForm(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getUserPrincipal() != null) {
            return "redirect:/app/index";
        }
        return "login";
    }

    public String doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        try {
            request.login(user, pass);
            response.sendRedirect(request.getContextPath() + "/protected/home");
            return "dologin";
        
        } catch (ServletException e) {
            response.sendRedirect(request.getContextPath() + "/app/login?error=invalid_credentials");
            return "redircect:/app/login-error";
        }
    }

    /**
     * Destroys the user session and logs out.
     */
    public String handleLogout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.logout(); // Clears Tomcat security principal
        } catch (Exception ignored) {}

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/app/index";
    }
}