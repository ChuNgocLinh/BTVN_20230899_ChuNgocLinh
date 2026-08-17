package vn.edu.eaut.lab7.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import vn.edu.eaut.lab7.model.Category;
import vn.edu.eaut.lab7.util.JpaUtil;

public class CategoryRepository {
    public List<Category> findAll() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery("select c from Category c order by c.name", Category.class)
                    .getResultList();
        }
    }

    public Category findById(Long id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.find(Category.class, id);
        }
    }

    public long count() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery("select count(c) from Category c", Long.class).getSingleResult();
        }
    }

    public void save(Category category) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        }
    }
}
