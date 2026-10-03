package kth.lab1.UI;

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
        
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");
        String roleInput = request.getParameter("role");
        UserDTO user = new UserDTO(usernameInput, roleInput, passwordInput);
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);

        request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp").forward(request, response);
    }
}
