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
import kth.lab1.UI.controller.HomeController;
import kth.lab1.UI.controller.SessionController;

@WebServlet("/app/*")
public class UIHandler extends HttpServlet {

        private final Map<String, ViewAction> actionRegistry = new HashMap<>();
        private final ProductController productController = new ProductController();
        private final StatusController statusController = new StatusController();
        private final HomeController homeController = new HomeController();
        private final SessionController sessionController = new SessionController();

        @Override
        public void init() throws ServletException {
            attachActions();
        }

        private void attachActions() {
        // --- Product & Catalog Actions ---
        actionRegistry.put("itemDetail", productController::handleDetail);
        actionRegistry.put("status", statusController::handleStatus);
        actionRegistry.put("index", homeController::handleHome);
        //actionRegistry.put("login", sessionController::showLoginForm);
        actionRegistry.put("logout", sessionController::handleLogout);
        //actionRegistry.put("dologin", sessionController::doLogin);
        //actionRegistry.put("search", req -> handleSearch(req)); // Lambda method reference or arrow
        //actionRegistry.put("userProfile", req -> {throw new UnsupportedOperationException("Userprofile is not implemented yet!");});
        //actionRegistry.put("addReview", req -> {throw new UnsupportedOperationException("handleAddReview is not implemented yet!");});
        //actionRegistry.put("showAuthors", req -> {throw new UnsupportedOperationException("handleShowAuthor is not implemented yet!");});
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
        String actionName = (pathInfo != null && pathInfo.length() > 1) ? pathInfo.substring(1) : "index";

        ViewAction action = actionRegistry.get(actionName);

        // If route doesn't exist in registry, attempt direct JSP forward
        if (action == null) {
            request.getRequestDispatcher("/WEB-INF/views/" + actionName + ".jsp").forward(request, response);
            return;
        }

        try {
            String viewName = action.execute(request, response);
        
            // Skip forwarding if controller already issued a response.sendRedirect()
            if (viewName == null || response.isCommitted()) {
                return;
            }

            // Handle string-based redirects like "redirect:/app/index"
            if (viewName.startsWith("redirect:")) {
                String redirectTarget = viewName.substring("redirect:".length());
                response.sendRedirect(request.getContextPath() + redirectTarget);
                return;
            }

            // Standard view rendering
            request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "An internal error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
