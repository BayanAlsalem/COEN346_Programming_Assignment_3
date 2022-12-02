package com.FirdawsBouzeghaya;

import java.sql.Timestamp;

public class Page {

    private String variable_id;
    private int variable_value; //unsigned integer value
    private boolean is_available; //a boolean variable that indicates the availability of of a page.
    private Timestamp lastAccess; //used to determine when was this page last accessed.

    public Page(String variable_id, int variable_value)
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

    public void setVariable_value(int variable_value) {
        this.variable_value = variable_value;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public Timestamp getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

}
