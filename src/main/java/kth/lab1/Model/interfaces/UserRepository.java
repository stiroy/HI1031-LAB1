package kth.lab1.Model.interfaces;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.User;
import java.util.List;
public interface UserRepository {
    /**
     * Creates a user with the details specified in the user object.
     * @param user The user to be added to database.
     * @throws DataAccessException If the database query fails.
     */
    void createUser(User user) throws DataAccessException;

    /**
     * Changes the role of a given user.
     * @param user The user that changes role.
     * @throws DataAccessException If the database query fails.
     */
    void changeRole(User user) throws DataAccessException;

    /**
     * Fetches all usernames and their respective roles in the database.
     * @return A list of usernames and role titles.
     * @throws DataAccessException
     */
    List<User> fetchUsers() throws DataAccessException;
}