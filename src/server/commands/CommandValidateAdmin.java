package server.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandValidateAdmin extends AbstractCommand{
    @Override
    public Object operate(String admin, Statement statement) throws IllegalArgumentException, SQLException {
        String query = String.format("SELECT count(1) FROM admins " +
                        "WHERE nickname = %s;",
                admin);
        ResultSet rs = statement.executeQuery(query);
        while(rs.next())
            return rs.getInt(1) == 1;
        return false;
    }
}
