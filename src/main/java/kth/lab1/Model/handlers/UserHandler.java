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
    //For sign up
    public void createCustomer(User customer) throws DataAccessException{
        users.createUser(customer);
    }
    /**
     * Marks an order as packed by the specified employee.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Product must exist.</li>
     * </ul>
     *
     * @param productName Name of product to be searched.
     * @throws NotFoundException If no product is found. 
     * @throws DataAccessException If the database operation fails.
     */   
    public void createEmployee(User employee) throws DataAccessException{
        users.createUser(employee);
    }
    public void changeUserRole(User user) throws DataAccessException {
            users.changeRole(user);

    }
    public List<User> fetchUsers() throws DataAccessException{
        return users.fetchUsers();
    }

}
