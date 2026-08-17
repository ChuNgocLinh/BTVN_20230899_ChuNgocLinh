package vn.edu.eaut.lab7.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.Optional;
import vn.edu.eaut.lab7.model.User;
import vn.edu.eaut.lab7.util.JpaUtil;

public class UserRepository {
    public Optional<User> findByUsername(String username) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            User user = em.createQuery(
                            "select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public long count() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery("select count(u) from User u", Long.class).getSingleResult();
        }
    }

    public void save(User user) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
    }
}
