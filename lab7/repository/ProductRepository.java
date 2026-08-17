package vn.edu.eaut.lab7.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import vn.edu.eaut.lab7.model.Product;
import vn.edu.eaut.lab7.util.JpaUtil;

public class ProductRepository {
    public List<Product> findAll() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery(
                            "select p from Product p join fetch p.category order by p.id desc", Product.class)
                    .getResultList();
        }
    }

    public List<Product> search(String keyword) {
        String likeKeyword = "%" + keyword.toLowerCase() + "%";
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery(
                            "select p from Product p join fetch p.category "
                                    + "where lower(p.code) like :kw or lower(p.name) like :kw "
                                    + "or lower(p.category.name) like :kw order by p.id desc",
                            Product.class)
                    .setParameter("kw", likeKeyword)
                    .getResultList();
        }
    }

    public Product findById(Long id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery(
                            "select p from Product p join fetch p.category where p.id = :id", Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public long count() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery("select count(p) from Product p", Long.class).getSingleResult();
        }
    }

    public void save(Product product) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        }
    }

    public void update(Product product) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        }
    }

    public void delete(Long id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
            }
            em.getTransaction().commit();
        }
    }
}
