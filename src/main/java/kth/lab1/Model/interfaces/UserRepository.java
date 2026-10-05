package kth.lab1.Model.interfaces;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.User;
import java.util.List;
public interface UserRepository {
    void createUser(User user) throws DataAccessException;
    void changeRole(User user) throws DataAccessException;
    List<User> fetchUsers() throws DataAccessException;
}