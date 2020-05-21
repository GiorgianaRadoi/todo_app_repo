package com.grad.controller;

import com.grad.model.DAO.Project;
import com.grad.model.DAO.User;
import com.grad.model.DTO.ProjectDTO;
import com.grad.repository.ProjectRepository;
import com.grad.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    public Tab tabProjects;

    public VBox vBoxProjects;
    public TextField txtFieldProject;
    public Button btnInsertProject;
    public TableView tblViewProject;
    public TableColumn colProjectId;
    public TableColumn colProject;
    public TableColumn colUser;
    private UserRepository userRepository;
    public ProjectRepository projectRepository;
    public User loggedInUser;

    public void initialize() {
        colProjectId.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("id"));
        colProject.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("name"));
        colUser.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("users"));
        userRepository = new UserRepository( Singleton.getInstance() );
        projectRepository = new ProjectRepository( Singleton.getInstance() );
    }


    public void insertProjectEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals( KeyCode.ENTER )) {
            insertProject();
            txtFieldProject.setText( "" );
        }
    }


    public void insertProject() {
        Project project = new Project();
        project.setName( txtFieldProject.getText() );
        projectRepository.save( project );
        CheckBox checkBox = new CheckBox();
        checkBox.setText( project.getName() );
        vBoxProjects.getChildren().add( checkBox );
        List<User> users = project.getUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains( loggedInUser )) {
            users.add( loggedInUser );
        }
        project.setUsers( users );

    }


    public void loadProjectList(Event event) {
        List<ProjectDTO> projects = new ArrayList<>(  );
        for(Project project: projectRepository.findAll()){
            projects.add( new ProjectDTO( project ) );
        }
        final ObservableList<ProjectDTO> dtoProjects = FXCollections.observableList(projects);
        tblViewProject.setItems(dtoProjects);
        System.out.println("Loaded Projects");

    }
}


