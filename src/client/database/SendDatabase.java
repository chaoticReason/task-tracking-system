package client.database;

import client.Client;
import client.models.AbstractTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.entities.Entity;

import java.util.ArrayList;

public class SendDatabase {

    private static String isException(Object obj){
        if(obj==null)
            return ("Не удалось подключиться");
        if(obj.getClass() == String.class)
            return (String)obj;
        return "";
    }

    public static boolean validateAdmin(String admin) throws Exception{
        Object obj = Client.get().sendMessage("validateAdmin~'" + admin+"'");

        String error = isException(obj);
        if(!error.equals(""))
            throw new Exception();

        return (boolean)obj;
    }

    public static boolean validateEmail(String email) throws Exception{
        Object obj = Client.get().sendMessage("validateEmail~'" + email+"'");

        String error = isException(obj);
        if(!error.equals(""))
            throw new IllegalArgumentException(error);

        return (boolean)obj;
    }

    public static boolean validatePassword(String login, String password){
        Object obj = Client.get().sendMessage("validatePassword~'" + login+"'~'"+password+"'");
        String error = isException(obj);
        if(!error.equals(""))
            throw new IllegalArgumentException(error);
        return (boolean)obj;
    }

    public static ObservableList<AbstractTable> getArray(AbstractTable table){
        Object obj = Client.get().sendMessage("getTable~" + table.getTableName());
        System.out.println(obj);

        String error = isException(obj);
        if(!error.equals("")) throw new IllegalArgumentException(error);

        ObservableList<AbstractTable> tableList = FXCollections.observableArrayList();
        ArrayList<Entity> entityList =(ArrayList<Entity>)obj;
        for(Entity item: entityList){
            tableList.add(item.getAbstractTable());
        }
        return tableList;
    }

    public static void updateDB(String s, AbstractTable table) throws IllegalArgumentException{
        Object obj = null;
        if(s.equals("delete")){
            obj = Client.get().sendMessage("deleteRecord~"+ table.getTableName() +"~"
                    + table.fields().get(0) +"~"+ table.takeTyped(table.fields().get(0)))+"";
        }
        else if(s.equals("add")){
            StringBuilder query = new StringBuilder("addRecord~"+ table.getTableName());
            for(String item: table.fields()){
                if(!item.equals("id"))
                    query.append("~").append(table.takeTyped(item));
            }
            obj = Client.get().sendMessage(query.toString());
        }
        else if(s.equals("edit")){
            StringBuilder query = new StringBuilder("editRecord~"+ table.getTableName());
            for(String item: table.fields()){
                if(!item.equals("id"))
                    query.append("~").append(table.takeTyped(item));
            }
            obj = Client.get().sendMessage(query.toString());
        }
        String error = isException(obj);
        if(!error.equals(""))
            throw new IllegalArgumentException(error);
    }

}
