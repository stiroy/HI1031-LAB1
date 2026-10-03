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
        actionRegistry.put("login", sessionController::showLoginForm);
        actionRegistry.put("doLogin", sessionController::handleLogin);
        actionRegistry.put("logout", sessionController::handleLogout);
        //actionRegistry.put("search", req -> handleSearch(req)); // Lambda method reference or arrow
        //actionRegistry.put("userProfile", req -> {throw new UnsupportedOperationException("Userprofile is not implemented yet!");});
        //actionRegistry.put("addReview", req -> {throw new UnsupportedOperationException("handleAddReview is not implemented yet!");});
        //actionRegistry.put("showAuthors", req -> {throw new UnsupportedOperationException("handleShowAuthor is not implemented yet!");});
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        // Extract action name from URL: e.g. "/app/itemDetail" -> "itemDetail"
        String pathInfo = request.getPathInfo();
        String actionName = (pathInfo != null && pathInfo.length() > 1) ? pathInfo.substring(1) : "index";

        ViewAction action = actionRegistry.get(actionName);

        try {
            String viewName = action.execute(request, response);
        
            if (viewName != null) {
                // Forward cleanly to WEB-INF/views/{viewName}.jsp
                request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp").forward(request, response);
            }
        
            } catch (IllegalArgumentException e) {
                // Handle bad user input (400 Bad Request)
                request.setAttribute("errorMessage", e.getMessage());
                //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        
            } 
            /*catch (ProductNotFoundException e) {
                // Handle missing resource (404 Not Found)
                request.setAttribute("errorMessage", e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        
            }*/
            catch (UnsupportedOperationException e) {
                // Handle unfinished features gracefully during parallel dev
                //request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        
            } catch (Exception e) {
                // Catch-all for unhandled 500 server errors
                request.setAttribute("errorMessage", "An internal error occurred: " + e.getMessage());
                //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
    }
}
