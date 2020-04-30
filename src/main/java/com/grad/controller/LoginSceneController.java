package com.grad.controller;

import com.grad.model.Task;
import com.grad.model.User;
import com.grad.repository.TaskRepository;
import com.grad.repository.UserRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class LoginSceneController {

    public Button btnGoRegister;
    public TextField txtFieldUsernameLogin;
    public PasswordField txtFieldPasswordLogin;
    public Button btnLogin;
    public Label lblLoginInfo;
    public MenuItem showLoginTab;
    public Tab tabLogin;
    public TabPane tabPane;
    public TextField txtFieldTODO;
    public Tab tabAddTask;
    public Button btnInsert;
    public VBox vBoxTasks;
    public Tab tabShowTasks;
    public TableView tblView;
    public TableColumn colTaskId;
    public TableColumn colTaskDesc;
    public TableColumn colUsername;
    public VBox vBoxTaskList;
    public VBox vBoxTaskListAllocated;
    private UserRepository userRepository;
    private User loggedInUser;
    private TaskRepository taskRepository;
    private boolean isConnectionSuccessful;

    /**
     * CTRL + SHIFT + A and write down whatever u want and intellij is bringing that stuff to you
     * write commit and commit stuff
     */

    public void initialize() {
        try {
            persistenceConnection();

        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
        taskRepository = new TaskRepository(entityManager);
    }

    public void GoToRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream( "sample.fxml" );
        Parent sceneLogin = fxmlLoader.load( resourceAsStream );
        Scene loginScene = new Scene( sceneLogin );

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle( "Register" );
        window.setScene( loginScene );
        window.show();
    }



    public void LoginUser(ActionEvent event) throws Exception {
        loggedInUser = userRepository.findByUserName( txtFieldUsernameLogin.getText() );
//        lblLoginInfo.setVisible( true );
        if (loggedInUser != null) {
            lblLoginInfo.setText( "Congratulations!" );
            lblLoginInfo.setTextFill( Color.GREEN );


        } else {
            lblLoginInfo.setText( "Incorrect user or pw." );
            lblLoginInfo.setTextFill( Color.RED );
        }
        txtFieldUsernameLogin.clear();
        txtFieldPasswordLogin.clear();
    }


    public void showLoginTab(ActionEvent event) {
        tabPane.getTabs().add( tabLogin );
    }

    public void insertTaskEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals( KeyCode.ENTER )) {
            insertTask();
        }
    }

    private void insertTask() {
        Task task = new Task();
        task.setCreatedAt( new Date() );
        task.setDescription( txtFieldTODO.getText() );

        taskRepository.save( task );

        //TODO show all tasks not only the last added ones
        CheckBox checkBox = new CheckBox();
        checkBox.setText( task.getDescription() );

        vBoxTasks.getChildren().add( checkBox );
    }

    public void insertTask(ActionEvent actionEvent) {
        insertTask();
    }

    public void loadTasks(Event event) {
        List<Task> tasks = taskRepository.findAll();
        final ObservableList<Task> dbTasks = FXCollections.observableList( tasks );
        tblView.setItems( dbTasks );
        System.out.println( "Loaded tasks" );
    }

    public void loadTaskList(Event event) {
        vBoxTaskList.getChildren().clear();
        vBoxTaskListAllocated.getChildren().clear();
        List<Task> tasks = taskRepository.findAll();

        for (final Task task : tasks
        ) {
            CheckBox checkBox = new CheckBox();
            checkBox.setWrapText( false );

            if (task.getUser() != null) {
                checkBox.setText( task.getId() + ". "
                        + task.getDescription() + " allocated to " + task.getUser().getUsername() );
                checkBox.setDisable( true );
                vBoxTaskListAllocated.getChildren().add( checkBox );
            } else {
                vBoxTaskList.getChildren().add( checkBox );
                checkBox.setText( task.getId() + ". "
                        + task.getDescription() );
                checkBox.selectedProperty().addListener( new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        task.setUser( loggedInUser );
                        taskRepository.save( task );
                        task.setInProgress( !newValue );
                        LoginSceneController.this.loadTaskList( null );
                    }
                } );
            }

        }
    }
}
