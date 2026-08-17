package vn.edu.eaut.lab7.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import vn.edu.eaut.lab7.repository.ProductRepository;

@WebServlet("/products/delete")
public class Bai2ProductDeleteServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        productRepository.delete(Long.parseLong(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/products?deleted=1");
    }
}
