package com.FirdawsBouzeghaya;

import java.sql.Timestamp;

public class Page {

    private String variable_id;
    private long variable_value; //unsigned integer value
    private boolean is_available; //a boolean variable that indicates the availability of of a page.
    private int last_access_time; //used to determine when was this page last accessed.

    public Page(String variable_id, long variable_value)
    {
       this.variable_id = variable_id;
       this.variable_value = variable_value;
    }
    public String get_variable_id() {
        return variable_id;
    }
    public void set_variable_id(String variable_id) {
        this.variable_id = variable_id;
    }

    public long get_variable_value() {
        return variable_value;
    }

    public void set_variable_value(int variable_value) {
        this.variable_value = variable_value;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public int get_last_access_time() {
        return last_access_time;
    }

    public void setLastAccess(int last_access_time) {
        this.last_access_time = last_access_time;
    }

}
