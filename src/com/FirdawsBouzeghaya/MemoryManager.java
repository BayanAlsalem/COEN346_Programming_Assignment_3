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
    Page page;
    SchedulerCycle clock;
    Process process;

    public MemoryManager(int main_memory_size,ArrayList<String>commands_list) throws IOException {
        this.main_memory = new ArrayList<>(main_memory_size);
        this.main_memory_size = main_memory_size;
        this.commands_list = commands_list;

    }

    //Extract the function that the process will execute
    public void extract_from_commands(String variable_id, long variable_value, ArrayList commands_list)
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
                    Lookup(variable_id);
                    break;
                default:
                    // code block
            }
        }
    }

    public void Store(String variable_id, long variable_value) {
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
    private void store_in_disk(String variable_id,long variable_value) throws IOException {
        //Store the processes.txt variable and id into a vm.txt file to represent a disk space.
        file_writer.write( variable_id +" "+variable_value);
        file_writer.write("\n");
        file_writer.close();
    }

    public void Release(String variable_id) {
        /*This function removes the variable id and its value from the memory and releases
         * the holding page --> empty it.*/
        /* We first need to find the assigned page to this variable by looping through
         * the array of pages. If we have a match --> remove it from the array and decrease
         * the number of pages used and break from the loop*/
        for (Page page : main_memory){
            if(page.getVariable_id().equals(variable_id))
            {
                main_memory.remove(page);
                number_of_used_page--;
                break;
            }
        }
    }

    public int Lookup(String variable_id) {
        // Check if the variable id exists in the main memory
        // loop through the pages
        for (int i = 0; i < main_memory.size(); i++) {
           if (page.getVariable_id().equals(variable_id))
           {
            //   System.out.println("Clock: ", clock, "com.FirdawsBouzeghaya.Process", process.getID(), "Lookup: Variable", variable_id, "Value ", page.getVariable_value(), "\n");
           }
           else // Search in the Disk using the swap function
           {
               while (scan_disk.hasNextLine()) {
                   if (page.getVariable_id().equals(variable_id))
                   {
                      // Swap(page, clock);
                       break;
                   }
               }
             //  System.out.println("Clock: ", clock, "com.FirdawsBouzeghaya.Process", process.getID(), "Lookup: Variable", variable_id, "Value ", page.getVariable_value(), "\n");
           }

           }
        return 0;
    }

    // The swap function is repsonsible of swapping a page from the Disk to the main memory
    // It is used in the look-up function because in order to look for a page, it needs
    // to be in the main memory.
    // So, here we are looking if the page exists in the disk or not
    // if it does, then we need to swap it with the least time accessed page
   /*
    public void Swap(Page page, Clock current_clock_time){
        //I don't think we need a page as an argument!
        //Check if the disk has the page

        while (scan_disk.hasNextLine()) //open the vm.txt file and go through it
        {
            //Save the line in an array of String and split the values
            String[] values = scan_disk.nextLine().split(" ");
            if (values[0] == page.getVariable_id()) // I am not sure if we need a page object as an argument or just the variable ID
            {
                //If we found the ID, that means it exists in the DISK!! good job
                // Now create the temperorary page because we are going to swap values
                // Since I am saving the line in an array of strings I need to convert/cast the
                // variable value from a string to LONG
                Long value_long = Long.parseLong(values[1]);
                Page page_to_swap = new Page(values[0], value_long); //this is the page we want from the DISK



                // Maybe here we can create a function that finds a page with the least accessed time?
                if (page.getLastAccess() < current_clock_time) {

                    Page temp_page = new Page(the_least_accessed_time_page); // To save the leaset accessed page from the main memory
                    the_least_accessed_time_page = page_to_swap;
                    main_memory.remove(the_least_accessed_time_page); //make a space in the main memory by removing the leaset accessed page after saving it in the DISK
                    main_memory.add(page_to_swap); //adding what we have in the disk to the main memory
                    page_to_swap = temp_page; // Save the leaset accessed page which is the temp_page to the disk
                }
            }
            else {
                System.out.println("The page does not exist in the main memory or in the disk. \n");
            }
        }
        scan_disk.close();
    }*/

    @Override
    public void run() {
        super.run();
    }
}
