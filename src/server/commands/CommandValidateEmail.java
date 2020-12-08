package server.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandValidateEmail extends AbstractCommand{

    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException{
        String email = message;
        String query = String.format("SELECT count(1) FROM users " +
                        "WHERE email = %s;",
                        email);
        ResultSet rs = statement.executeQuery(query);
        while(rs.next())
            return rs.getInt(1) == 1;
        return false;
    }

}
