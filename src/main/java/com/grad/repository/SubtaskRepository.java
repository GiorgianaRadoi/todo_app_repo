package com.grad.repository;

import com.grad.model.Subtask;
import com.grad.model.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class SubtaskRepository implements CrudRepository<Subtask, Integer> {
    private EntityManager entityManager;
    private SubtaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Subtask> findAll() {
        return null;
    }

    public void save(Subtask subtask) {
        entityManager.getTransaction().begin();
        entityManager.persist( subtask );
        entityManager.getTransaction().commit();

    }

    public void deleteById(Integer id) {
        Subtask subtask = findById( id );
        if (subtask != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(subtask);
            entityManager.getTransaction().commit();
        }

    }

    public Subtask findById(Integer id) {
        try {
            Subtask subtask= entityManager.find(Subtask.class, id);
            return subtask;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return null;
    }
}
