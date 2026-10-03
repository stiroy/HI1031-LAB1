package kth.lab1.UI.controller;

import kth.lab1.DB.DBManager;
import kth.lab1.UI.SystemStatusDTO;
import kth.lab1.UI.SessionCounterListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class StatusController {

    public String handleStatus(HttpServletRequest req, HttpServletResponse resp) {
        boolean jndiResolved = false;
        String jndiStatusMessage;
        String resourceName = "java:comp/env/jdbc/postgres";
        
        boolean connectionEstablished = false;
        String connectionStatusMessage;
        long queryLatency = -1;

        // 1. Verify JNDI Lookup
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/postgres");
            
            if (ds != null) {
                jndiResolved = true;
                jndiStatusMessage = "Resolved via Tomcat Container JNDI";
            } else {
                jndiStatusMessage = "JNDI returned null DataSource";
            }
        } catch (Exception e) {
            jndiStatusMessage = "JNDI Lookup Failed: " + e.getMessage();
        }

        // 2. Test Connection & Benchmark Latency
        if (jndiResolved) {
            long startTime = System.currentTimeMillis();
            try (Connection conn = DBManager.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                if (conn.isValid(2)) {
                    stmt.executeQuery("SELECT 1"); // Validation Ping
                    long endTime = System.currentTimeMillis();
                    
                    connectionEstablished = true;
                    connectionStatusMessage = "Pool Connection Active (SELECT 1 Verified)";
                    queryLatency = (endTime - startTime);
                } else {
                    connectionStatusMessage = "Connection Invalid on Checkout";
                }
            } catch (Exception e) {
                connectionStatusMessage = "Connection Checkout Failed: " + e.getMessage();
            }
        } else {
            connectionStatusMessage = "Skipped (JNDI Unresolved)";
        }

        // 3. User & Session Metadata
        HttpSession session = req.getSession(false);
        String user = (session != null && session.getAttribute("user") != null)
                      ? (String) session.getAttribute("user")
                      : "Guest";

        SystemStatusDTO status = new SystemStatusDTO(
            req.getServletContext().getServerInfo(),
            jndiResolved,
            jndiStatusMessage,
            resourceName,
            connectionEstablished,
            connectionStatusMessage,
            queryLatency,
            SessionCounterListener.getActiveSessions(),
            user
        );

        req.setAttribute("status", status);
        return "status"; // Maps to /WEB-INF/views/status.jsp
    }
}