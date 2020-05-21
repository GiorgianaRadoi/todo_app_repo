package com.grad.controller;

import com.grad.model.DAO.User;
import com.grad.repository.TaskRepository;
import com.grad.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;

public class LoginController {

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


    /**
     * CTRL + SHIFT + A and write down whatever u want and intellij is bringing that stuff to you
     * write commit and commit stuff
     */

    public void initialize() {
        userRepository = new UserRepository(Singleton.getInstance());

    }


    public void GoToRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream( "register.fxml" );
        Parent sceneLogin = fxmlLoader.load( resourceAsStream );
        Scene loginScene = new Scene( sceneLogin );

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle( "Register" );
        window.setScene( loginScene );
        window.show();
    }


    public void LoginUser(ActionEvent event) throws Exception {
        loggedInUser = userRepository.findByUserName( txtFieldUsernameLogin.getText() );
        if (loggedInUser != null) {
            lblLoginInfo.setText( "Congratulations!" );
            lblLoginInfo.setTextFill( Color.GREEN );


        } else {
            lblLoginInfo.setText( "Incorrect user or pw." );
            lblLoginInfo.setTextFill( Color.RED );
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream( "project.fxml" );
        Parent sceneLogin = fxmlLoader.load( resourceAsStream );
        Scene loginScene = new Scene( sceneLogin );

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle( "Project");
        window.setScene( loginScene );
        window.show();
    }


    public void showLoginTab(ActionEvent event) {
        tabPane.getTabs().add( tabLogin );
    }


}
