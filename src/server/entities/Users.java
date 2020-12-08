package server.entities;


import client.models.AbstractTable;
import client.models.UsersTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Users extends Entity{

  private long id;
  private String email;
  private String name;
  private String surname;
  private String password;

  public static final String ID = "id";
  public static final String EMAIL = "email";
  public static final String NAME = "name";
  public static final String SURNAME = "surname";
  public static final String PASSWORD = "password";

  public Users(){
    super.FIELDS.addAll(
            Arrays.asList(Users.ID, Users.EMAIL, Users.NAME, Users.SURNAME, Users.PASSWORD));
    id = 0;
    email = "";
    name = "";
    surname = "";
    password = "";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void fill(String field, Object value){
    if (field.equals( FIELDS.get(0) )) setId((Long)value);
    else if (field.equals( FIELDS.get(1) )) setEmail((String)value);
    else if(field.equals( FIELDS.get(2) )) setName((String)value);
    else if(field.equals( FIELDS.get(3) )) setSurname((String)value);
    else if( field.equals( FIELDS.get(4) )) setPassword((String)value);
  }

  @Override
  public AbstractTable getAbstractTable() {
    return new UsersTable(getEmail(), getName(), getSurname(), getPassword());
  }

  @Override
  public Entity clone(){
    Users users = new Users();
    users.setId(id);
    users.setEmail(email);
    users.setName(name);
    users.setSurname(surname);
    users.setPassword(password);
    return users;
  }

}
