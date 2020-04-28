package com.grad.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data //for getter and setter
@NoArgsConstructor // for no arg constructor
@Entity
@Table(name = "subtask")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private int id;

    private String description;

    @ManyToOne
    private Task task;
}
