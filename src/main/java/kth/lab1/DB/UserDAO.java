package kth.lab1.DB;
import kth.lab1.Model.exceptions.DataAccessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
        //Commits current transaction
    public static void commit(Connection connection) throws DataAccessException{
        try{
            connection.commit();
        }
        catch(SQLException e){handleException(connection, "Failed to commit", e);}
    }

    //Rollbacks current transaction
    public static void rollback(Connection connection){
        try{
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Problem when rollback: " + e.getMessage());
        }
    }

    public static void createUser(UserDTO user){
        
    }




    private static void handleException(Connection connection, String failureMsg, Exception cause) throws DataAccessException {
        String completeFailureMsg = failureMsg;
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackExc) {
                completeFailureMsg = completeFailureMsg +
                        ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
            }
        }
        throw new DataAccessException(completeFailureMsg, cause);
    }    

    private static void closeResultSet(String failureMsg, ResultSet result) throws DataAccessException {
        try {
            result.close();
        } catch (Exception e) {
            throw new DataAccessException(failureMsg + " Could not close result set.", e);
        }
    }   
}
