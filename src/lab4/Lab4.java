package lab4;

public class Lab4 {
    public static void main(){
        //ex1();
        ex2();
    }

    private static void ex1() {
        ProductBuffer productBuffer = new ProductBuffer(10);
        Thread producent = new Thread(new FactoryProducent(productBuffer,1,20));
        producent.start();
        Thread consumer = new Thread(new FactoryConsumer(productBuffer,5,1,20));
        consumer.start();
        Thread workers[] = new Thread[5];
        for(int i = 0; i < 5; i++){
            workers[i] = new Thread(new FactoryWorker(productBuffer,i,1,20));
            workers[i].start();
        }
    }

    private static void ex2(){
        test(10);
        test(100);
        test(1000);
    }

    private static void test(int n){
        int m = 1000;
        naiveTest(n,m);
        betterTest(n,m);
        m = 10000;
        naiveTest(n,m);
        betterTest(n,m);
        m = 100000;
        naiveTest(n,m);
        betterTest(n,m);
    }

    private static void naiveTest(int n, int m){
        IBuffer buffer = new NaiveBuffer(m,1000000);
        Timer timer = new Timer();
        test(n,m,buffer,timer);
        System.out.println(String.format("Naive n:%d, m:%d, getSum:%d, getCount:%d, putSum:%d, putCount:%d",
                n,m,timer.sumGetTime,timer.getTimeCounter,timer.sumPutTime,timer.putTimeCounter));
    }

    private static void betterTest(int n, int m){
        IBuffer buffer = new Buffer(m,1000000);
        Timer timer = new Timer();
        test(n,m,buffer,timer);
        System.out.println(String.format("Better n:%d, m:%d, getSum:%d, getCount:%d, putSum:%d, putCount:%d",
                n,m,timer.sumGetTime,timer.getTimeCounter,timer.sumPutTime,timer.putTimeCounter));
    }

    private static void test(int n, int m, IBuffer buffer, Timer timer){
        Thread producents[] = new Thread[n];
        Thread consumers[] = new Thread[n];
        for(int i = 0; i < n; i++){
            producents[i] = new Thread(new Producer(m,buffer,timer));
            producents[i].start();
            consumers[i] = new Thread(new Consumer(m,buffer,timer));
            consumers[i].start();
        }
        for(int i = 0; i < n; i++){
            try {
                producents[i].join();
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
