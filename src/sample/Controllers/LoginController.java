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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.util.Duration;
import sample.DatabaseConnection;

import util.MyChecker;

public class LoginController implements Initializable {
    public static String login = "";

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
            Parent newRoot = FXMLLoader.load(getClass().getResource("../Views/register.fxml"));
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

    public void onContinue(){
        if(loginField.getText().isBlank()){
            invalidLoginLabel.setText("Введите почту");
        }
        else if(MyChecker.notEmail(loginField.getText())){
            invalidLoginLabel.setText("Не является почтой или содержит недопустимые символы. \nВводите в формате email@box.any с -._");
        }
        else if (!validateEmail()){
            invalidLoginLabel.setText("Почта не найдена. \nПроверьте правильность или зарегистрируйтесь");
        }
        else{
            login = loginField.getText();
            enterPassword();
        }
    }

    private boolean validateEmail(){
        DatabaseConnection toolDB = new DatabaseConnection();
        Connection toDB = toolDB.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE email = '" + loginField.getText() + "';";

        try{
            Statement statement = toDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next())
                return queryResult.getInt(1) == 1;

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    private void enterPassword(){
        try{
            Stage stage = (Stage) continueButton.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(getClass().getResource("../Views/password.fxml"));
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
