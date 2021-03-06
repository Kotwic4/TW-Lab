package lab1;

public class Consumer implements Runnable {
    private IBuffer buffer;
    private int times;

    public Consumer(IBuffer buffer, int times) {
        this.buffer = buffer;
        this.times = times;
    }

    public void run() {

        for(int i = 0;  i < times;   i++) {
            String message = buffer.take();
            System.out.println(message);
        }

    }
}
