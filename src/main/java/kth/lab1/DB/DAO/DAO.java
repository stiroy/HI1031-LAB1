package kth.lab1.DB.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import kth.lab1.Model.exceptions.DataAccessException;

public abstract class DAO {
//Commits transaction
    protected void commit(Connection connection)
            throws DataAccessException {
        try {
            connection.commit();
        } catch (SQLException e) {
            handleException(connection,
                    "Failed to commit", e);
        }
    }
//Rollbacks transaction
    protected void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(
                "Problem during rollback: "
                + e.getMessage());
        }
    }
//database exeception handling
    protected void handleException(Connection connection, String failureMsg, Exception cause)
            throws DataAccessException {
        if (connection != null) {
            rollback(connection);
        }
        throw new DataAccessException(failureMsg, cause);
    }
//closes the resultset
    protected void closeResultSet(ResultSet result, String failureMsg) throws DataAccessException {
        try {
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                    failureMsg, e);
        }
    }
}