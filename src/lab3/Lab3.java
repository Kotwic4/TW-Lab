package lab3;

import lab1.Buffer;
import lab1.Consumer;
import lab1.Producer;

public class Lab3 {
    public static void main(){
        ex1();
    }

    private static void ex1() {
        BoundedBuffer buffer = new BoundedBuffer(2);
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
}
