package server.commands;

import client.models.AbstractTable;
import client.models.ManagerTables;
import server.entities.Entity;
import server.entities.Users;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommandGetTable extends AbstractCommand{

    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException  {
        String table = message;
        String query = String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 10;",table);
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        ArrayList<Entity> entityArray = new ArrayList<>();
        Entity row;
        while(rs.next()){
            row = Entity.pojos.get(table).clone();
            for(int i = 1; i<=columnsNumber; ++i){
                try {
                    row.fill(rsmd.getColumnName(i), rs.getObject(i));
                } catch (Exception e) { e.printStackTrace(); }
            }
            entityArray.add(row);
        }
        return entityArray;
    }

}