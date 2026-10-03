package kth.lab1.UI;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class SystemStatusDTO {
    private final String deploymentStatus = "ONLINE";
    private final Date serverTime = new Date();
    private final String javaVersion = System.getProperty("java.version");
    private final String serverInfo;

    // Database & JNDI Diagnostics
    private final boolean jndiResolved;
    private final String jndiStatusMessage;
    private final String jndiResourceName;
    private final boolean connectionEstablished;
    private final String connectionStatusMessage;
    private final long dbQueryLatencyMs;

    // Server Health Metrics
    private final String serverUptime;
    private final int activeSessions;
    private final String loggedInUser;
    private final long freeMemoryMb = Runtime.getRuntime().freeMemory() / (1024 * 1024);
    private final long totalMemoryMb = Runtime.getRuntime().totalMemory() / (1024 * 1024);

    public SystemStatusDTO(String serverInfo, boolean jndiResolved, String jndiStatusMessage, 
                           String jndiResourceName, boolean connectionEstablished, 
                           String connectionStatusMessage, long dbQueryLatencyMs, 
                           int activeSessions, String loggedInUser) {
        this.serverInfo = serverInfo;
        this.jndiResolved = jndiResolved;
        this.jndiStatusMessage = jndiStatusMessage;
        this.jndiResourceName = jndiResourceName;
        this.connectionEstablished = connectionEstablished;
        this.connectionStatusMessage = connectionStatusMessage;
        this.dbQueryLatencyMs = dbQueryLatencyMs;
        this.activeSessions = activeSessions;
        this.loggedInUser = loggedInUser != null ? loggedInUser : "Guest";
        this.serverUptime = calculateUptime();
    }

    private String calculateUptime() {
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = uptimeMs / 1000 % 60;
        long minutes = uptimeMs / (1000 * 60) % 60;
        long hours = uptimeMs / (1000 * 60 * 60) % 24;
        long days = uptimeMs / (1000 * 60 * 60 * 24);
        return String.format("%dd %dh %dm %ds", days, hours, minutes, seconds);
    }

    // Getters
    public String getDeploymentStatus() { return deploymentStatus; }
    public Date getServerTime() { return serverTime; }
    public String getJavaVersion() { return javaVersion; }
    public String getServerInfo() { return serverInfo; }
    public boolean isJndiResolved() { return jndiResolved; }
    public String getJndiStatusMessage() { return jndiStatusMessage; }
    public String getJndiResourceName() { return jndiResourceName; }
    public boolean isConnectionEstablished() { return connectionEstablished; }
    public String getConnectionStatusMessage() { return connectionStatusMessage; }
    public long getDbQueryLatencyMs() { return dbQueryLatencyMs; }
    public String getServerUptime() { return serverUptime; }
    public int getActiveSessions() { return activeSessions; }
    public String getLoggedInUser() { return loggedInUser; }
    public long getFreeMemoryMb() { return freeMemoryMb; }
    public long getTotalMemoryMb() { return totalMemoryMb; }
}