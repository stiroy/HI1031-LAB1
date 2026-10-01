package kth.lab1.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Holds a connection to the database.
public class DBManager {
    private Connection connection;
    private static DBManager instance = null;

    private static DBManager getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://10.89.0.2:5432/milkyway", "sol", "terra");
        connection.setAutoCommit(false);
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getInstance().connection;
    }
}