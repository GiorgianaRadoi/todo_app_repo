package com.grad.controller;

import com.grad.model.User;
import com.grad.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controller {
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
        }
        catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);
    }

    @FXML
    private void registerUser(ActionEvent actionEvent) {

        User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());
        if(pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText())) {
            user = new User();

            user.setUsername(txtFieldUsernameRegister.getText());
            user.setPassword(pwdFieldRegister.getText());

            userRepository.save(user);
        }
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        //TODO: implement login action
    }
}
