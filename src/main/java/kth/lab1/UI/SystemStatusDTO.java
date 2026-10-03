package kth.lab1.UI;

import java.util.Date;

public class SystemStatusDTO {
    private final String deploymentStatus = "ONLINE";
    private final Date serverTime = new Date();
    private final String javaVersion = System.getProperty("java.version");
    private final String serverInfo;
    private final boolean connectionEstablished;
    private final String connectionStatusMessage;
    private final boolean driverFound;
    private final String driverStatusMessage;

    // Optional Extra Metrics
    private final long maxMemoryMb = Runtime.getRuntime().maxMemory() / (1024 * 1024);
    private final long freeMemoryMb = Runtime.getRuntime().freeMemory() / (1024 * 1024);
    private final long totalMemoryMb = Runtime.getRuntime().totalMemory() / (1024 * 1024);

    public SystemStatusDTO(String serverInfo, boolean connectionEstablished, String connectionStatusMessage, 
                           boolean driverFound, String driverStatusMessage) {
        this.serverInfo = serverInfo;
        this.connectionEstablished = connectionEstablished;
        this.connectionStatusMessage = connectionStatusMessage;
        this.driverFound = driverFound;
        this.driverStatusMessage = driverStatusMessage;
    }

    // Getters
    public String getDeploymentStatus() { return deploymentStatus; }
    public Date getServerTime() { return serverTime; }
    public String getJavaVersion() { return javaVersion; }
    public String getServerInfo() { return serverInfo; }
    public boolean isConnectionEstablished() { return connectionEstablished; }
    public String getConnectionStatusMessage() { return connectionStatusMessage; }
    public boolean isDriverFound() { return driverFound; }
    public String getDriverStatusMessage() { return driverStatusMessage; }
    public long getMaxMemoryMb() { return maxMemoryMb; }
    public long getFreeMemoryMb() { return freeMemoryMb; }
    public long getTotalMemoryMb() { return totalMemoryMb; }
}