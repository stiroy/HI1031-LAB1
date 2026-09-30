
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Håller en koppling till databasen
public class DBManager{
    private Connection connection;
    private static DBManager instance = null;
    

    private static DBManager getInstance()throws ClassNotFoundException, SQLException{
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws ClassNotFoundException, SQLException{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:1234/placeholder",
                                                    "placeholder", "123");
        connection.setAutoCommit(false);
        
    }
    public static Connection getConnection()throws ClassNotFoundException, SQLException{
        return getInstance().connection;
    }
    

}