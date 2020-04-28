package com.grad.repository;

import com.grad.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepository implements CrudRepository<User, Integer> {
    private EntityManager entityManager;


    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
       return entityManager.createQuery( "SELECT u  FROM User u " ).getResultList();

    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        User user = findById(id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }

    public User findById(Integer id) {
        //COMPLETED : Add Try catch
        try {
            User user = entityManager.find(User.class, id);
            return user;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return null;
    }

    public User findByUserName(String username) {
        try {
            User user = (User) entityManager
                    .createQuery( "SELECT u  FROM User u WHERE u.username = :username" )
                    .setParameter( "username", username )
                    .getResultList()
                    .get( 0 );
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
