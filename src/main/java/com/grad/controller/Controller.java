package com.grad.controller;

import com.grad.model.User;
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
    public Label lblErrorMessage;
    public Button showPassword;
    @FXML
    private Label lblInformation;

    @FXML
    private TextField txtFieldUsernameRegister;

    @FXML
    private PasswordField pwdFieldRegister;

    @FXML
    private PasswordField pwdFieldConfirmRegister;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtFieldUsernameLogin;

    @FXML
    private PasswordField pwdFieldLogin;

    @FXML
    private Button btnLogin;

    private UserRepository userRepository;
    private boolean isConnectionSuccessful = false;



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
    }

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        User  user = userRepository.findByUserName( txtFieldUsernameRegister.getText() );
      // String pwdPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{5,}";

        if(pwdFieldRegister.getText().equals( pwdFieldConfirmRegister.getText() )&& user == null   ) {//&&pwdFieldRegister.getText().equals(  pwdPattern)
            user = new User();
            user.setUsername( txtFieldUsernameRegister.getText() );
            user.setPassword( pwdFieldRegister.getText() );
            userRepository.save( user );
            pwdFieldRegister.setText( "" );
            pwdFieldConfirmRegister.setText( "" );
            txtFieldUsernameRegister.setText( "" );
            lblInformation.setText( "user registered!" );

        }else{
            lblInformation.setText("username: "+txtFieldUsernameRegister.getText() + " is already registered.Please pick other username." );
            lblPasswordRegister.setTextFill( Color.RED );
            lblErrorMessage.setText( "Invalid password format!" );
            lblErrorMessage.setTextFill( Color.RED );
        }
    }


    public void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("LoginScene.fxml");
        Parent sceneLogin = fxmlLoader.load(resourceAsStream);
        Scene loginScene = new Scene( sceneLogin );

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene( loginScene );
        window.show();

    }
    public void unveilPassword(ActionEvent actionEvent) {
        if(!txtFieldPasswordRegister.isVisible()){
            showPassword.setText( "Hide" );
            txtFieldPasswordRegister.setText( pwdFieldRegister.getText() );
            txtFieldPasswordRegister.setEditable( false );
            txtFieldPasswordRegister.setVisible( true );
            pwdFieldRegister.setVisible( false );
        }else{
            showPassword.setText( "Show" );
            txtFieldPasswordRegister.setVisible( false );
            pwdFieldRegister.setVisible( true );
        }

    }
}
