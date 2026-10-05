package kth.lab1.UI.controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kth.lab1.Model.ModelFacade;
import kth.lab1.UI.DTO.OrderDTO;
import kth.lab1.UI.DTO.UserDTO;

public class EmployeeController {

    private final ModelFacade modelFacade = new ModelFacade();

    /**
     * Retrieves all customer orders for processing and populates request attributes for the warehouse view.
     * 
     * @param request  the HTTP servlet request used to pass the order list and page title to the view
     * @param response the HTTP servlet response
     * @return the logical view name for the warehouse order packing page
     * @throws Exception if an error occurs while fetching customer orders from the model facade
     */
    public String handleViewOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("orders", modelFacade.viewAllCustomerOrders());
        request.setAttribute("pageTitle", "Warehouse - Pack Orders");
        return "employeeOrders";
    }
    
    /**
     * Processes an order packing request by associating the authenticated employee with the order ID and updating its status.
     * 
     * @param request  the HTTP servlet request containing the 'orderId' parameter and user authentication info
     * @param response the HTTP servlet response
     * @return a redirect path string back to the warehouse orders view
     * @throws Exception if an unhandled error occurs during order packing
     */
    public String handlePackOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String orderIdParam = request.getParameter("orderId");
        String employee = request.getRemoteUser();

        if (orderIdParam != null && !orderIdParam.trim().isEmpty()) {
            try {
                UserDTO employeeUser = new UserDTO(0, employee, orderIdParam, employee);
                
                modelFacade.packOrder(employeeUser, orderIdParam);
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/employee/employeeOrders";
    }
}