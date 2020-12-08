package server.commands;

import server.entities.Entity;

import java.sql.SQLException;
import java.sql.Statement;

public class CommandEditRecord extends AbstractCommand{

    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException {
        String[] parameter = split(message);
        Entity entity = Entity.pojos.get(parameter[0]);

        int unique = 0;
        if(entity.FIELDS.get(1).equals("email"))
            unique = 1;
        StringBuilder update = new StringBuilder( "UPDATE " + parameter[0] + "\n");
        StringBuilder set = new StringBuilder("SET\n");
        String where = "WHERE " + entity.FIELDS.get(unique) + " = "+parameter[1] + ";";

        String prefix = ""; int i = 1;
        for (String item: entity.FIELDS) {
            if (item.equals("id"))
                continue;
            set.append(prefix).append(" ").append(item).append(" = ").append(parameter[i]);
            prefix = ",\n";
            ++i;
        }
        update.append(set).append("\n").append(where);

        System.out.println(update.toString());

        return statement.executeUpdate(update.toString());
    }

}

