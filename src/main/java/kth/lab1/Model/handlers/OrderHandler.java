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

    /**
     * Places a order in database, reserving the quantities of products and creating an order
     *
     * <p>Business rules:
     * <ul>
     *   <li>Username must not be blank.</li>
     *   <li>Ordered products must exist.</li>
     * </ul>
     *
     * @param customerUsername The customer who's placing the order.
     * @param orderedProducts Products ordered from shopping cart .
     * @throws IllegalArgumentException If the username or order is invalid.
     * @throws DataAccessException If the database operation fails.
     */
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

    /**
     * Marks an order as packed by the specified employee.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Username must not be blank.</li>
     *   <li>Order must exist.</li>
     *   <li>Order must currently be in PENDING status.</li>
     * </ul>
     *
     * @param employeeUsername Employee performing the packing.
     * @param orderID Order to pack.
     * @throws IllegalArgumentException If the username or order ID is invalid.
     * @throws DataAccessException If the database operation fails.
     */ 
    public void packOrder(String employeeUsername, String orderID) throws DataAccessException{
        if(employeeUsername.isBlank()){
            throw new IllegalArgumentException("Invalid username");
        }
        orders.packOrder(employeeUsername, orderID);
    }

    /**
     * Fetches all orders from customers stored in database.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Order must exist.</li>
     * </ul>
     * @return A list of all customer orders in database
     * @throws NotFoundException If no orders are found.
     * @throws DataAccessException If the database operation fails.
    */
    public List<CustomerOrder>viewAllOrders() throws DataAccessException{

        List<CustomerOrder> foundOrders = orders.viewOrders(null);
        if(foundOrders.isEmpty()){
            throw new NotFoundException("No orders found in database");
        }
        return foundOrders;
    }

    /**
     * Fetches all orders from customers stored in database, given a username.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Username must not be blank.</li>
     *   <li>Order must exist.</li>
     * </ul>
     *
     * @param customerUsername Employee performing the packing.
     * @param orderID Order to pack.
     * @return All orders for a given username
     * @throws NotFoundException If no orders are found for the given username.
     * @throws DataAccessException If the database operation fails.
     */
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
