package kth.lab1.DB.DAO;
import kth.lab1.DB.DBManager;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.UserRepository;
import kth.lab1.Model.records.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends DAO implements UserRepository{

    public void createUser(User user) throws DataAccessException{
        String failureMsg = "Could not insert new user: " + user.username();
        String createUserStatement = "INSERT INTO T_users(username, password_hash) VALUES (?, ?)";
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
        userStatement.close();
        roleStatement.close();
        connection.close();
        }catch(SQLException | ClassNotFoundException e){handleException(connection, failureMsg, e);}
    }
}
