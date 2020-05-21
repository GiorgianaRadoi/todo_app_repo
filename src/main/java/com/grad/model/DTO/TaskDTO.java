package com.grad.model.DTO;

import com.grad.model.DAO.Project;
import com.grad.model.DAO.Subtask;
import com.grad.model.DAO.Task;
import com.grad.model.DAO.User;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class TaskDTO {

    private static int count;
    private int id;

    private int task_id;//from database
    private String description;
    private User user;

    private Project project;
    private List<Subtask> subtasks;
    private Date createdAt;
    private Boolean inProgress;

    public TaskDTO(Task task) {
        count++;
        this.id = count;
        this.task_id = task.getId();
        this.description = task.getDescription();
        this.user = task.getUser();
    }
}
