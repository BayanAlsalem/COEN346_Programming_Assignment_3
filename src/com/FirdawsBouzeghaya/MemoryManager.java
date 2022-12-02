package com.FirdawsBouzeghaya;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MemoryManager extends Thread{
    private int main_memory_size;
    private int number_of_used_page = 0;
    private ArrayList<Page>main_memory;
    private File vm_disk = new File("src/com/FirdawsBouzeghaya/vm.txt");
    FileWriter file_writer = new FileWriter(vm_disk, false);
    ArrayList<String>commands_list; // this commands list should store all the commands
                                    // obtained from the commands.txt file


    public MemoryManager(int main_memory_size,ArrayList<String>commands_list) throws IOException {
        this.main_memory = new ArrayList<>(main_memory_size);
        this.main_memory_size = main_memory_size;
        this.commands_list = commands_list;

    }

    public void Store(String variable_id, int variable_value) {
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
    private void store_in_disk(String variable_id,int variable_value) throws IOException {
        //Store the processes variable and id into a vm.txt file to represent a disk space.
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
    public void select_command_type()
    {
        //This function selects which type of command has to be executed.
        for (String command: this.commands_list)
        {
            switch(command){
                case "Store":
                    //call the store function.
                    break;
                case "Lookup":
                    //call the Lookup function
                    break;
                case "Release":default:
                    //call the release function
            }
        }

    }


    public int Lookup(String variable_id) {
      /*  if (anObject[i].get_variable_id().equals(variable_id)) {
            int value = anObject[i].get_variable_value();
            return value;
        }*/
        return 0;
    }

    @Override
    public void run() {
        super.run();
    }
}
