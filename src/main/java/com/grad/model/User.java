package com.grad.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data //for getter and setter
@NoArgsConstructor // for no arg constructor
@Entity
@Table(name = "user")// redundant, because table will be named implicitly user
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_Increment
    private int user_id;
    private String username;
    private String password;
    @Column(name="ia_admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "working_project",
            joinColumns = @JoinColumn(name = "user_id"), //current entity -> user

            inverseJoinColumns = @JoinColumn(name = "project_id")) // "foreign" entity -> project
    private List<Project> projects;

    @OneToOne(mappedBy = "user")
    private PendingUser pendingUser;

}
