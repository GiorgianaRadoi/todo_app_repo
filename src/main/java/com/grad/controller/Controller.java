package com.grad.controller;

import com.grad.model.Task;
import com.grad.model.User;
import com.grad.repository.TaskRepository;
import com.grad.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;

public class Controller {


    public Button btnGoLogin;
    public Label lblPasswordRegister;
    public TextField txtFieldPasswordRegister;
    public TextField txtFieldConfirmPasswordRegister;
    public Button showPassword;
    public Button btnRegister;
    public Label lblUsernameRegister;
    public Label lblConfirmPasswordRegister;
    public Label lblInformation;
    public Label lblErrorMessage;

    @FXML
    private TextField txtFieldUsernameRegister;
    @FXML
    private PasswordField pwdFieldRegister;
    @FXML
    private PasswordField pwdFieldConfirmRegister;
    private UserRepository userRepository;
    public TaskRepository taskRepository;
    private boolean isConnectionSuccessful = false;
    private Task task;
    private User loggedInUser;
//

    //
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
        taskRepository = new TaskRepository(entityManager);
    }

    @FXML
    public void registerUser(ActionEvent actionEvent) {
        User user = userRepository.findByUserName( txtFieldUsernameRegister.getText() );
        String pwdPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{5,}";

        if (txtFieldUsernameRegister.getText().equals( "" )) {
            lblUsernameRegister.setTextFill( Color.RED );
            lblInformation.setText( "Please provide Username" );
        } else if (pwdFieldRegister.getText().equals( "" )) {
            lblPasswordRegister.setTextFill( Color.RED );
            lblInformation.setText( "Please provide Password" );
//            }else if(!pwdFieldRegister.getText().matches( pwdPattern )){
//                lblPasswordRegister.setTextFill( Color.RED );
//                lblInformation.setText( "Password must contain nr, special ch, capital letter" );
        } else if (pwdFieldConfirmRegister.getText().equals( "" )) {
            lblConfirmPasswordRegister.setTextFill( Color.RED );
            lblInformation.setText( "Please Confirm password" );
        } else if (pwdFieldRegister.getText().equals( pwdFieldConfirmRegister.getText() ) && user == null) {
            user = new User();
            user.setUsername( txtFieldUsernameRegister.getText() );
            user.setPassword( pwdFieldRegister.getText() );
            userRepository.save( user );
            lblInformation.setText( "User registered" );
        } else {
            lblInformation.setText( "Is already in" );
        }
        txtFieldUsernameRegister.clear();
        pwdFieldRegister.clear();
        pwdFieldConfirmRegister.clear();
    }

    public void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream( "LoginScene.fxml" );
        Parent sceneLogin = fxmlLoader.load( resourceAsStream );
        Scene loginScene = new Scene( sceneLogin );

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle( "Login" );
        window.setScene( loginScene );
        window.show();

    }

    public void unveilPassword(ActionEvent actionEvent) {
        if (!txtFieldPasswordRegister.isVisible()) {
            showPassword.setText( "Hide" );
            txtFieldPasswordRegister.setText( pwdFieldRegister.getText() );
            txtFieldPasswordRegister.setEditable( false );
            txtFieldPasswordRegister.setVisible( true );
            pwdFieldRegister.setVisible( false );
        } else {
            showPassword.setText( "Show" );
            txtFieldPasswordRegister.setVisible( false );
            pwdFieldRegister.setVisible( true );
        }

    }

}
