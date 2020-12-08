package client.controllers;

import client.database.SendDatabase;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Duration;

import utils.Alert;
import utils.MyChecker;

public class LoginController implements Initializable {
    public static String login = "";
    private static boolean admin = false;
    public static boolean isAdmin(){return admin;}

    @FXML
    private TextField loginField;

    @FXML
    private Label invalidLoginLabel;

    @FXML
    private Button continueButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView leftImage;

    @FXML
    private Hyperlink registerHyperlink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image1 = new Image("images/login.png");
        leftImage.setImage(image1);

        invalidLoginLabel.setText("");
        loginField.setText(login);
    }

    public void onCancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow(); // could be better code here
        stage.close();
    }

    public void onRegister() {
        Stage stage = (Stage) registerHyperlink.getScene().getWindow();
        try{
            Parent newRoot = FXMLLoader.load(getClass().getResource("../views/register.fxml"));
            double w = 500;
            double h = 510;
            stage.setWidth(w);
            stage.setHeight(h);
            stage.setScene(new Scene(newRoot));
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void onContinue() {
        String tempLogin = loginField.getText();
        admin = false;

        try {if(tempLogin.isBlank()){
            invalidLoginLabel.setText("Введите почту");
        }
        else if(MyChecker.notEmail(tempLogin)
                && SendDatabase.validateAdmin(tempLogin)){
            admin = true;
            login = tempLogin;
            enterPassword();
        }
        else if(MyChecker.notEmail(tempLogin)){
            invalidLoginLabel.setText("Не является почтой или содержит недопустимые символы. \nВводите в формате email@box.any с -._");
        }
        else if (!SendDatabase.validateEmail(tempLogin)){
            invalidLoginLabel.setText("Почта не найдена. \nПроверьте правильность или зарегистрируйтесь");
        }
        else{
            login = tempLogin;
            enterPassword();
        }}
        catch(IllegalArgumentException e){
            Alert.alert("Ошибка", e.getMessage());
        }
        catch(Exception ignored){}
    }

    private void enterPassword(){
        try{
            Stage stage = (Stage) continueButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../views/password.fxml"));
            stage.setScene(new Scene(newRoot));

            //animation
            newRoot.translateXProperty().set(stage.getWidth());
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(newRoot.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
