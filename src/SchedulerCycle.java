public class SchedulerCycle extends Thread {

    /*Creating a static variable to keep track of the scheduler cycle of all processes */
    /*All processes created will have the same time, but different ready time (arrival time). */
    static int time = 0; /*we initially start at time =0*/

    static public void count()
    {
        time++;
    }
    static public int getTime()
    {
        return time;
    }
    static void tick(int quantum)
    {
        try {
            Thread.sleep(1000);
            time = time+quantum;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){

        tick();
    }
}
