package server.entities;

import client.models.AbstractTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity implements Serializable {
    public final ArrayList<String> FIELDS = new ArrayList<>();
    public static final Map<String, Entity> pojos = new HashMap<>() {
        {
            put("users", new Users());
            put("admins", new Admins());
        }
    };
    public abstract AbstractTable getAbstractTable();
    public abstract void fill(String field, Object value);
    public abstract Entity clone();
}
