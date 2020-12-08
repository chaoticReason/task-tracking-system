package client.models;

import javafx.beans.property.SimpleStringProperty;
import utils.MyChecker;

import java.util.*;

public class UsersTable implements AbstractTable{

    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty password;

    public final ArrayList<String> FIELDS = new ArrayList<>(
            Arrays.asList("email", "name", "surname", "password"));

    public UsersTable(){
        this("","","","");
    }

    public UsersTable(String email, String name, String surname, String password){
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.password = new SimpleStringProperty(password);
    }

    @Override
    public ArrayList<String> fields(){
        return FIELDS;
    }

    @Override
    public void fill(String field, Object value){
        if (field.equals( FIELDS.get(0) )) setEmail((String)value);
        else if(field.equals( FIELDS.get(1) )) setName((String)value);
        else if(field.equals( FIELDS.get(2) )) setSurname((String)value);
        else if( field.equals( FIELDS.get(3) )) setPassword((String)value);
    }
    @Override
    public String take(String field){
        if(field.equals( FIELDS.get(0) )) return getEmail();
        else if (field.equals( FIELDS.get(1) )) return getName();
        else if(field.equals( FIELDS.get(2) )) return getSurname();
        else if(field.equals( FIELDS.get(3) )) return getPassword();
        return "";
    }
    public String takeTyped(String field){
        if(field.equals( FIELDS.get(0) )) return getEmailTyped();
        else if (field.equals( FIELDS.get(1) )) return getNameTyped();
        else if(field.equals( FIELDS.get(2) )) return getSurnameTyped();
        else if(field.equals( FIELDS.get(3) )) return getPasswordTyped();
        return "";
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public UsersTable cloneEmpty(){
        return new UsersTable();
    }
    @Override
    public UsersTable clone(){
        return new UsersTable(getEmail(), getName(), getSurname(), getPassword());
    }
    @Override
    public boolean isEmpty(){
        if(getName().equals("") && getSurname().equals("")
        && getPassword().equals("") && getEmail().equals(""))
            return true;
        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersTable)) return false;
        UsersTable user = (UsersTable) o;
        return Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getPassword(), user.getPassword());
    }
    @Override
    public String toString() {
        return "User{" +
                " email=" + email.get() +
                ", name=" + name +
                ", surname=" + surname +
                ", password=" + password +
                ", fields=" + FIELDS +
                '}';
    }

    //// GETTERS & SETTERS
    public String getEmailTyped() { return "'"+email.getValue()+"'"; }
    public String getNameTyped() { return "'"+name.getValue()+"'"; }
    public String getSurnameTyped() { return "'"+surname.getValue()+"'"; }
    public String getPasswordTyped() { return "'"+password.getValue()+"'"; }

    public String getEmail() { return email.getValue(); }
    public String getName() { return name.getValue(); }
    public String getSurname() { return surname.getValue(); }
    public String getPassword() { return password.getValue(); }

    public void setEmail(String e) {
        if(e.equals(""))
            throw new IllegalArgumentException(FIELDS.get(1) + " Введите почту");
        if(e.contains("'"))
            throw new IllegalArgumentException(FIELDS.get(2) + " Не используйте кавычки");
        else if (MyChecker.notEmail(e))
            throw new IllegalArgumentException(FIELDS.get(1) + " Поле почты не является почтой");
        email.setValue(e);
    }

    public void setName(String n) {
        if(n.equals(""))
            throw new IllegalArgumentException(FIELDS.get(2) + " Введите имя");
        if(n.contains("'"))
            throw new IllegalArgumentException(FIELDS.get(2) + " Не используйте кавычки");
        name.setValue(n);
    }

    public void setSurname(String s) {
        if(s.equals(""))
            throw new IllegalArgumentException(FIELDS.get(3) + " Введите фамилию");
        if(s.contains("'"))
            throw new IllegalArgumentException(FIELDS.get(2) + " Не используйте кавычки");
        surname.setValue(s);
    }

    public void setPassword(String p) {
        if(p.equals(""))
            throw new IllegalArgumentException(FIELDS.get(4) + " Введите пароль");
        if(p.contains("'"))
            throw new IllegalArgumentException(FIELDS.get(2) + " Не используйте кавычки");
        else if (!MyChecker.isGoodPassword(p))
            throw new IllegalArgumentException(FIELDS.get(4) + " В надёжном пароле должно быть минимум 8 символов \nи должны встречаться цифры и латинские буквы разного регистра.");
        password.setValue(p);
    }

}
