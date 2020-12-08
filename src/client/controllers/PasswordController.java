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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordController implements Initializable {
    private String login;

    @FXML
    private TextField passwordField;

    @FXML
    private Label invalidLoginLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private ImageView leftImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image1 = new Image("images/login.png");
        leftImage.setImage(image1);

        login = LoginController.login;
        invalidLoginLabel.setText("");
    }

    public void onLogin(){
        if(passwordField.getText().isBlank()){
            invalidLoginLabel.setText("Введите пароль");
        }
        else if (!SendDatabase.validatePassword(LoginController.login, passwordField.getText())){
            invalidLoginLabel.setText("Неверный пароль");
        }
        else{
            if(LoginController.isAdmin()){
                adminStage();
            }
            else invalidLoginLabel.setText("Юху!");
        }
    }
    public void onBack(){
        try{
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            stage.setScene(new Scene(newRoot));

            //animation
            newRoot.translateXProperty().set(-1*stage.getWidth());
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

    private void adminStage(){
        Stage stage = (Stage) invalidLoginLabel.getScene().getWindow();
        try{
            Parent newRoot = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            double w = 900;
            double h = 520;
            stage.setWidth(w);
            stage.setHeight(h);
            stage.setScene(new Scene(newRoot));
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}

