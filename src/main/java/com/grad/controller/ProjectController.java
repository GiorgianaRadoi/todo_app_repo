package com.grad.controller;

import com.grad.model.Project;
import com.grad.model.User;
import com.grad.repository.ProjectRepository;
import com.grad.repository.TaskRepository;
import com.grad.repository.UserRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProjectController {
    public Tab tabProjects;

    public VBox vBoxProjects;
    public TextField txtFieldProject;
    public VBox vBoxProjectAllocated;

    private UserRepository userRepository;
    public TaskRepository taskRepository;
    public ProjectRepository projectRepository;
    private boolean isConnectionSuccessful = false;
    private List<User> loggedInUser;

    public void initialize() {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println( "Connection is not allowed" );
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory( "TODOFx" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository( entityManager );
        taskRepository = new TaskRepository( entityManager );
        projectRepository = new ProjectRepository( entityManager );
    }

    public void insertProjectEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals( KeyCode.ENTER )) {
            insertProject();
            txtFieldProject.setText( "" );
        }
    }

    private void insertProject() {
        Project project = new Project();
        project.setName( txtFieldProject.getText() );

        projectRepository.save( project );

        CheckBox checkBox = new CheckBox();
        checkBox.setText( project.getName() );

        vBoxProjects.getChildren().add( checkBox );
    }

    public void insertProject(ActionEvent actionEvent) {
        insertProject();
    }

    public void loadProjectList(Event event) {

        vBoxProjectAllocated.getChildren().clear();
        List<Project> projects = projectRepository.findAll();

        for (final Project project : projects
        ) {
            CheckBox checkBox = new CheckBox();
            checkBox.setWrapText( false );

            if (project.getUsers() != null) {
                checkBox.setText( project.getProject_id() + ". "
                        + project.getName() + " allocated to " + project.getUsers() );
                checkBox.setDisable( true );
                vBoxProjectAllocated.getChildren().add( checkBox );
            } else {
                vBoxProjects.getChildren().add( checkBox );
                checkBox.setText( project.getProject_id() + ". "
                        + project.getName() );
                checkBox.selectedProperty().addListener( (observable, oldValue, newValue) -> {
                    project.setUsers( loggedInUser );
                    projectRepository.save( project );
                    ProjectController.this.loadProjectList( null );
                } );
            }

        }
    }


}
