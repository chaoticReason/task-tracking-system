package server.commands;

import server.entities.Entity;

import java.sql.SQLException;
import java.sql.Statement;

public class CommandAddRecord extends AbstractCommand{
    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException {

        String[] parameter = split(message);
        Entity entity = Entity.pojos.get(parameter[0]);

        int i = 1;
        StringBuilder query = new StringBuilder("INSERT INTO " + parameter[0] + " (");
        StringBuilder values = new StringBuilder("(");

        String prefix = "";
        for (String item : entity.FIELDS) {
            if (item.equals("id"))
                continue;
            query.append(prefix).append(" ").append(item);
            values.append(prefix).append(" ").append(parameter[i]);
            prefix = ",";
            ++i;
        }
        values.append(");");
        query.append(") VALUES\n").append(values);


        System.out.println(query.toString());

        return statement.executeUpdate(query.toString());
    }
}
