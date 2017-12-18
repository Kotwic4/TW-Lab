package lab6;


public class Lab6 {
    public static void main() {
        ActivationQueue activationQueue = new ActivationQueue();
        Proxy proxy = new Proxy(1, activationQueue);
        Thread producer = new Thread(new Producer(proxy));
        Thread producer2 = new Thread(new Producer(proxy));
        Thread consumer = new Thread(new Consumer(proxy));
        Thread consumer2 = new Thread(new Consumer(proxy));
        Thread sheduler = new Thread(new Scheduler(activationQueue));
        producer.start();
        producer2.start();
        consumer.start();
        consumer2.start();
        sheduler.start();
    }

}
