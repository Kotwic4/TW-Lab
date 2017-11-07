package lab4;

public class Lab4 {
    public static void main(){
        ex1();
        //ex2();
    }

    private static void ex1() {
        ProductBuffer productBuffer = new ProductBuffer(10);
        Thread producent = new Thread(new Producent(productBuffer,1,20));
        producent.start();
        Thread consumer = new Thread(new Consumer(productBuffer,5,1,20));
        consumer.start();
        Thread workers[] = new Thread[5];
        for(int i = 0; i < 5; i++){
            workers[i] = new Thread(new Worker(productBuffer,i,1,20));
            workers[i].start();
        }
    }
}
