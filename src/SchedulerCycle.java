import java.util.concurrent.Semaphore;

public class SchedulerCycle extends Thread {

    /* The class time extends from class thread as required in the handout*/
    // Use one binary semaphore that ensures the access to the shared variable time between processes.
    volatile  static int time; /*we initially start at time =0*/
    int permits = 0;
    static Semaphore semaphore; // use a binary semaphore to protect the critical section.

    public SchedulerCycle() // constructor that has no parameters.
    {
        time =0;
        semaphore = new Semaphore(permits);
    }
    static public void count()
    {
        time++;
    }
    public int get_time() throws InterruptedException {
        semaphore.acquire();
        try {
            return time*1000;
        } finally {
            semaphore.release(); // once you're a process is done accessing time, release its semaphore.
        }
    }
    static void tick() throws InterruptedException {
        semaphore.acquire();
        try
        {
            //Thread.sleep(1000);
            time++;
        } finally { // the finally block always executes when the try block exits,
                    // as it ensures that the finally block is executed even though there might be an exception.
            semaphore.release();
        }

    }


    @Override
    public void run(){

        try {
            tick();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
