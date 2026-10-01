package kth.lab1.UI;

import kth.lab1.Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Extract form values sent from login.jsp
        String usernameInput = request.getParameter("username");
        String roleInput = request.getParameter("role");

        // 2. Create the Model object (no validation checks, as requested)
        User user = new User(usernameInput, roleInput);

        // 3. Store the user object in the Session (persists across page reloads)
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);

        // 4. Forward internal control to the hidden profile JSP
        request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp")
               .forward(request, response);
    }
}
