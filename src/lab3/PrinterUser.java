package lab3;

import java.util.Random;

public class PrinterUser implements Runnable{

    IPrinterMonitor printerMonitor;
    int times;
    Random random = new Random();
    int MAX_SLEEP = 100;
    int task;
    int name;

    public PrinterUser(IPrinterMonitor printerMonitor, int times, int name) {
        this.printerMonitor = printerMonitor;
        this.times = times;
        this.task = 0;
        this.name = name;
    }

    @Override
    public void run() {
        for(int i = 0; i< times; i++){
            create_task();
            int x = printerMonitor.take();
            print_task(x);
            printerMonitor.release(x);
        }
    }

    private void print_task(int x) {
        System.out.println(name+" print task " + task + " on " + x);
        try {
            Thread.sleep(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void create_task() {
        try {
            task = random.nextInt(MAX_SLEEP);
            Thread.sleep(task);
            //System.out.println("Created task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
