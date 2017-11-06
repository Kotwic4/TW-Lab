package lab3;

import lab1.Buffer;
import lab1.Consumer;
import lab1.Producer;

public class Lab3 {
    public static void main(){
        //ex1();
        //ex2();
        ex3();
    }

    private static void ex2() {
        Thread[] users = new Thread[10];
        IPrinterMonitor printerMonitor = new PrinterMonitor(5);
        for(int i = 0; i < users.length; i++){
            users[i] = new Thread(new PrinterUser(printerMonitor,10,i));
        }
        for (Thread user : users) {
            user.start();
        }
    }

    private static void ex1() {
        BoundedBufferString buffer = new BoundedBufferString(2);
        Thread producer = new Thread(new Producer(buffer,10));
        Thread consumer = new Thread(new Consumer(buffer,10));
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void ex3(){

    }
}
