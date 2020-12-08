package server.commands;

import java.sql.SQLException;
import java.sql.Statement;

public class CommandDeleteRecord extends AbstractCommand{
    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException{
        String[] pars = split(message);
        String table = pars[0];
        String uniqueField = pars[1];
        String value = pars[2];
        String query =  String.format("DELETE FROM %s WHERE %s = %s;",
                        table, uniqueField, value);

        System.out.println(query);
        return statement.executeUpdate(query);
    }
}
