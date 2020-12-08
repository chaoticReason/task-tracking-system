package client.models;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface AbstractTable {
    public ArrayList<String> fields();
    public void fill(String field, Object value) throws Exception;
    public String take(String field);
    public String takeTyped(String field);
    public AbstractTable cloneEmpty();
    public AbstractTable clone();
    public boolean isEmpty();
    public String getTableName();
}
