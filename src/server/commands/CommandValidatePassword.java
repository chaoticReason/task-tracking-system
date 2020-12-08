package server.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandValidatePassword extends AbstractCommand{

    @Override
    public Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException{
        String[] pars = split(message);
        String email = pars[0];
        String password = pars[1];

        String query1 = String.format("SELECT count(1) FROM admins " +
                        "WHERE nickname = %s AND password = %s;",
                         email, password);
        String query2 = String.format("SELECT count(1) FROM users " +
                        "WHERE email = %s AND password = %s;",
                email, password);

        ResultSet rs = statement.executeQuery(query1);
        while(rs.next())
            if(rs.getInt(1) == 1)
                return true;

        rs = statement.executeQuery(query2);
        while(rs.next())
            return(rs.getInt(1) == 1);
        return false;
    }

}
