package kth.lab1.Model.interfaces;
import kth.lab1.Model.User;
import kth.lab1.Model.exceptions.DataAccessException;

public interface UserRepository {
    void createUser(User user) throws DataAccessException;
}