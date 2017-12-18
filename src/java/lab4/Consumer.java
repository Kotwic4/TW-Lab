package lab4;

import java.util.Random;

public class Consumer implements Runnable {
    private int m;
    private IBuffer buffer;
    private Timer timer;
    private Random random = new Random();

    public Consumer(int m, IBuffer buffer, Timer timer) {
        this.m = m;
        this.buffer = buffer;
        this.timer = timer;
    }

    @Override
    public void run() {
        while(true){
            Long start = System.nanoTime();
            try {
                buffer.get(random.nextInt(m));
            } catch (EndTaskException e) {
                break;
            }
            Long end = System.nanoTime();
            timer.get(end-start);
        }
    }
}
