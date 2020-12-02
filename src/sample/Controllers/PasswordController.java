package sample.Controllers;

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
import sample.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        else if (!validatePassword()){
            invalidLoginLabel.setText("Неверный пароль");
        }
        else{
            // INSERT HERE FURTHER ACTIONS
            invalidLoginLabel.setText("Юху!");
        }
    }
    public void onBack(){
        try{
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));
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

    private boolean validatePassword(){
        DatabaseConnection toolDB = new DatabaseConnection();
        Connection toDB = toolDB.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE password = '" + passwordField.getText() +
                "' AND email = '" + login + "';";

        try{
            Statement statement = toDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                return queryResult.getInt(1) == 1;
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

}

