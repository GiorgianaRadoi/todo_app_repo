package com.grad.model.DTO;

import com.grad.model.DAO.Project;
import com.grad.model.DAO.Task;
import com.grad.model.DAO.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data //for getter and setter
public class ProjectDTO {
    private static int count;
    private int id;

    private int project_id; // from database
    private String name;

    private List<User> users;
    private List<Task> tasks;

    public ProjectDTO (Project project){
        count++;
        this.id = count;
        this.project_id = project.getProject_id();
        this.name = project.getName();
    }
}
