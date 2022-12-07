package com.FirdawsBouzeghaya;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class MemoryManager extends Thread{
    private int main_memory_size;
    private int number_of_used_page = 0;
    private ArrayList<Page>main_memory;
    private File vm_disk = new File("src/com/FirdawsBouzeghaya/vm.txt");
    FileWriter file_writer = new FileWriter(vm_disk, false);

    public static Queue<Command> commands_list; // this commands list should store all the commands
    File output_file = new File("src/com/FirdawsBouzeghaya/Output.txt");
    FileWriter file_writer_output = new FileWriter(output_file, false);

    Command command ;//this is the command that should be executed.
    // obtained from the commands.txt file
    // To read from the text file
    Scanner scan_disk = new Scanner(vm_disk);
    Semaphore semaphore = new Semaphore(1);


    public MemoryManager(int main_memory_size) throws IOException {
        this.main_memory = new ArrayList<>(main_memory_size);
        this.main_memory_size = main_memory_size;

    }

    //Reading commands should be synchronized, as we only want to allow one process at a time.
    //Extract the function that the process will execute
    public void execute_command(Command command) throws IOException, InterruptedException {

        switch (command.getCommand_name()) {
            case "Store":
               semaphore.acquire();
                Store(command.getVariable_id(), command.getVariable_value());
                semaphore.release();
                break;
            case "Release":
                semaphore.acquire();
                Release(command.getVariable_id());
                semaphore.release();
                break;
            case "Lookup":
                semaphore.acquire();
                Lookup(command.getVariable_id());
                 semaphore.release();
                break;
            default:
                // do nothing.
        }

    }

    public void Store(String variable_id, long variable_value) throws InterruptedException {
        // Check if the main memory has empty pages
        Page page = new Page(variable_id,variable_value);
       // semaphore.acquire();
        if (number_of_used_page < main_memory_size) {
           // Store the new variable in a page in the main memory.
            main_memory.add(page);
            number_of_used_page++;
           // semaphore.release();
            System.out.println(" Clock: " +assignClockTime()+ " Store: Variable "+variable_id + ", Value " + variable_value);

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
    private  void store_in_disk(String variable_id,long variable_value) throws IOException, InterruptedException {
        //Store the processes.txt variable and id into a vm.txt file to represent a disk space.
       // semaphore.acquire();
        file_writer.write( variable_id +" "+variable_value);
        file_writer.write("\n");
       // semaphore.release();
        file_writer.close();
    }

    public  void Release(String variable_id) throws InterruptedException {
        /*This function removes the variable id and its value from the memory and releases
         * the holding page --> empty it.*/
        /* We first need to find the assigned page to this variable by looping through
         * the array of pages. If we have a match --> remove it from the array and decrease
         * the number of pages used and break from the loop*/
        for (Page page : main_memory){
            if(page.get_variable_id().equals(variable_id))
            {
              //  semaphore.acquire();
                main_memory.remove(page);
                number_of_used_page--;
              //  semaphore.release();
                break;
            }
        }
    }

    public long Lookup(String variable_id) throws InterruptedException, IOException {
        // Check if the variable id exists in the main memory
        // loop through the pages

       // semaphore.acquire();
        for (Page page : main_memory) { // for every page in the main memory search for the page

            if (page.get_variable_id().equals(variable_id))
            { // if it exists

                System.out.println("Clock: " + assignClockTime() + " Process"
                        + getId() + " Lookup: Variable " + variable_id
                        + " Value " + page.get_variable_value() + "\n");

                file_writer_output.write("Clock: " + assignClockTime() + " Process"
                        + getId() + " Lookup: Variable " + variable_id
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

                System.out.println("Clock: "+ assignClockTime()+ "Process "+ getId()+ " Lookup: Variable "+ variable_id+ " Value "+ page.get_variable_value()+ "\n");

            }
            break;

        }
        //semaphore.release();
        return -1; // if page does not exist in the main memory but does in the vm.txt
    }

    // The swap function is responsible of swapping a page from the Disk to the main memory
    // It is used in the look-up function because in order to look for a page, it needs
    // to be in the main memory.
    // So, here we are checking if the page exists in the disk or not
    // if it does, then we need to swap it with the least time accessed page
    public  void Swap(Page page) throws InterruptedException, IOException {
        //I don't think we need a page as an argument!
        //Check if the disk has the page

        while (scan_disk.hasNextLine()) //open the vm.txt file and go through it
        {

            //semaphore.acquire();
            //Save the line in an array of String and split the values
            String [] values = scan_disk.nextLine().split(" ");
            if (values[0].equals(page.get_variable_id()))
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
                            System.out.println("Clock: "+ assignClockTime()+ " Memory Manager,"+" SWAP:"+" Variable " + page.get_variable_id()+ " with "+ " Variable "+ page.get_variable_value()+ "\n");
                            file_writer_output.write("Clock: "+ assignClockTime()+ " Memory Manager,"+" SWAP:"+" Variable " + page.get_variable_id()+ "with "+ "Variable "+ page.get_variable_value()+ "\n");

                           // break;
                        }

                    }

                }


            }
           // semaphore.release();

        }

      //  scan_disk.close();
        file_writer_output.close();
    }


    @Override
    public void run() {
        while (!commands_list.isEmpty()) {
            try {
                execute_command(commands_list.remove());
                //
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public int assignClockTime() throws InterruptedException {

        SchedulerCycle s = new SchedulerCycle(0,true);
        SchedulerCycle.tick();
        return SchedulerCycle.get_time();


    }

}