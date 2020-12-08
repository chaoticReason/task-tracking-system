package client.models;

public class ManagerTables {
    private static final String USERS = "users";
    private static final String ADMINS = "admins";

    public static AbstractTable get(String s){
        switch(s)
        {
            case USERS: return new UsersTable();
            case ADMINS: return new AdminsTable();
            default: throw new IllegalArgumentException("Нет такой таблицы");
        }
    }
}
