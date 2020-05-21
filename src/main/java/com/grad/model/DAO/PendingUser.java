package com.grad.model.DAO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data //for getter and setter
@NoArgsConstructor // for no arg constructor
@Entity
@Table(name = "pending_user")
public class PendingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
