package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.exceptions.NotFoundException;
import kth.lab1.Model.interfaces.UserRepository;
import kth.lab1.Model.records.User;
import java.util.List;
public class UserHandler {
    private final UserRepository users;

    public UserHandler(UserRepository users) {
        this.users = users;
    }

    /**
     * Create a customer
     * @param customer Customer to be created.
     * @throws DataAccessException If the database operation fails.
     */   
    public void createCustomer(User customer) throws DataAccessException{
        users.createUser(customer);
    }
    /**
     * Create Employee
     * @param employee Employee to be created.
     * @throws DataAccessException If the database operation fails.
     */   
    public void createEmployee(User employee) throws DataAccessException{
        users.createUser(employee);
    }
    /**
     * Change role of a given user
     * @param user User to be changed.
     * @throws DataAccessException If the database operation fails.
     */      
    public void changeUserRole(User user) throws DataAccessException {
            users.changeRole(user);
    }
    /**
     * Fetch usernames and their roles from database
     * @return A list of all users and roles from database
     * @throws DataAccessException If the database operation fails.
     */  
    public List<User> fetchUsers() throws DataAccessException{
        return users.fetchUsers();
    }
}
