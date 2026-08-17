package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import vn.edu.eaut.lab7.model.Category;
import vn.edu.eaut.lab7.model.Product;
import vn.edu.eaut.lab7.repository.CategoryRepository;
import vn.edu.eaut.lab7.repository.ProductRepository;

@WebServlet("/products/edit")
public class Bai2ProductEditServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        request.setAttribute("product", productRepository.findById(id));
        request.setAttribute("categories", categoryRepository.findAll());
        request.getRequestDispatcher("/product-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        Product product = productRepository.findById(Long.parseLong(request.getParameter("id")));
        Category category = categoryRepository.findById(Long.parseLong(request.getParameter("categoryId")));

        product.setCode(request.getParameter("code"));
        product.setName(request.getParameter("name"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        product.setCategory(category);

        productRepository.update(product);
        response.sendRedirect(request.getContextPath() + "/products?updated=1");
    }
}
