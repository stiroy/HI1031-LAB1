package kth.lab1.DB;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBManager {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
            "jdbc:postgresql://10.89.0.2:5432/milkyway");

        config.setUsername("sol");
        config.setPassword("terra");

        config.setDriverClassName(
            "org.postgresql.Driver");

        // Pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setAutoCommit(false);

        config.setPoolName("MilkyWayPool");

        dataSource = new HikariDataSource(config);
    }

    private DBManager() {}

    public static Connection getConnection()
            throws SQLException {

        return dataSource.getConnection();
    }

    public static void shutdown() {
        dataSource.close();
    }
}