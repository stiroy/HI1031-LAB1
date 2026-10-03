package kth.lab1.Model.interfaces;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.User;

public interface UserRepository {
    void createUser(User user) throws DataAccessException;
}