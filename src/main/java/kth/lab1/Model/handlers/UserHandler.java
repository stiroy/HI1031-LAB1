package kth.lab1.Model.handlers;

import kth.lab1.Model.User;
import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.UserRepository;

public class UserHandler {
    private final UserRepository users;

    public UserHandler(UserRepository users) {
        this.users = users;
    }

    public void createCustomer(User customer) throws DataAccessException{
        if(customer.role() == "CUSTOMER"){users.createUser(customer);}
        else{}//wrong user type
    }

    public void createEmployee(User employee) throws DataAccessException{
        if(employee.role() == "CUSTOMER"){users.createUser(employee);}
        else{}//wrong user type
    }


}
