package com.grad.repository;

import com.grad.model.Task;
import com.grad.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskRepository implements CrudRepository<Task, Integer> {
    private EntityManager entityManager;
    private TaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Task> findAll() {
        List <Task> tasks= entityManager.createQuery( "SELECT t  FROM Task t " )
                .getResultList();
        return  tasks;

    }

    public void save(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();

    }

    public void deleteById(Integer id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        }

    }

    public Task findById(Integer id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return task;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return null;
    }
}
