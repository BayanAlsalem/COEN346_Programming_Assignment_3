package com.FirdawsBouzeghaya;

public class SchedulerCycle extends Thread{

    /* The class time extends from class thread as required in the handout*/
    // Use one binary semaphore that ensures the access to the shared variable time between processes.
    static int time; /*we initially start at time =0*/
    boolean start_clock;

    public  SchedulerCycle() // constructor that has no parameters.
    {
        time =0;
        start_clock = false;
    }

    public static int get_time() throws InterruptedException
    {
        return time;
    }
    public static void tick() throws InterruptedException {

        Thread.sleep(1000);
            time=time+1000;
    }

    @Override
        public void run()
    {


            try {
                tick();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
