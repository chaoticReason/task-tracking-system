package server.commands;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractCommand {
    public abstract Object operate(String message, Statement statement) throws IllegalArgumentException, SQLException;
    protected String[] split(String s){
        return s.split("~");
    }
}
