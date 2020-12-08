package client.controllers;

import client.database.SendDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import client.models.AbstractTable;
import client.models.ManagerTables;
import server.entities.Users;
import utils.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public AbstractTable modelPrototype;
    private AbstractTable modelSelected;

    ObservableList<AbstractTable> models = FXCollections.observableArrayList();
    ObservableList<String> choices = FXCollections.observableArrayList("users", "admins");

    @FXML
    ChoiceBox<String> tableNameChoiceBox ;

    @FXML
    TableView<AbstractTable> table;

    @FXML
    Label updateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableNameChoiceBox.getItems().addAll(choices);
        tableNameChoiceBox.setOnAction(event -> {
            String choice = tableNameChoiceBox.getValue();
            changeTable(choice);
            displayColumns();
            updateTable();
        });
        tableNameChoiceBox.setValue("users");

        //получить выделенную запись
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setSelected((AbstractTable)newValue));

        //двойной клик по записи открывает окно редактирования
        table.setRowFactory( tv -> {
            TableRow<AbstractTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    onEdit();
                }
            });
            return row ;
        });

    }

    @FXML
    public void onAdd() {
        try {
            AdminDialogController controller = openDialog();
            //специфичные для добавления настройки
            controller.setMode("Add");
            controller.setModel(modelPrototype.cloneEmpty());
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    public void onEdit(){
        if(modelSelected != null) {
            try {
                AdminDialogController controller = openDialog();
                //специфичные для редактирования настройки
                controller.setMode("Edit");
                controller.setModel(modelSelected);
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        else Alert.alert("Не выбрано поле!","Пожалуйста, выберите запись.");
    }

    @FXML
    public void onDelete() {
        if(modelSelected != null) {
            updateTable("delete", modelSelected);
            updateTable();
        }
        else Alert.alert("Не выбрано поле!", "Пожалуйста, выберите запись.");
    }

    private AdminDialogController openDialog() throws IOException {
        //загружаем adminDialog.fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/adminDialog.fxml"));
        Parent rootLayout = loader.load();

        //создаём новое окно и отображаем adminDialog.fxml
        Stage dlg = new Stage();
        dlg.initStyle(StageStyle.UNDECORATED);
        dlg.setTitle("Редактор");
        dlg.setScene(new Scene(rootLayout));

        //даём контроллеру доступ к главному приложению
        AdminDialogController controller = loader.getController();
        controller.setAdminController(this);

        // отображаем диалоговое окно и настраиваем
        dlg.show();
        controller.setStage(dlg);

        return controller;
    }

    @FXML
    public void onClose() {
        Stage stage = (Stage) table.getScene().getWindow();

        Parent root = null;
        try { root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        } catch (IOException e) { e.printStackTrace(); }

        stage.setWidth(600);
        stage.setHeight(400);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onUpdate(){
        updateTable();
    }

    private void displayColumns() {
        table.getColumns().clear();
        for(String f : modelPrototype.fields()) {
            TableColumn<AbstractTable, String> col = new TableColumn<>(f);
            col.setCellValueFactory(new PropertyValueFactory<>(f));
            table.getColumns().add(col);
        }
    }

    public void setSelected(AbstractTable at){
        modelSelected = at;
    }

    public AbstractTable getSelected(){
        return modelSelected;
    }

    public void updateTable(){
        try{
            models.clear();
            models.addAll( SendDatabase.getArray(modelPrototype));
            table.setItems(models);
            new Thread() {
                public void run() {
                    updateLabel.setVisible(true);
                    try{
                        sleep(2000);
                    }catch(Exception ignored){};
                    updateLabel.setVisible(false);
                }
            }.start();
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            Alert.alert("Ошибка", e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            Alert.alert("Ошибка", "Не получилось соединиться с сервером");
        }
    }
    public void updateTable(String command, AbstractTable table){
        try{
            SendDatabase.updateDB(command, table);
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            Alert.alert("Ошибка", e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            Alert.alert("Ошибка", "Не получилось соединиться с сервером");
        }
    }

    public void changeTable(String tableName){
        models.clear();
        modelPrototype = ManagerTables.get(tableName);
    }

}
