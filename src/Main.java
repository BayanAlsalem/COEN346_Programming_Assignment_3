public class Main {
    public static void main(String[] args) throws InterruptedException {


        System.out.println("hELLO");
        SchedulerCycle s = new SchedulerCycle();
        s.run();
        s.join();
        s.get_time();

    }
}