package client.models;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AdminsTable implements AbstractTable{
    private final SimpleStringProperty nickname;
    private final SimpleStringProperty password;

    public final ArrayList<String> FIELDS = new ArrayList<>(
            Arrays.asList("nickname", "password"));

    public AdminsTable(){
        this("","");
    }

    public AdminsTable(String nickname, String password) {
        this.nickname = new SimpleStringProperty(nickname);
        this.password = new SimpleStringProperty(password);
    }

    @Override
    public ArrayList<String> fields() {
        return FIELDS;
    }

    @Override
    public void fill(String field, Object value) throws Exception {
        if (field.equals( FIELDS.get(0) )) setNickname((String)value);
        else if(field.equals( FIELDS.get(1) )) setPassword((String)value);
    }

    @Override
    public String take(String field) {
        if(field.equals( FIELDS.get(0) )) return getNickname();
        else if(field.equals( FIELDS.get(1) )) return getPassword();
        return "";
    }

    @Override
    public String takeTyped(String field) {
        if(field.equals( FIELDS.get(0) )) return "'" + getNickname() + "'";
        else if(field.equals( FIELDS.get(1) )) return "'" + getPassword() + "'";
        return "";
    }

    @Override
    public AbstractTable cloneEmpty() {
        return new AdminsTable();
    }

    @Override
    public AbstractTable clone() {
        return new AdminsTable(getNickname(), getPassword());
    }

    @Override
    public boolean isEmpty() {
        if(getNickname().equals("")
                && getPassword().equals(""))
            return true;
        return false;
    }

    @Override
    public String getTableName() {
        return "admins";
    }

    @Override
    public String toString() {
        return "AdminsTable{" +
                "nickname=" + nickname +
                ", password=" + password +
                ", FIELDS=" + FIELDS +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminsTable)) return false;
        AdminsTable that = (AdminsTable) o;
        return Objects.equals(getNickname(), that.getNickname()) &&
                Objects.equals(getPassword(), that.getPassword());
    }


    public String getNickname() {
        return nickname.get();
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
