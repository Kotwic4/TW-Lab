package lab1;

public class Lab1 {
    public static void main(){
        //ex1();
        ex2();
    }

    static void ex1(){
        MyNumber target = new MyNumber(0L);
        CalcThread inc = new CalcThread(Operation.PLUS, 1L,target,100000000L);
        CalcThread dec = new CalcThread(Operation.MINUS, 1L,target,100000000L);
        inc.start();
        dec.start();
        try {
            inc.join();
            dec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(target);
    }

    static void ex2(){
        Buffer buffer = new Buffer(null);
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
