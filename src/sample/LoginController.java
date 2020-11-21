package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import util.MyChecker;

public class LoginController implements Initializable {
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

    public void onContinue(){
        if(loginField.getText().isBlank()){
            invalidLoginLabel.setText("Введите почту");
        }
        else if( !MyChecker.isEmail( loginField.getText() ) ){
            invalidLoginLabel.setText("Не является почтой или содержит недопустимые символы. \nВводите в формате email@box.any с -._");
        }
        else if (!validateEmail()){
            invalidLoginLabel.setText("Почта не найдена. \nПроверьте правильность или зарегистрируйтесь");
        }
        else{
            invalidLoginLabel.setText("Продолжение следует...");
        }
    }

    public void onCancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow(); // could be better code here
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file1 = new File("images/0.png"); //no picture now
        Image image1 = new Image(file1.toURI().toString());
        leftImage.setImage(image1);
    }

    private boolean validateEmail(){
        DatabaseConnection toolDB = new DatabaseConnection();
        Connection toDB = toolDB.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE email = '" + loginField.getText() + "';";

        try{
            Statement statement = toDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    return true;
                } else {
                    return false;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }
}
