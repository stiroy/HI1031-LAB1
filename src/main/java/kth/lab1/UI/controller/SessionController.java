package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionController {

    /**
     * Renders the login page or redirects if already logged in.
     */
    public String showLoginForm(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/app/index";
        }
        return "login";
    }

    /**
     * Processes login submissions.
     */
    public String handleLogin(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Basic credential validation (replace with DB lookup/hashing in production)
        if (username != null && !username.trim().isEmpty() && "password123".equals(password)) {
            // Invalidate old session to prevent Session Fixation attacks
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // Create a new session and store user identity
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username.trim());
            
            return "redirect:/app/index";
        } else {
            req.setAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }
    }

    /**
     * Destroys the user session and logs out.
     */
    public String handleLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/app/index";
    }
}