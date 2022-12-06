package com.FirdawsBouzeghaya;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Scanner;

public class MemoryManager extends Thread{
    private int main_memory_size;
    private int number_of_used_page = 0;
    private ArrayList<Page>main_memory;
    private File vm_disk = new File("src/com/FirdawsBouzeghaya/vm.txt");
    FileWriter file_writer = new FileWriter(vm_disk, false);
    ArrayList<String>commands_list; // this commands list should store all the commands
    // obtained from the commands.txt file
    // To read from the text file
    Scanner scan_disk = new Scanner(vm_disk);

    public MemoryManager(int main_memory_size,ArrayList<String>commands_list) throws IOException {
        this.main_memory = new ArrayList<>(main_memory_size);
        this.main_memory_size = main_memory_size;
        this.commands_list = commands_list;

    }

    //Reading commands should be synchronized, as we only want to allow one process at a time.
    //Extract the function that the process will execute
    public synchronized void extract_from_commands(String variable_id, long variable_value, ArrayList<String>commands_list)
    {
        for (String command: this.commands_list) {
            switch (command) {
                case "Store":
                    Store(variable_id, variable_value);
                    break;
                case "Release":
                    Release(variable_id);
                    break;
                case "Lookup":
                    // Lookup(variable_id);
                    break;
                default:
                    // do nothing.
            }
        }
    }

    public synchronized void Store(String variable_id, long variable_value) {
        // Check if the main memory has empty pages
        Page page = new Page(variable_id,variable_value);
        if (number_of_used_page < main_memory_size) {
            //Store the new variable in a page in the main memory.
            main_memory.add(page);
            number_of_used_page++;
        }
        else // Store in the disk space in the vm.txt file.
        {
            try {
                store_in_disk(variable_id,variable_value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*does storing into the vm.txt file have to be synchronized ?*/
    private synchronized void store_in_disk(String variable_id,long variable_value) throws IOException {
        //Store the processes.txt variable and id into a vm.txt file to represent a disk space.
        file_writer.write( variable_id +" "+variable_value);
        file_writer.write("\n");
        file_writer.close();
    }

    public synchronized void Release(String variable_id) {
        /*This function removes the variable id and its value from the memory and releases
         * the holding page --> empty it.*/
        /* We first need to find the assigned page to this variable by looping through
         * the array of pages. If we have a match --> remove it from the array and decrease
         * the number of pages used and break from the loop*/
        for (Page page : main_memory){
            if(page.get_variable_id().equals(variable_id))
            {
                main_memory.remove(page);
                number_of_used_page--;
                break;
            }
        }
    }

    public long Lookup(String variable_id) throws InterruptedException, IOException {
        // Check if the variable id exists in the main memory
        // loop through the pages


        for (Page page : main_memory) { // for every page in the main memory search for the page

            if (page.get_variable_id().equals(variable_id))
            { // if it exists

                System.out.println("Clock: " + SchedulerCycle.get_time() + "Process"
                                  + page.get_process_id() + "Lookup: Variable" + variable_id
                                  + " Value " + page.get_variable_value() + "\n");

                file_writer.write("Clock: " + SchedulerCycle.get_time() + "Process"
                                + page.get_process_id() + "Lookup: Variable" + variable_id
                                + " Value " + page.get_variable_value() + "\n");

                return page.get_variable_value(); // if page exists in main memory return its value
            }

            else // Search in the Disk using the swap function
            {
               // while (scan_disk.hasNextLine()) {
                    if (page.get_variable_id().equals(variable_id) && number_of_used_page <= main_memory_size) // if we have less than 2 pages in main memory.
                                                                                                              // (there's still more space)
                    {
                        Store(variable_id, page.get_variable_value());

                    }
                    else Swap(page);
                //}
                System.out.println("Clock: "+ SchedulerCycle.get_time()+ "Process"+ page.get_process_id()+ "Lookup: Variable"+ variable_id+ "Value "+ page.get_variable_value()+ "\n");
            }

        }
        return -1; // if page does not exist in the main memory but does in the vm.txt
    }

    // The swap function is responsible of swapping a page from the Disk to the main memory
    // It is used in the look-up function to look for a page that needs to be in the main memory.
    // So, here we are checking if the page exists in the disk or not
    // if it does, then we need to swap it with the least time accessed page
    public synchronized void Swap(Page page) throws InterruptedException, IOException {
        //I don't think we need a page as an argument!
        //Check if the disk has the page

        while (scan_disk.hasNextLine()) //open the vm.txt file and go through it
        {

            //Save the line in an array of String and split the values
            String [] values = scan_disk.nextLine().split(" ");
            if (values[0].equals(page.get_variable_id())) // I am not sure if we need a page object as an argument or just the variable ID
            {
                long value_long = Long.parseLong(values[1]);
                for (int i=0; i<main_memory_size; i++)
                {
                    for (Page page1 : main_memory)
                    {
                        if(page1.get_last_access_time()*1000 < SchedulerCycle.get_time())
                        {
                            Release(page1.get_variable_id()); // delete the current page from main memory
                            Store(page.get_variable_id(),page.get_variable_value());
                            System.out.println("Clock: "+ SchedulerCycle.get_time()+ "Memory Manager,"+" SWAP:"+" Variable " + page.get_variable_id()+ "with "+ "Variable "+ page.get_variable_value()+ "\n");
                            file_writer.write("Clock: "+ SchedulerCycle.get_time()+ "Memory Manager,"+" SWAP:"+" Variable " + page.get_variable_id()+ "with "+ "Variable "+ page.get_variable_value()+ "\n");
                        }
                    }
                }

            }
            else {
                System.out.println("The page does not exist in the main memory or in the disk. \n");
            }
        }
        scan_disk.close();
    }

    @Override
    public void run() {
/*
        while (!commands_list.isEmpty())
        {
            // extract_from_commands;
        }*/
    }
}