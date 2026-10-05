package kth.lab1.UI;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Web listener that monitors HTTP session lifecycle events to track the number of active user sessions across the application.
 */
@WebListener
public class SessionCounterListener implements HttpSessionListener {
    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    /**
     * Increments the active session counter when a new HTTP session is created.
     * 
     * @param se the event notification containing the newly created {@link HttpSession}
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions.incrementAndGet();
    }

    /**
     * Increments the active session counter when a new HTTP session is created.
     * 
     * @param se the event notification containing the newly created {@link HttpSession}
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions.decrementAndGet();
    }
    
    /**
     * Retrieves the current count of active HTTP sessions.
     * 
     * @return the total number of active sessions currently tracked by the container
     */
    public static int getActiveSessions() {
        return activeSessions.get();
    }
}