package kth.lab1.DB;
import kth.lab1.Model.User;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserRepository{
    
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

    public void createUser(User user) throws DataAccessException{
        String failureMsg = "Could not insert new user: " + user.username();
        String createUserStatement = "INSERT INTO T_users(username, password) VALUES (?, ?)";
        String assignRoleStatement = "INSERT INTO T_user_roles(username, role_name) VALUES (?, ?)";

        Connection connection = null;
        try{   
        connection = DBManager.getConnection();

        PreparedStatement userStatement = connection.prepareStatement(createUserStatement);
        PreparedStatement roleStatement = connection.prepareStatement(assignRoleStatement);

            userStatement.setString(1, user.username());
            userStatement.setString(2, user.password());
            int updatedUserRows = userStatement.executeUpdate();

            roleStatement.setString(1, user.username());
            roleStatement.setString(2, user.role());
            int updatedRoleRows = roleStatement.executeUpdate();

        if(updatedUserRows == 0 || updatedRoleRows == 0){
            userStatement.close();
            roleStatement.close();
            handleException(connection, failureMsg, null);
        }
        commit(connection);
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
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
