package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.interfaces.UserRepository;
import kth.lab1.Model.records.User;

public class UserHandler {
    private final UserRepository users;

    public UserHandler(UserRepository users) {
        this.users = users;
    }
    //For sign up
    public void createCustomer(User customer) throws DataAccessException{
        if(customer.role().equals("CUSTOMER") ){users.createUser(customer);}
        else{}//wrong user type
    }
    //Admin creates employee
    public void createEmployee(User employee) throws DataAccessException{
        if(employee.role().equals("EMPLOYEE")){users.createUser(employee);}
        else{}//wrong user type
    }


}
