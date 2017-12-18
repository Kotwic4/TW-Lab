package lab2;

public class Semaphore implements ISemaphore{

    private int value;

    public Semaphore(int value) {
        if(value < 0) value = 0;
        this.value = value;
    }

    @Override
    public synchronized void P() {
        while(value == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value--;
    }

    @Override
    public synchronized void V() {
        value++;
        notify();
    }
}
