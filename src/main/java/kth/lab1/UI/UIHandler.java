package kth.lab1.UI;

import java.io.IOException;

import kth.lab1.Model.ProductHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/item")
public class UIHandler extends HttpServlet {
    
        private final ProductHandler handler = new ProductHandler();

        @Override 
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            String id = request.getParameter("id");
            int itemId = 101; // default value in case of failiure

            if (id != null && !id.trim().isEmpty()) {
                try {
                    itemId = Integer.parseInt(id.trim());
                } catch (NumberFormatException e) {
                    itemId = 101; // fallback in case of parsial write
                }
            }
            ProductDTO productDTO = handler.getItemById(itemId);
            if (productDTO == null) {
                productDTO = new ProductDTO(itemId, "fallback Item", "we are doomed", 0, 1);
            }

            request.setAttribute("item", productDTO);
            request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);;

        }
}
