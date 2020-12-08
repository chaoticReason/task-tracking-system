package client.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import client.models.AbstractTable;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminDialogController implements Initializable {
    final Map<String, TextField> TEXT_FIELDS = new java.util.HashMap<>();
    private Stage stage;
    AdminController adminController;
    AbstractTable model;
    boolean doAdd;
    boolean okClicked;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label invalidLabel;


    /*
     /
     Настройки контроллера:
     /
     */

    //первоначальные настройки
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOk(false);
    }

    //получаем вызвавший контроллер
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    //получаем окно и добавляем к нему слушатели
    public void setStage(Stage stage) {
        this.stage = stage;
        addStageListeners();
    }

    //получаем модель и заполняем её полями окно
    public void setModel(AbstractTable model) {
        this.model = model;
        displayFields();
    }

    //выставляем режим
    public void setMode(String s) {
        if(s.equals("Add")) doAdd = true;
        else doAdd = false;
    }


    /*
     /
     Слушатели событий диалогового окна
     /
     */

    //Stage
    private void addStageListeners(){
        // Слушатель 1
        // Закрываем диалоговое окно без сохранения при нажатии вне диалогового окна
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed (ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown){
                stage.close();
            }
        });

        // Слушатель 2
        // Сохраняем введённые данные при нажатии на Enter
        KeyCombination ok = new KeyCodeCombination(KeyCode.ENTER);
        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(ok.match(event))
                onOK();
        });
    }

    // Кнопка ОК
    @FXML
    public void onOK(){
        try{
            if(doAdd){
                model = updateModel(model);
                adminController.updateTable("add", model);
                adminController.updateTable();
            }
            else{
                AbstractTable newModel = updateModel(model);
                adminController.updateTable("edit", newModel);
                adminController.updateTable();
            }

            stage.close();
        }
        catch(Exception e){
            String str = e.getMessage();
            invalidLabel.setText(str.substring(str.indexOf(' ') + 1));
            TextField tf = TEXT_FIELDS.get(str.substring(0, str.indexOf(" ")));
            if(tf != null) {
                tf.requestFocus();
                if(! tf.getText().equals(""))
                    tf.selectPositionCaret(tf.getText().length());
            }
            return;
        }
    }

    // Показываем поля
    private void displayFields() {
        Label label;
        TextField textField;
        int i = 0;

        for (String f : model.fields()) {
            //добавляем надписи и текстовые поля по сетке
            label = new Label(f);
            gridPane.add(label, 0, i);
            textField = new TextField(model.take(f));
            gridPane.add(textField, 1, i);
            textField.setPromptText(f);

            // поле email устанавливаем недоступным при редактировании
            if(!doAdd)
                if(label.getText().equals("email"))
                    textField.setDisable(true);

            // сохраняем текстовое поле
            TEXT_FIELDS.put(f, textField);

            i++;
        }
    }

    // Передаём значения в модель
    public AbstractTable updateModel(AbstractTable model) throws Exception{
        AbstractTable temp = model.clone();
        String str;
        for (String key : TEXT_FIELDS.keySet()) {
            str = TEXT_FIELDS.get(key).getText();
            temp.fill(key, str);
        }
        return temp;
    }

    /*
    /
    сеттеры и геттеры
      /
     */
    public void setOk(boolean b){
        okClicked = b;
    }
    public boolean getOk(){
        return okClicked;
    }
}
