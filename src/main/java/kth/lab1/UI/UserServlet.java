package kth.lab1.UI;

import kth.lab1.Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Fetch/Construct Model Data
        User user = new User("Jane Doe", "Administrator");

        // 2. Attach Model to the Request Scope
        request.setAttribute("user", user);

        // 3. Forward Request & Response to the View (JSP)
        request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp")
               .forward(request, response);
    }
}
