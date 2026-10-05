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

        //if (orderId != null && !orderId.trim().isEmpty()) {
            // 1. Update status in Global Warehouse Queue
            /*@SuppressWarnings("unchecked")
            List<OrderDTO> globalOrders = (List<OrderDTO>) request.getServletContext().getAttribute("globalOrders");

            if (globalOrders != null) {
                for (int i = 0; i < globalOrders.size(); i++) {
                    OrderDTO existing = globalOrders.get(i);
                    if (existing.getOrderId().equals(orderId)) {
                        OrderDTO packedOrder = new OrderDTO(
                            existing.getOrderId(),
                            existing.getCustomerName(),
                            existing.getItems(),
                            existing.getTotalAmount(),
                            existing.orderDate(),
                            "PACKED"
                        );
                        
                        // Replace in global list
                        globalOrders.set(i, packedOrder);

                        // 2. Also update in the user's active session order history if present
                        List<OrderDTO> userOrders = (List<OrderDTO>) request.getSession().getAttribute("orderHistory");
                        if (userOrders != null) {
                            for (int j = 0; j < userOrders.size(); j++) {
                                if (userOrders.get(j).getOrderId().equals(orderId)) {
                                    userOrders.set(j, packedOrder);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }*/
        //}

        return "redirect:/app/employeeOrders";
    }
}