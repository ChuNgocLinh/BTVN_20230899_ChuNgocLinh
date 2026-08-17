package vn.edu.eaut.lab7.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.util.List;
import vn.edu.eaut.lab7.model.Category;
import vn.edu.eaut.lab7.model.Product;
import vn.edu.eaut.lab7.model.User;
import vn.edu.eaut.lab7.repository.CategoryRepository;
import vn.edu.eaut.lab7.repository.ProductRepository;
import vn.edu.eaut.lab7.repository.UserRepository;
import vn.edu.eaut.lab7.util.JpaUtil;

@WebListener
public class AppStartupListener implements ServletContextListener {
    private final UserRepository userRepository = new UserRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        seedUsers();
        seedCatalog();
        System.out.println("Lab 7 JPA/Hibernate da khoi tao du lieu mau.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.close();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "123456", "Quan tri vien", "ADMIN"));
            userRepository.save(new User("user", "123456", "Nguoi dung", "USER"));
        }
    }

    private void seedCatalog() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Laptop", "May tinh xach tay"));
            categoryRepository.save(new Category("Phone", "Dien thoai thong minh"));
            categoryRepository.save(new Category("Accessory", "Phu kien cong nghe"));
        }

        if (productRepository.count() == 0) {
            List<Category> categories = categoryRepository.findAll();
            Category accessory = categories.stream()
                    .filter(c -> "Accessory".equals(c.getName()))
                    .findFirst()
                    .orElse(categories.get(0));
            Category laptop = categories.stream()
                    .filter(c -> "Laptop".equals(c.getName()))
                    .findFirst()
                    .orElse(categories.get(0));
            Category phone = categories.stream()
                    .filter(c -> "Phone".equals(c.getName()))
                    .findFirst()
                    .orElse(categories.get(0));

            productRepository.save(new Product("SP001", "Laptop Dell Inspiron", new BigDecimal("14500000"), 8, laptop));
            productRepository.save(new Product("SP002", "iPhone 13", new BigDecimal("12990000"), 12, phone));
            productRepository.save(new Product("SP003", "Chuot Logitech M331", new BigDecimal("350000"), 30, accessory));
        }
    }
}
