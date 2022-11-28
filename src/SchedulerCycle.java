import java.util.TimerTask;

public class SchedulerCycle extends TimerTask {

    /*Creating a static variable to keep track of the scheduler cycle of all processes */
    /*All processes created will have the same time, but different ready time (arrival time). */
  volatile  static int seconds = 0; /*we initially start at time =0*/

    static public void count()
    {
        seconds++;
    }
    static public int getTime()
    {
        return seconds;
    }
    static void tick()
    {
        try {
            Thread.sleep(1000);
            seconds++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){

        tick();
    }
}
