package com.grad.model;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue
    private int task_id;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Subtask> subtask;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public Task() {
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subtask getSubtask() {
        return (Subtask) subtask;
    }

    public void setSubtask(Subtask subtask) {
        this.subtask = (Set<Subtask>) subtask;
    }
}
