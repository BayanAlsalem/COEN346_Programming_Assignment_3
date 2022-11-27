public class Process extends Thread{

    int process_arrival_time;
    int process_execution_time;

    Page page = new Page();

    // constructor
    public Process(int process_arrival_time, int process_execution_time)
    {
        this.process_arrival_time = process_arrival_time;
        this.process_execution_time = process_execution_time;
    }


}
