package com.grad.controller;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Singleton {

    private static Singleton connection;
    private static EntityManager entityManager;

    private Singleton() {
        try {
            persistenceConnection();

        } catch (Exception ex) {
            System.out.println( "Connection is not allowed" );

        }
    }


    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "TODOFx" );
        entityManager = entityManagerFactory.createEntityManager();

    }


    public static EntityManager getInstance() {
        if (connection == null) {
            connection = new Singleton();
        }

        return entityManager;
    }

}
