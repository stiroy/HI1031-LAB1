package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.ServletException;

public class SessionController {

    /**
     * Destroys the user session and logs out.
     */
    public String handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.logout(); // Clears Tomcat security principal
        } catch (Exception ignored) {}

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/app/index");
        return null;
    }
}