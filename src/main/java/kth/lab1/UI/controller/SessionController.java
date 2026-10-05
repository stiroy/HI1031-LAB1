package kth.lab1.UI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.ServletException;

public class SessionController {

    /**
     * Logs out the authenticated user, invalidates the current HTTP session, and redirects to the home page.
     * 
     * @param req  the HTTP servlet request used to end the user session and retrieve the context path
     * @param resp the HTTP servlet response used to send the HTTP redirect
     * @return {@code null} as navigation is handled directly by sending an HTTP response redirect
     * @throws IOException if an input or output error occurs during the redirection process
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