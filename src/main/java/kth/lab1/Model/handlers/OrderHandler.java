package kth.lab1.Model.handlers;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.exceptions.NotFoundException;
import kth.lab1.Model.interfaces.OrderRepository;
import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;
import kth.lab1.Model.records.Product;

import java.util.List;
public class OrderHandler {
    private final OrderRepository orders;

    public OrderHandler(OrderRepository orders) {
        this.orders = orders;
    }
    
    public void placeOrder(String customerUsername, List<OrderProduct> orderedProducts) throws DataAccessException {
        if (customerUsername == null || customerUsername.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (orderedProducts.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }
        String orderID = orderedProducts.get(0).orderID();
        for (OrderProduct orderedProduct : orderedProducts) {
            Product product = orderedProduct.product();
            if (orderedProduct.quantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            if (orderedProduct.quantity() > product.stockQuantity()) {
                throw new IllegalArgumentException("Not enough stock in inventory for " + product.name());
            }
        }
        orders.placeOrder(customerUsername, orderedProducts, orderID);
    }

    public void packOrder(String employeeUsername, int orderID) throws DataAccessException{
        if(employeeUsername.isBlank()){
            throw new IllegalArgumentException("Invalid username");
        }
        if(orderID <= 0){
            throw new IllegalArgumentException("Invalid order id");
        }
        orders.packOrder(employeeUsername, orderID);
    }

    public List<CustomerOrder>viewAllOrders() throws DataAccessException{

        List<CustomerOrder> foundOrders = orders.viewOrders(null);
        if(foundOrders.isEmpty()){
            throw new NotFoundException("No orders found in database");
        }
        return foundOrders;
    }

    public List<CustomerOrder> viewCustomerOrders(String customerUsername) throws DataAccessException {
        if (customerUsername == null || customerUsername.isBlank()) {
            return viewAllOrders();
        }
        List<CustomerOrder> foundOrders = orders.viewOrders(customerUsername);

        if (foundOrders.isEmpty()) {
            throw new NotFoundException("No orders found for customer: "+ customerUsername);
        }
        return foundOrders;
    }
}
