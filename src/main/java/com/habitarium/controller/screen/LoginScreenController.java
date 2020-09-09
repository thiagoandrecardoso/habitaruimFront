package com.habitarium.controller.screen;


import com.habitarium.utility.screen.AlertScreens;
import com.habitarium.utility.screen.OpenFirstLoginScreen;
import com.habitarium.utility.screen.OpenScreens;
import com.habitarium.utility.screen.ScreenUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.habitarium.dao.UserDAO;
import com.habitarium.entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    @FXML
    private Label tfForgotMyPass;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField tfLogin;

    @FXML
    public Button login;

    @FXML
    public void switchToMain() {
        UserDAO UserDAO = new UserDAO();
        User user = null;
        try{
            user = UserDAO.findByLogin("admin");
        } catch (RuntimeException e){
            System.out.println(e);
        }
        if (user != null) {
            System.out.println(user.getLogin());
            if (user.getLogin().equals(tfLogin.getText()) && user.getPassword().equals(passwordField.getText())) {
                OpenScreens openFirstLogin = new OpenFirstLoginScreen();
                try {
                    CloseScreen();
                    openFirstLogin.loadScreen("screen/firstLoginScreen", "Login", user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                AlertScreens.alertError("Entre em contato com a Habitarium", "Entrar com login inicial");
            }
        } else {
            user = UserDAO.findByLogin(tfLogin.getText());
            if (user != null) {
                if (passwordField.getText().equals(user.getPassword())) {
                    try {
                        CloseScreen();
                        ScreenUtils.switchScreen("screen/mainScreen", "Registro de Propriedade");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertScreens.alertError("Usuário ou senha inválido", "Erro na tentativa de login");
                }
            }
        }
    }

    private void CloseScreen() {
        Stage stageLogin = (Stage) login.getScene().getWindow();
        stageLogin.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFields();
    }

    public void setTextFields() {
        passwordField.setFocusTraversable(false);
        tfLogin.requestFocus();

        tfLogin.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.ENTER || code == KeyCode.TAB) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.ENTER || code == KeyCode.TAB) {
                if (tfLogin.getText().equals("")) {
                    tfLogin.requestFocus();
                } else {
                    switchToMain();
                }
            }
        });
    }
}

