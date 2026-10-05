package kth.lab1.Model.interfaces;


import java.util.List;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;

public interface OrderRepository {

    /**
     * Places a new order in database for a given username and their cart of products, each product has a quantity that the customer has ordered and a total price is calculated for the order. Reduces quantity in stock.
     *
     * @param customerUsername Product identifier.
     * @param orderedProduct List of products from shopping cart.
     * @param orderID The ID of the order
     * @throws DataAccessException If the database query fails.
     */
    void placeOrder(String customerUsername, List<OrderProduct> orderedProducts, String orderID) throws DataAccessException;

    /** Employee packs given orderID, changing orderstatus from PENDING to PACKED
     * @param employeeUsername The employee that packs the order
     * @param orderID The order that should be marked as packed
     * @throws DataAccessException If the database query fails.
     */   
    void packOrder(String username, String orderID)throws DataAccessException;

    /** Fetch method for customer orders, 
     * @param customerUsername The orders of a given user, if empty, show all orders
     * @return A list of orders from the database
     * @throws DataAccessException If the database query fails.
     */   
    List<CustomerOrder>viewOrders(String customerUsername) throws DataAccessException;
}