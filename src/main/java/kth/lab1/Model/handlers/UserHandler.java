package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.UserRepository;
import kth.lab1.Model.records.User;
import java.util.List;
public class UserHandler {
    private final UserRepository users;

    public UserHandler(UserRepository users) {
        this.users = users;
    }
    //For sign up
    public void createCustomer(User customer) throws DataAccessException{
        users.createUser(customer);
    }
    //Admin creates employee
    public void createEmployee(User employee) throws DataAccessException{
        users.createUser(employee);
    }
    public void changeUserRole(User user) throws DataAccessException {
        if (user.role().equals("ADMIN") || user.role().equals("CUSTOMER")
                                                || user.role().equals("EMPLOYEE")) {
            users.changeRole(user);
        } else {
            throw new IllegalArgumentException("Invalid role: " + user.role());
        }
    }
    public List<User> fetchUsers() throws DataAccessException{
        return users.fetchUsers();
    }

}
