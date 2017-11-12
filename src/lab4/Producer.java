package lab4;

import java.util.Random;

public class Producer implements Runnable {
    private int m;
    private IBuffer buffer;
    private Timer timer;
    private Random random = new Random();

    public Producer(int m, IBuffer buffer, Timer timer) {
        this.m = m;
        this.buffer = buffer;
        this.timer = timer;
    }

    @Override
    public void run() {
        while(true){
            Long start = System.nanoTime();
            try {
                buffer.put(random.nextInt(m));
            } catch (EndTaskException e) {
                break;
            }
            Long end = System.nanoTime();
            timer.put(end-start);
        }
    }
}
