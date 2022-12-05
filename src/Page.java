

import java.sql.Timestamp;

public class Page {

    private String variable_id;
    private long variable_value; //unsigned integer value
    private boolean is_available; //a boolean variable that indicates the availability of of a page.
    private Timestamp last_access; //used to determine when was this page last accessed.

    public Page(String variable_id, long variable_value)
    {
        this.variable_id = variable_id;
        this.variable_value = variable_value;
    }
    public String getVariable_id() {
        return variable_id;
    }
    public void setVariable_id(String variable_id) {
        this.variable_id = variable_id;
    }

    public long getVariable_value() {
        return variable_value;
    }

    public void setVariable_value(long variable_value) {
        this.variable_value = variable_value;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public Timestamp getLast_access() {
        return last_access;
    }

    public void setLast_access(Timestamp last_access) {
        this.last_access = last_access;
    }
    public void increment_last_accessed_time(int clock)
    {
        this.last_access = clock;
    }

}