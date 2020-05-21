package com.grad.controller;

import com.grad.model.DAO.Task;
import com.grad.model.DAO.User;
import com.grad.model.DTO.TaskDTO;
import com.grad.repository.ProjectRepository;
import com.grad.repository.TaskRepository;
import com.grad.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskController {
    public Tab tabAddTask;
    public TextField txtFieldTODO;
    public Button btnInsert;
    public VBox vBoxTasks;
    public TableColumn colTaskId;
    public TableColumn colTaskDesc;
    public TableColumn colUsername;
    public Tab tabTasks;
    public VBox vBoxTaskList;
    public VBox vBoxTaskListAllocated;
    public Tab tabShowTasks;
    public TableView tblView;
    public TabPane tabPane;
    private UserRepository userRepository;
    public TaskRepository taskRepository;
    public ProjectRepository projectRepository;
    private User loggedInUser;


    public void initialize() {

            colTaskId.setCellValueFactory( new PropertyValueFactory<TaskDTO, Integer>( "id" ) );
            colTaskDesc.setCellValueFactory( new PropertyValueFactory<TaskDTO, String>( "description" ) );
            colUsername.setCellValueFactory( new PropertyValueFactory<TaskDTO, String>( "user" ) );
        userRepository = new UserRepository(Singleton.getInstance());
        projectRepository = new ProjectRepository( Singleton.getInstance() );
        taskRepository = new TaskRepository( Singleton.getInstance() );
    }



    public void insertTaskEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals( KeyCode.ENTER )) {
            insertTask();
            txtFieldTODO.setText( "" );
        }
    }

    private void insertTask() {
        Task task = new Task();
        task.setCreatedAt( new Date() );
        task.setDescription( txtFieldTODO.getText() );

        taskRepository.save( task );


        CheckBox checkBox = new CheckBox();
        checkBox.setText( task.getDescription() );

        vBoxTasks.getChildren().add( checkBox );

    }

    public void insertTask(ActionEvent actionEvent) {
        insertTask();
    }

    public void loadTasks(Event event) {
        List<TaskDTO> tasks = new ArrayList<>(  );
        for (Task task:taskRepository.findAll()) {
            tasks.add( new TaskDTO( task ) );
        }
        final ObservableList<TaskDTO> dbTasks = FXCollections.observableList( tasks );
        tblView.setItems( dbTasks );
        System.out.println( "Loaded tasks" );
    }

    public void loadTaskList(Event event) {
        vBoxTaskList.getChildren().clear();
        vBoxTaskListAllocated.getChildren().clear();
        List<Task> tasks = taskRepository.findAll();

        for (final Task task : tasks) {
            CheckBox checkBox = new CheckBox();
            checkBox.setWrapText( false );

            if (task.getUser() != null) {
                checkBox.setText( task.getId() + ". "
                        + task.getDescription() + " allocated to " + task.getUser().getUsername() );
                checkBox.setDisable( true );
                vBoxTaskListAllocated.getChildren().add( checkBox );
            }else{
                    vBoxTaskList.getChildren().add( checkBox );
                    checkBox.setText( task.getId() + ". "
                            + task.getDescription() );
                    checkBox.selectedProperty().addListener( (observable, oldValue, newValue) -> {
                        task.setUser( loggedInUser );
                        taskRepository.save( task );
                        task.setInProgress( !newValue );
                        loadTaskList( null );
                    } );
                }

            }
        }
    }





