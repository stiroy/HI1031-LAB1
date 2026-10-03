package kth.lab1.UI.controller;

import kth.lab1.UI.SystemStatusDTO;
import kth.lab1.UI.SessionCounterListener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatusController {

    private static final String JNDI_PATH = "java:comp/env/jdbc/postgres";

    public String handleStatus(HttpServletRequest req, HttpServletResponse resp) {
        boolean jndiResolved = false;
        String jndiStatusMessage;
        
        boolean connectionEstablished = false;
        String connectionStatusMessage;
        long queryLatency = -1;

        DataSource ds = null;

        // 1. Isolated JNDI Lookup Test
        try {
            Context initContext = new InitialContext();
            ds = (DataSource) initContext.lookup(JNDI_PATH);

            if (ds != null) {
                jndiResolved = true;
                jndiStatusMessage = "Resolved via Tomcat Container JNDI";
            } else {
                jndiStatusMessage = "JNDI returned null DataSource";
            }
        } catch (Exception e) {
            jndiStatusMessage = "JNDI Lookup Failed: " + e.getMessage();
        }

        // 2. Direct Connection & Latency Ping (Self-Contained)
        if (jndiResolved && ds != null) {
            long startTime = System.currentTimeMillis();

            // Try-with-resources guarantees Connection, Statement, and ResultSet 
            // are unconditionally closed and returned to the pool after execution.
            try (Connection conn = ds.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1")) {

                long endTime = System.currentTimeMillis();

                if (rs.next()) {
                    connectionEstablished = true;
                    connectionStatusMessage = "Pool Connection Active (SELECT 1 Verified)";
                    queryLatency = (endTime - startTime);
                } else {
                    connectionStatusMessage = "Ping Query Returned No Data";
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
            JNDI_PATH,
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