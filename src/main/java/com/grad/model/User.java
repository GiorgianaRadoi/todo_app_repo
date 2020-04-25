package com.grad.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")// redundant, because table will be named implicitly user
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO-INCREMENT
    private int id;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Task> tasks;


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
