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

    public String handleViewOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("orders", modelFacade.viewAllCustomerOrders());
        request.setAttribute("pageTitle", "Warehouse - Pack Orders");
        return "employeeOrders";
    }

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