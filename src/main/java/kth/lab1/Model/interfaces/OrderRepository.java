package kth.lab1.Model.interfaces;


import java.util.List;

import kth.lab1.Model.Product;
import kth.lab1.Model.exceptions.DataAccessException;

public interface OrderRepository {

    void placeOrder(List<Product> orderedProducts);
    //List<Order>viewOrders();

    
}