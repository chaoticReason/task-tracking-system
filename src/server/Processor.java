package server;

import server.commands.*;
import java.util.Map;

public class Processor{

    public static final Map<String, AbstractCommand> COMMANDS = new java.util.HashMap<>();
    static{
        COMMANDS.put("validateEmail", new CommandValidateEmail());
        COMMANDS.put("validateAdmin", new CommandValidateAdmin());
        COMMANDS.put("validatePassword", new CommandValidatePassword());
        COMMANDS.put("getTable", new CommandGetTable());
        COMMANDS.put("deleteRecord", new CommandDeleteRecord());
        COMMANDS.put("addRecord", new CommandAddRecord());
        COMMANDS.put("editRecord", new CommandEditRecord());
    }

}
