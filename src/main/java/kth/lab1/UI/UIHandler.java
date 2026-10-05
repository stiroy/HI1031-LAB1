package kth.lab1.UI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Only of clearification
import kth.lab1.UI.controller.ProductController;
import kth.lab1.UI.controller.StatusController;
import kth.lab1.UI.controller.UserController;
import kth.lab1.UI.controller.AdminController;
import kth.lab1.UI.controller.CartController;
import kth.lab1.UI.controller.EmployeeController;
import kth.lab1.UI.controller.HomeController;
import kth.lab1.UI.controller.SessionController;

@WebServlet(urlPatterns = {"/app/*", "/employee/*", "/admin/*"})
public class UIHandler extends HttpServlet {

        private final Map<String, ViewAction> actionRegistry = new HashMap<>();
        private final ProductController productController = new ProductController();
        private final StatusController statusController = new StatusController();
        private final HomeController homeController = new HomeController();
        private final SessionController sessionController = new SessionController();
        private final UserController userController = new UserController();
        private final CartController cartController = new CartController();
        private final EmployeeController employeeController = new EmployeeController();
        private final AdminController adminController = new AdminController();

        @Override
        public void init() throws ServletException {
            attachActions();
        }

        private void attachActions() {
        // --- Product & Catalog Actions ---
        actionRegistry.put("status", statusController::handleStatus);
        actionRegistry.put("index", homeController::handleHome);
        actionRegistry.put("logout", sessionController::handleLogout);
        actionRegistry.put("userProfile", userController::handleProfile);
        actionRegistry.put("orders", userController::handleOrderHistory);
        // --- Admin Routes ---
        actionRegistry.put("/admin/adminUsers", adminController::handleListUsers);
        actionRegistry.put("/admin/updateUserRole", adminController::handleUpdateUserRole);
        actionRegistry.put("/admin/adminCatalog", adminController::handleManageCatalog);
        actionRegistry.put("/admin/addProduct", adminController::handleAddProduct);
        actionRegistry.put("/admin/updateProduct", adminController::handleUpdateProduct);
        // Employee
        actionRegistry.put("/employee/employeeOrders", employeeController::handleViewOrders);
        actionRegistry.put("/employee/packOrder", employeeController::handlePackOrder);
        // catalog fuctions
        actionRegistry.put("itemDetail", productController::handleDetail);
        actionRegistry.put("catalog", productController::handleCatalog);
        actionRegistry.put("search", productController::handleCatalog);
        // cart functions
        actionRegistry.put("cart", cartController::handleViewCart);
        actionRegistry.put("addToCart", cartController::handleAddToCart);
        actionRegistry.put("updateCart", cartController::handleUpdateCart);
        actionRegistry.put("removeFromCart", cartController::handleRemoveFromCart);
        actionRegistry.put("checkout", cartController::handleCheckout);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        String pathInfo = request.getPathInfo();
        String actionName = (pathInfo != null && pathInfo.length() > 1) 
                            ? pathInfo.substring(1) 
                            : "index";

        ViewAction action = actionRegistry.get(actionName);

        // If route doesn't exist in registry, fall back to resolve JSP dynamically
        if (action == null) {
            forwardToJsp(request, response, actionName);
            return;
        }

        try {
            String viewName = action.execute(request, response);
            if (viewName == null || response.isCommitted()) {
                return;
            }

            if (viewName.startsWith("redirect:")) {
                String redirectTarget = viewName.substring("redirect:".length());
                response.sendRedirect(request.getContextPath() + redirectTarget);
                return;
            }

            // Forward to resolved view JSP
            forwardToJsp(request, response, viewName);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    /**
     * Resolves subfolders dynamically based on view name prefix:
     * - "admin/index"       -> /WEB-INF/admin/index.jsp
     * - "employee/orders"  -> /WEB-INF/employee/orders.jsp
     * - "catalog"           -> /WEB-INF/views/catalog.jsp
     */
    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String viewName) 
            throws ServletException, IOException {

        String jspPath;
        if (viewName.startsWith("admin/") || viewName.startsWith("employee/")) {
            jspPath = "/WEB-INF/" + viewName + ".jsp";
        } else {
            jspPath = "/WEB-INF/views/" + viewName + ".jsp";
        }

        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
