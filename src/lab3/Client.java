package lab3;

import java.util.Random;

public class Client implements Runnable {

    int number;
    IWaiter waiter;
    Random random = new Random();
    int MAX_SLEEP = 100;
    int times;

    public Client(IWaiter waiter, int number, int times){
        this.waiter = waiter;
        this.number = number;
        this.times = times;
    }


    @Override
    public void run() {
        for(int i = 0; i< times; i++){
            ownStaff();
            System.out.println(number+" wait for table");
            waiter.takeTable(number/2);
            eat();
            waiter.releaseTable();
            System.out.println(number+" release table");
        }
    }

    private void eat() {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(number+" finises eat");
    }

    private void ownStaff() {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(number+" finishes doing own staff");
    }


}
