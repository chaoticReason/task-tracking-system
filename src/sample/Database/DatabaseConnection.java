package sample.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection{
    private Connection dbLink;

    public Connection getConnection(){
        String dbName = "dbts"; //Database Bug Tracking System
        String dbUser = "root";
        String dbPassword = "root";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return dbLink;
    }

    public Connection getLink() {
        return dbLink;
    }
}
