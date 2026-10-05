package kth.lab1.Model;

import java.util.List;
import kth.lab1.DB.DAO.OrderDAO;
import kth.lab1.DB.DAO.ProductDAO;
import kth.lab1.DB.DAO.UserDAO;

import kth.lab1.Model.exceptions.DataAccessException;
import kth.lab1.Model.handlers.OrderHandler;
import kth.lab1.Model.handlers.ProductHandler;
import kth.lab1.Model.handlers.UserHandler;

import kth.lab1.Model.records.CustomerOrder;
import kth.lab1.Model.records.OrderProduct;
import kth.lab1.Model.records.Product;
import kth.lab1.Model.records.User;

import kth.lab1.UI.DTO.CustomerOrderDTO;
import kth.lab1.UI.DTO.OrderProductDTO;
import kth.lab1.UI.DTO.ProductDTO;
import kth.lab1.UI.DTO.UserDTO;
/*
 * This is a entry point in to the Model layer
 * Hides all subsystem handlers from the UI layer
 */
public class ModelFacade {
    private final ProductHandler productHandler = new ProductHandler(new ProductDAO());
    private final UserHandler userHandler = new UserHandler(new UserDAO());
    private final OrderHandler orderHandler = new OrderHandler(new OrderDAO());

    /*To be determined:
        How is the current user passed down from view,
        Determines how some methods are called and also how a user's role is decided
    */
    //Enables search by name
    // ----------- CUSTOMER LEVEL METHODS -------------------------------------
    //Search products by name
    public List<ProductDTO> getProductByName(String productName) throws DataAccessException {
        try {
            List<Product> productsByName = productHandler.searchProduct(productName);
            return productsByName.stream().map(p -> new ProductDTO(p.id(), p.name(), p.description(), 
                                        p.category(), p.price(), p.stockQuantity())).toList();
            }catch (DataAccessException e) { throw new DataAccessException("Failed to get product by: "+productName,e);
        }
    }

    //Called when a customer signs up
    public void customerSignUp(UserDTO customer) throws DataAccessException{
        //password should be a secret
        /*User newCustomer = new User(customer.getUsername(), customer.getRole(), customer.getPassword());
        try {
            userHandler.createCustomer(newCustomer);
        } catch (DataAccessException e) {
             throw new DataAccessException("Could not create customer with username: "+ customer.username(),e);
        }*/
    }
/* 
    //Called when a customer places an order from shopping cart
    public void placeOrder(UserDTO activeCustomerDTO, List<OrderProductDTO> shoppingCart) throws DataAccessException{
        List<OrderProduct> orderedProducts = shoppingCart.stream()
        .map(orderedProduct-> new OrderProduct(
            new Product(orderedProduct.product().id(), orderedProduct.product().name(), 
            orderedProduct.product().description(), orderedProduct.product().category(),
            orderedProduct.product().price(), orderedProduct.product().quantity()), 
            orderedProduct.quantity())).toList();
        String activeCustomerUsername = activeCustomerDTO.getUsername();
        try {
            orderHandler.placeOrder(activeCustomerUsername, orderedProducts);
        } catch (DataAccessException e) {
             throw new DataAccessException("Could not place order for customer with username: "+ activeCustomerUsername,e);
        }
    }

    // ----------- ADMIN METHODS -------------------------------------
    //For admin to create Employees
    public void createEmployee(UserDTO employee) throws DataAccessException{
        /*User newEmployee = new User(employee.username(), employee.role(), employee.password());
        try {
            userHandler.createEmployee(newEmployee);
        } catch (DataAccessException e) {
            throw new DataAccessException("Could not create employee with username: "+ employee.username(),e);
        }

    }*/

    // ----------- EMPLOYEE METHODS -------------------------------------

    //Enables search by id 
    // REPLACE PLACEHOLDER INFORMATION
    /*
    public ProductDTO getProductByID(int id) throws DataAccessException {
        try {
            Product product = productHandler.searchProductByID(id);
            return new ProductDTO(
                product.id(), product.name(), product.description(),
                product.category(), product.price(), product.stockQuantity()
            );
        } catch (DataAccessException e) {
            throw new DataAccessException("Failed to get product with id: " + id, e);
        }
    }*/

    // Get Products
    public List<ProductDTO> getProducts() throws DataAccessException {
        try {
            List<Product> productsList = productHandler.getProducts();
            return productsList.stream()
                    .map(p -> new ProductDTO(p.id(), p.name(), p.description(), 
                                            p.category(), p.price(), p.stockQuantity()))
                    .toList();
        } catch (DataAccessException e) {
            throw new DataAccessException("Failed to retrieve products", e);
        }
    }
    /* 
    //Adds product to database
    public void addProduct(ProductDTO productDTO) throws DataAccessException{
        Product product = new Product(-1, productDTO.name(), productDTO.description(), productDTO.category(), productDTO.price(), productDTO.quantity());
        try {
            productHandler.addProduct(product);
        } catch (DataAccessException e) {
            throw new DataAccessException("Failed to add product " + productDTO.name() + " to database",e);
        }
    }

    //Removes a product from product table
    public void removeProduct(ProductDTO productDTO) throws DataAccessException{
        int idToRemove = productDTO.id();
        try {
            productHandler.removeProduct(idToRemove);
        } catch (DataAccessException e) {
             throw new DataAccessException("Failed to remove product with id: "+productDTO.id(),e);
        }
    }

    //Allows for all attributes except for id to be changed
    public void updateProduct(ProductDTO productDTO) throws DataAccessException{
        Product product = new Product(productDTO.id(), productDTO.name(), productDTO.description(), productDTO.category(), productDTO.price(), productDTO.quantity());
        try {
            productHandler.updateProduct(product);
        } catch (DataAccessException e) {
             throw new DataAccessException("Failed to update product with id: "+productDTO.id(),e);
        }
    }

    //Updates quantity of product in stock
    public void updateQuantity(ProductDTO productDTO, int quantity) throws DataAccessException{
        int productID = productDTO.id();
        try {
            productHandler.updateQuantity(productID, quantity);
        } catch (DataAccessException e) {
             throw new DataAccessException("Failed to update quantity of product with id: "+productDTO.id(),e);
        }
    }

    //Initial listing of all customer orders for employees to browse
    public List<CustomerOrderDTO> viewAllCustomerOrders() throws DataAccessException{
         List<CustomerOrder> allCustomerOrders = orderHandler.viewAllOrders();
            return allCustomerOrders.stream().map(co -> new CustomerOrderDTO(co.order_id(), co.customerUsername(),
             co.productName(), co.quantity(), co.unit_price(), co.orderStatus(), co.totalPrice())).toList();
    }

    //Specific user lookup
    public List<CustomerOrderDTO> viewCustomerOrders(String customerUsername) throws DataAccessException{
         List<CustomerOrder> allCustomerOrders = orderHandler.viewCustomerOrders(customerUsername);
            return allCustomerOrders.stream().map(co -> new CustomerOrderDTO(co.order_id(), co.customerUsername(),
             co.productName(), co.quantity(), co.unit_price(), co.orderStatus(), co.totalPrice())).toList();
    }

    public void packOrder(UserDTO activeEmployee, int orderID) throws DataAccessException{
        String activeEmployeeUsername = activeEmployee.getUsername();
        try {
            orderHandler.packOrder(activeEmployeeUsername, orderID);
        } catch (DataAccessException e) {
             throw new DataAccessException("Failed to pack order with id: "+orderID,e);
        }
    }
*/

}

