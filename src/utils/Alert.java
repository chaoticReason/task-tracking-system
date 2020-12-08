package utils;

public class Alert {
    public static void alert(String header, String context){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
