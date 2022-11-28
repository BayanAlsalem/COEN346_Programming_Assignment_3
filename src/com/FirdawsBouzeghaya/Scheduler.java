package com.FirdawsBouzeghaya;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler extends Thread{
    //This class is somehow similar to the Scheduler class
    // implemented in the 2nd programming assignment.
    private Queue<Process> ready_processes;
    private Queue<Process> all_processes;
    private int number_cores;

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
        this.all_processes = processes;
        this.number_cores = number_cores;
        this.ready_processes = new LinkedList<>();

    }
    void start_process(Process process)
    {
        //start the process
    }
    void finish_process(Process process)
    {
        //finish the process by removing it from the queue of all processes and the ready queue.
    }
    void fill_ready_queue()
    {
        //we need the clock to fill our ready processes to the queue.

    }
    void update_ready_queue()
    {
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
    //todo :define swap process

}
