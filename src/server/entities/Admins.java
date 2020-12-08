package server.entities;


import client.models.AbstractTable;
import client.models.AdminsTable;
import java.util.Arrays;

public class Admins extends Entity {

  private long id;
  private String nickname;
  private String password;

  public static final String ID = "id";
  public static final String NICKNAME = "nickname";
  public static final String PASSWORD = "password";

  public Admins(){
    super.FIELDS.addAll(
            Arrays.asList(Admins.ID, Admins.NICKNAME, Admins.PASSWORD));
    id = 0;
    nickname = "";
    password = "";
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public AbstractTable getAbstractTable() {
    return new AdminsTable(getNickname(), getPassword());
  }

  @Override
  public void fill(String field, Object value){
    if (field.equals( FIELDS.get(0) )) setId((Long)value);
    else if(field.equals( FIELDS.get(1) )) setNickname((String)value);
    else if( field.equals( FIELDS.get(2) )) setPassword((String)value);
  }

  @Override
  public Entity clone(){
    Admins admins = new Admins();
    admins.setId(id);
    admins.setNickname(nickname);
    admins.setPassword(password);

    return admins;
  }
}
