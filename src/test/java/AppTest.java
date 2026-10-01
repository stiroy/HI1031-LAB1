import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    @Test
    public void testPostgresDriverAndConnection() {
        boolean driverFound = false;
        
        try {
            Class.forName("org.postgresql.Driver");
            driverFound = true;
            System.out.println("✅ PostgreSQL JDBC Driver found on classpath!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver missing!");
        }
        assertTrue(driverFound, "PostgreSQL JDBC driver (org.postgresql.Driver) should be present on the classpath.");
    }
    @Test 
    public void testPostgresConnection(){
        boolean connectionWorks = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://10.89.0.2:5432/milkyway",
                                                    "sol", "terra");
            connectionWorks = true;
            System.out.println("✅ Connection established with database!");
        } catch (SQLException e) {
            System.err.println("❌ Connection to database failed!");
        }
        assertTrue(connectionWorks, "PostgreSQL ");
    }
}
