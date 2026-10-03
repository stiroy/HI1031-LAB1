package kth.lab1.UI.controller;

import kth.lab1.DB.DBManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kth.lab1.UI.SystemStatusDTO;

public class StatusController {

    public String handleStatus(HttpServletRequest req, HttpServletResponse resp) {
        boolean connectionEstablished = false;
        String connectionStatusMessage;

        try {
            connectionEstablished = DBManager.getConnection().isValid(2);
            connectionStatusMessage = connectionEstablished ? "CONNECTED" : "CONNECTION INVALID";
        } catch (Exception e) {
            connectionStatusMessage = "CONNECTION FAILED: " + e.getMessage();
            req.getServletContext().log("Database connection check failed", e);
        }

        boolean driverFound = false;
        String driverStatusMessage;
        try {
            Class.forName("org.postgresql.Driver");
            driverFound = true;
            driverStatusMessage = "Loaded (org.postgresql.Driver)";
        } catch (ClassNotFoundException e) {
            driverStatusMessage = "NOT FOUND on Classpath";
        }

        SystemStatusDTO status = new SystemStatusDTO(
            req.getServletContext().getServerInfo(),
            connectionEstablished,
            connectionStatusMessage,
            driverFound,
            driverStatusMessage
        );

        req.setAttribute("status", status);
        return "status"; // Returns /WEB-INF/views/status.jsp
    }
}