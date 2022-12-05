package com.FirdawsBouzeghaya;
public class SchedulerCycle extends Thread{
    /* The class time extends from class thread as required in the handout*/
    // Use one binary semaphore that ensures the access to the shared variable time between processes.
    private static int time; /*we initially start at time =0*/
    private boolean clk_cycle; // the clock cycle to determine when to stop our clk.
                      // initially set to false
    static final  int incremental_time = 10;
    public  SchedulerCycle() // constructor that has no parameters.
    {
        time =0;
        clk_cycle = false;
    }

    public static int get_time() throws InterruptedException
    {
        return time;
    }
    public void set_Clk_cycle(boolean clk_cycle)
    {
        this.clk_cycle = clk_cycle;
    }
    public static void tick() throws InterruptedException {

        Thread.sleep(1000);
        time=time+1000;
    }

    @Override
    public void run()
    {
        while (!clk_cycle)
        {
            try {
                tick();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}