package client.controllers;

import client.database.SendDatabase;
import client.models.UsersTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import utils.Alert;
import utils.MyChecker;

public class RegisterController implements Initializable {

    @FXML
    private ImageView upImage;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private Button closeButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Label invalidLoginLabel;

    @FXML
    private Label invalidFirstnameLabel;

    @FXML
    private Label invalidLastnameLabel;

    @FXML
    private Label invalidPasswordLabel;

    @FXML
    private Label invalidRepeatLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label hintLabel;

    @FXML
    private Rectangle hintRectangle;

    private Boolean[] isValid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Image image1 = new Image("images/register.png");
        //upImage.setImage(image1);

        isValid = new Boolean[5];
        Arrays.fill(isValid, Boolean.FALSE);

        addListeners();
    }

    public void onClose(){
        Stage stage = (Stage) closeButton.getScene().getWindow(); // could be better code here
        stage.close();
    }

    public void onHint(){
        if(hintLabel.isVisible()){
            hintLabel.setVisible(false);
            hintRectangle.setVisible(false);
        }
        else{
            hintLabel.setVisible(true);
            hintRectangle.setVisible(true);
        }
    }

    public void onBack(){
        Stage stage = (Stage) backButton.getScene().getWindow();
        try{
            Parent newRoot = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            double w = 600;
            double h = 400;
            stage.setWidth(w);
            stage.setHeight(h);
            stage.setScene(new Scene(newRoot));
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void onRegister(){
        if(Arrays.asList(isValid).contains(false))
            messageLabel.setText("Заполните корректно все поля!");
        else {
            addUser();
            clearFields();
        }
    }

    private void addListeners(){
        loginField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            isValid[0] = false;
            if (!newVal) {
                try{
                    if(loginField.getText().equals("")) {
                        invalidLoginLabel.setText("*");
                        invalidLoginLabel.setText("Такой пользователь уже зарегистрирован");}
                    else if(MyChecker.notEmail(loginField.getText())) invalidLoginLabel.setText("Не является почтой");
                    else if(!SendDatabase.validateEmail(loginField.getText())) invalidLoginLabel.setText("Такой пользователь уже зарегистрирован");
                    else {
                    invalidLoginLabel.setText("");
                    isValid[0] = true;
                }} catch(Exception e){}
            }
        });

        firstnameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            isValid[1] = false;
            if(!newVal) {
                if (!firstnameField.getText().equals("")) {
                    invalidFirstnameLabel.setText("");
                    isValid[1] = true;
                }
                else invalidFirstnameLabel.setText("*");
            }
        });

        lastnameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            isValid[2] = false;
            if(!newVal) {
                if (!lastnameField.getText().equals("")) {
                    invalidLastnameLabel.setText("");
                    isValid[2] = true;
                }
                else invalidLastnameLabel.setText("*");
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValid[3] = false;
            if(MyChecker.isGoodPassword(newValue)){
                invalidPasswordLabel.setText("Надёжный пароль");
                isValid[3] = true;
            }
            else invalidPasswordLabel.setText("Простой пароль");
        });

        repeatPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValid[4] = false;
            if(oldValue.length() < newValue.length()
                    || newValue.length() == passwordField.getText().length()
                    || passwordField.getText().equals(oldValue))
                if(newValue.equals(passwordField.getText())){
                    invalidRepeatLabel.setText("");
                    isValid[4] = true;
                }
                else if(newValue.length() < passwordField.getText().length()){
                    invalidRepeatLabel.setText("*");
                }
                else invalidRepeatLabel.setText("Не совпадает");
        });
    }

    private void addUser(){
        UsersTable newUser = new UsersTable(loginField.getText(), firstnameField.getText(),
                lastnameField.getText(), passwordField.getText());
        try{
            SendDatabase.updateDB("add", newUser);
            Alert.alert("Вы успешно зарегистрировались", "");
        }
        catch(IllegalArgumentException e){
            Alert.alert("Ошибка", e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private void clearFields(){
        loginField.clear();
        firstnameField.clear();
        lastnameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }
}
