package com.FirdawsBouzeghaya;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /**************************************************************************************
         Read the different lines first the text file and assign them to different variables to pass
         through the different classes constructors.
         the first line will always be the number of cores available to use.
         the second line is always the number of processes available in the system.
         *************************************************************************************/
        Scanner sc_commands = new Scanner(new BufferedReader(new FileReader("src/com/FirdawsBouzeghaya/commands.txt")));
        Scanner sc_memconfig = new Scanner(new BufferedReader(new FileReader("src/com/FirdawsBouzeghaya/memconfig.txt")));
        Scanner sc_processes = new Scanner(new BufferedReader(new FileReader("src/com/FirdawsBouzeghaya/processes.txt")));
        int number_cores = Integer.parseInt(sc_processes.nextLine());//the first line of our processes.txt
                                                 // file is the number of cores in our system
        int number_processes = Integer.parseInt(sc_processes.nextLine()); //the second line of our processes.txt
                                                      // is the number of processes in the system.
        String memory_size = " ";

        Queue<Command>commands_list = new LinkedList<>();// this queue list stores the different
                                                            //commands that we read from the commands.txt file.

        Queue<Process>processes = new LinkedList<>(); //this queue is used to store all the processes available
                                                     // in the system
        /* Get the list of commands from commands.txt file*/
        while (sc_commands.hasNextLine())
        {
            String command_line = sc_commands.nextLine();

                String command_api = command_line.split(" ")[0];
                String variable_id = command_line.split(" ")[1];
                if (command_api.equals("Store")) {
                    int variable_value = Integer.parseInt(command_line.split(" ")[2]);
                    Command command = new Command(command_api, variable_id, variable_value);
                    commands_list.add(command);
                }
                else
                {
                    Command command = new Command(command_api,variable_id,-1);
                    commands_list.add(command);
                }

        }
        /* Get the size of our main memory*/
        while(sc_memconfig.hasNextLine())
        {
            memory_size = sc_memconfig.next();
            //System.out.println(memory_size);
        }
        while (sc_processes.hasNextLine())
        {
            String process_line = sc_processes.nextLine();
            int arrival_time = Integer.parseInt(process_line.split(" ")[0]);
            int burst_time =   Integer.parseInt(process_line.split(" ")[1]);
            Process process = new Process(arrival_time,burst_time);
            processes.add(process);
        }


        try {
            SchedulerCycle clock =  new SchedulerCycle(0,true);

            Scheduler scheduler = new Scheduler(processes,number_cores);//create a scheduler object
                                                                         // and pass to it the list of
                                                                         // processes available in the
            MemoryManager.commands_list = commands_list;
            MemoryManager memoryManager = new MemoryManager(Integer.parseInt(memory_size));
            scheduler.start();
            memoryManager.start();
            clock.start();
            memoryManager.join();
            scheduler.join();

            clock.set_start_clock(false);
            clock.join();

           // schedulerCycle.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        sc_commands.close();
        sc_memconfig.close();
        sc_processes.close();


    }
}