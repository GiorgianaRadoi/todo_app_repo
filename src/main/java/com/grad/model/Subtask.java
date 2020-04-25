package com.grad.model;

import javax.persistence.*;

@Entity
@Table
public class Subtask {

    @Id
    @GeneratedValue
    private int subtask_id;
    private String descriere;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;


    public Subtask() {
    }

    public int getSubtask_id() {
        return subtask_id;
    }

    public void setSubtask_id(int subtask_id) {
        this.subtask_id = subtask_id;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
