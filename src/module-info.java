module TaskTrackingApp {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;
    //requires javax.json;

    opens client;
}