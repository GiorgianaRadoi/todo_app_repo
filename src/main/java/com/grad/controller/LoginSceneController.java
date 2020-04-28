package com.grad.controller;

import com.grad.model.User;
import com.grad.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;

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
    private UserRepository userRepository;
    private User loggedInUser;


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

    public void initialize() {
        tabPane.getTabs().remove( tabLogin );
    }

    public void LoginUser(ActionEvent event) {
        loggedInUser = userRepository.findByUserName( txtFieldUsernameLogin.getText() );
        lblLoginInfo.setVisible( true );
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
}
