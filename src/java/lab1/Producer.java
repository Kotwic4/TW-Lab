package lab1;

public class Producer implements Runnable {
    private IBuffer buffer;
    private int times;

    public Producer(IBuffer buffer, int times) {
        this.buffer = buffer;
        this.times = times;
    }

    public void run() {

        for(int i = 0;  i < times;   i++) {
            buffer.put("message "+i);
        }

    }
}
