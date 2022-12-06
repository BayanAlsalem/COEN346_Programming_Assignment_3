package com.FirdawsBouzeghaya;
public class SchedulerCycle extends Thread{

    /* The class time extends from class thread as required in the handout*/
    // Use one binary semaphore that ensures the access to the shared variable time between processes.
    static int start_time; /*we initially start at time =0*/
    boolean start_clock;

    public  SchedulerCycle(int start_time, boolean flag) // constructor that has no parameters.
    {
        this.start_time = start_time;
        this.start_clock = flag;
    }

    public static int get_time() throws InterruptedException
    {
        return start_time;
    }
    public synchronized static void tick() throws InterruptedException {

        Thread.sleep(1000);
        start_time=start_time+1000;
    }
    public void set_start_clock(boolean flag)
    {
        this.start_clock = flag;
    }

    @Override
    public void run()
    {
        System.out.println("Clock started: ");
        while (!start_clock)
        {
        try {
            tick();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        }


    }
}