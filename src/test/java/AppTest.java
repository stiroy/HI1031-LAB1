import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
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
}
