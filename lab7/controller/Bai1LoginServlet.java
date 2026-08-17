package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import vn.edu.eaut.lab7.model.User;
import vn.edu.eaut.lab7.repository.UserRepository;

@WebServlet("/login")
public class Bai1LoginServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            request.getSession().setAttribute("currentUser", user.get());
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        request.setAttribute("error", "Sai ten dang nhap hoac mat khau");
        request.setAttribute("username", username);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
