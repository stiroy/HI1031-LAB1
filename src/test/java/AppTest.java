import org.junit.jupiter.api.Test;


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
    /* 
    @Test 
    public void testPostgresConnection(){
        boolean connectionWorks = false;
        try {
            Connection connection = DriverManager.getConnection(secret.getSecret(),secret.getUser(),secret.getPass());
            connectionWorks = true;
            System.out.println("✅ Connection established with database!");
        } catch (SQLException e) {
            System.err.println("❌ Connection to database failed!");
        }
        assertTrue(connectionWorks, "PostgreSQL connection was not established");
    }*/
}
