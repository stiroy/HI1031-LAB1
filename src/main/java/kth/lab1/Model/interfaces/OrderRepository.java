package kth.lab1.Model.interfaces;


import java.util.List;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;

public interface OrderRepository {
//customer
    void placeOrder(String customerUsername, List<OrderProduct> orderedProducts) throws DataAccessException;

//employee
    void packOrder(String username, int orderID)throws DataAccessException;

    List<CustomerOrder>viewOrders(String customerUsername) throws DataAccessException;
}