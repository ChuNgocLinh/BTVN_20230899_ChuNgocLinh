package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import vn.edu.eaut.lab7.model.Category;
import vn.edu.eaut.lab7.model.Product;
import vn.edu.eaut.lab7.repository.CategoryRepository;
import vn.edu.eaut.lab7.repository.ProductRepository;

@WebServlet("/products")
public class Bai2ProductServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Product> products = keyword == null || keyword.isBlank()
                ? productRepository.findAll()
                : productRepository.search(keyword.trim());

        request.setAttribute("products", products);
        request.setAttribute("categories", categoryRepository.findAll());
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.getRequestDispatcher("/product-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        Category category = categoryRepository.findById(Long.parseLong(request.getParameter("categoryId")));
        Product product = new Product(
                request.getParameter("code"),
                request.getParameter("name"),
                new BigDecimal(request.getParameter("price")),
                Integer.parseInt(request.getParameter("quantity")),
                category);
        productRepository.save(product);
        response.sendRedirect(request.getContextPath() + "/products?created=1");
    }
}
