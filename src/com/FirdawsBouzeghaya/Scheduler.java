package com.FirdawsBouzeghaya;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler extends Thread {
    //This class is somehow similar to the Scheduler class
    // implemented in the 2nd programming assignment.
    private Queue<Process> ready_processes;
    private Queue<Process> all_processes;
    private Queue<Process> finished_processes;
    private Queue<Process> running_processes; /*queue that stores the processes that we are running.*/
    private ArrayList<String>commands = new ArrayList<>(); /*An array list to store the different commands from the commands.txt*/
    private int number_cores;
    private int number_available_cores;

    File output_file = new File("src/com/FirdawsBouzeghaya/Output.txt");
    FileWriter file_writer;

    {
        try {
            file_writer = new FileWriter(output_file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scheduler(Queue<Process> processes, int number_cores,ArrayList<String>commands) {
        this.all_processes = new LinkedList<>();
        this.finished_processes = new LinkedList<>();
        this.all_processes = processes;
        this.number_cores = number_cores;
        this.running_processes = new LinkedList<>();
        this.ready_processes = new LinkedList<>();
        this.commands = commands;

    }

    public void set_number_available_cores() {
        //we should not have a number of available cores greater that our number of cores.
        // this will be used to start processes within the 1000ms.
        this.number_available_cores = number_cores - running_processes.size();
    }

    public void assign_core() throws InterruptedException {
        //we first need to check whether we have any process in our ready_processes queue.
        //if that's the case, we need to remove it from the ready_queue process and add it to the running
        // processes queue.
        this.set_number_available_cores();
       /* if (!ready_processes.isEmpty()) {*/
            for (int i = 0; i < this.number_available_cores; i++) {
                if (!ready_processes.isEmpty()) {
                    Process process = ready_processes.remove();
                    running_processes.add(process);
                }
            }
       /*}*/

    }

    void start_process() throws InterruptedException, IOException {
        //to start the execution of process, we first need to
        // verify if the queue of running processes is not empty
        //and then process state is equal to false--> which indicates it has never
        // been started.

        if (!this.running_processes.isEmpty()) {
            for (Process p : this.running_processes) {
                if (!p.getProcess_state()) {
                    file_writer.write("Clock: " + SchedulerCycle.get_time()+ p.getServiceTime() + "," + p.getId() + ": Started.");
                    System.out.println("Clock: " + SchedulerCycle.get_time() + "," + p.getId() + ": Started.");
                    p.setProcess_state(false);


                }

            }

        }
    }

    void finish_process() throws InterruptedException, IOException {
        //When we finish a process, we first need to release the core and update all the queue.
        if (!this.running_processes.isEmpty()) {
            for (Process p : this.running_processes) {

                // if (p.equals(process))
                {
                    this.running_processes.remove(p);
                    this.finished_processes.add(p);
                    this.all_processes.remove(p);
                    file_writer.write("Clock: " + (SchedulerCycle.get_time()+ p.getServiceTime()*1000) + "," + p.getId() + ": Finished.");
                    System.out.println("Clock: " + (SchedulerCycle.get_time()+ p.getServiceTime()*1000) + "," + p.getId() + ": Finished.");

                }
            }
            //finish the process by removing it from the queue of all processes and the ready queue.

        }

    }

    void fill_ready_queue() throws InterruptedException //we add all the processes with arrival
    // time equal to clock time.
    {
        for (Process process : this.all_processes) {
            if (process.getArrival_time() * 1000 <= SchedulerCycle.get_time()) {
                this.ready_processes.add(process);
            }
        }

    }

    /*This function is used to update the queues: finish and ready
     *if any process  */
    void updateQueues() throws InterruptedException {
        for (Process process : this.ready_processes) {
            /*the process' remaining time = 0 --> process is done. We have to
            move it to the finish queue.*/
            if (process.getRemainingTime() == 0) {
                this.finished_processes.add(process);
            }
        }
    }


    @Override
    public void run() {

        while (!this.all_processes.isEmpty())
        try {
            SchedulerCycle.tick();/*increment the time*/
            fill_ready_queue();/*fill the ready queue*/
            assign_core();/*assign core to ready processes*/
            start_process();
            finish_process();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }


      //   while (!this.all_processes.isEmpty())
        //{

            //startSchedulingProcesses();
            //while we still have processes in our queue.
            }

        //}

    }

