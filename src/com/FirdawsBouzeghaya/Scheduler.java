package com.FirdawsBouzeghaya;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler extends Thread{
    //This class is somehow similar to the Scheduler class
    // implemented in the 2nd programming assignment.
    private Queue<Process> ready_processes;
    private Queue<Process> all_processes;
    private Queue<Process> finished_processes;
    private Queue<Process> running_processes; /*queue that stores the processes that we are running.*/

    private int number_cores;
    private int number_available_cores;

    File output_file  = new File("src/com/FirdawsBouzeghaya/Output.txt");
    FileWriter file_writer;
    {
        try {
            file_writer = new FileWriter(output_file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scheduler(Queue<Process>processes,int number_cores)
    {   this.all_processes = new LinkedList<>();
        this.finished_processes = new LinkedList<>();
        this.all_processes = processes;
        this.number_cores = number_cores;
        this.running_processes = new LinkedList<>();
        this.ready_processes = new LinkedList<>();

    }

    public int get_number_available_cores()
    {
        //we should not have a number of available cores greater that our number of cores.
        // this will be used to start processes within the 1000ms.
        this.number_available_cores = number_cores- running_processes.size();
        return number_available_cores;
    }
    public void assign_core()
    {
        //we first need to check whether we have any process in our ready_processes queue.
        //if that's the
        if (!ready_processes.isEmpty()) {
            for (int i = 0; i < this.number_available_cores; i++) {

                Process process = ready_processes.peek();
                start_process(process);
            }
        }

    }
    void start_process(Process process)
    {

        //start the process
    }
    void finish_process(Process process)
    {
        //finish the process by removing it from the queue of all processes and the ready queue.
    }
    void fill_ready_queue() throws InterruptedException //we add all the processes with arrival
                                                        // time equal to clock time.
    {
        for(Process process: this.all_processes)
        {
            if (process.getArrival_time()*1000 <= SchedulerCycle.get_time())
            {
                this.ready_processes.add(process);

                //we have a queue that groups all processes in our system.
                //when r
            }
        }

    }
    /*This function is used to update the queues: finish and ready
    *if any process  */
    void updateQueues()
    {
        for(Process process: this.ready_processes)
        {
            /*the process' remaining time = 0 --> process is done. We have to
            move it to the finish queue.*/
            if (process.getRemainingTime() == 0)
            {
                this.finished_processes.add(process);
            }
        }

        //this function is used to check if we have any process in the ready queue
        //with a remaining time equal to 0.
        //we will pop the process from the queue
    }


    @Override
    public void run() {


    }


    //todo: use A semaphore to make sure that we are running two processes at the same time.

    //todo : define Start process()
    //todo: define finish process

}
