public class MemoryManager extends Thread{
    int main_memory_size;
    int number_of_used_page = 0;

    public MemoryManager(int main_memory_size) {
        this.main_memory_size = main_memory_size;
    }

    public void Store(String variable_id, long variable_value) {
        // Check if the main memory has empty pages
        if (number_of_used_page < main_memory_size) {
            //Store the new variable in a page in the main memory
            number_of_used_page++;
        }
    }

    public void Release(String variable_id) {

        if (anObject.get_variable_id().equals(variable_id)) {// found

            anObject[i] = null; // delete what is at memory location
        }
    }

    public int Lookup(String variable_id) {
        if (anObject[i].get_variable_id().equals(variable_id)) {
            int value = anObject[i].get_variable_value();
            return value;
        }
    }
}
