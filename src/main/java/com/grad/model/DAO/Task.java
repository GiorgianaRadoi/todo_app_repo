package com.grad.model.DAO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data //for getter and setter
@NoArgsConstructor // for no arg constructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //AUTO , 1, 2, -> POSGRESQL
    @Column(name = "task_id")
    private int id;

    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "task" , cascade = CascadeType.ALL)
    private List<Subtask> subtasks;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "in_progress")
    private Boolean inProgress;
}
