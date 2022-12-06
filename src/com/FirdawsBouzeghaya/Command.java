package com.FirdawsBouzeghaya;

public class Command {
    /*Attributes*/
    private String command_name;
    private String variable_id;
    private int variable_value;

    private Process process_executing;
    /*Constructor: */
    public Command(String command_name, String variable_id, int variable_value)
    {
        this.command_name = command_name;
        this.variable_id = variable_id;
        this.variable_value = variable_value;
    }
    /*Setters*/
    public void setCommand_name(String command_name)
    {
        this.command_name = command_name;
    }
    public void setVariable_id(String variable_id)
    {
        this.variable_id = variable_id;
    }
    public void setVariable_value(int variable_value)
    {
        this.variable_id = variable_id;
    }
    public void setProcess_executing(Process process_executing) {
        this.process_executing = process_executing;
    }

    /*Getters*/
    public String getCommand_name() {
        return command_name;
    }

    public String getVariable_id() {
        return variable_id;
    }

    public int getVariable_value() {
        return variable_value;
    }
    public Process getProcess_executing() {
        return process_executing;
    }

}
