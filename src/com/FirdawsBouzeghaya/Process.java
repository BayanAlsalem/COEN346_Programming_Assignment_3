package com.FirdawsBouzeghaya;

import java.io.FileWriter;
import java.time.Clock;

public class Process extends Thread {
    // Declare attributes
    private int burst_time; // execution time.
    private int arrival_time; //time at which the process enters the queue.
    private int remaining_time; //time remaining for the execution of a process.
    private final int process_ID;
    private String command_executed; /*This variable indicates which command this process is executing. */
    private Page assigned_page;

    private Boolean process_state;// set to false if the process did not start its execution
                                  // true when we start its execution and assign to it a core.
    private FileWriter file_writer;

    // Default Constructor
    public Process() {
        process_ID = 0;
        burst_time = 0;
        arrival_time  = 0;
        remaining_time = burst_time;
        process_state = false;
    }
    // Parameterized Constructor
    public Process(int arrival_time, int burst_time) {
        this.burst_time = burst_time;
        //this.remaining_time =  service_time;
        this.process_ID = (int) this.getId();
        this.arrival_time = arrival_time;
        this.process_state = false;
    }
    //getter and setters:
    public int getProcessID() {
        return process_ID;
    }

    public int getServiceTime() {
        return burst_time;
    }

    public int getRemainingTime() {
        return remaining_time;
    }
    public void setRemaining_time(int remaining_time)
    {
        this.remaining_time = remaining_time;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }
    public void setProcess_state(boolean process_state)
    {
        this.process_state = process_state;
    }
    public boolean getProcess_state()
    {
        return this.process_state;
    }
    /*this function is used to generate a random time for the execution of the commands. */

    public void assignProcess_command()
    {

    }


    @Override
    public void run() {

        try {
            //SchedulerCycle.tick();
            System.out.println("Clock: " + SchedulerCycle.get_time() + ", Process " + this.getId() + ": Started.");
            burst_time--;
            //MemoryManager.commands_list.peek().setProcess_executing(this.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //assert MemoryManager.commands_list.peek() != null;
        //MemoryManager.commands_list.peek().setProcess_executing(this);

    }


}