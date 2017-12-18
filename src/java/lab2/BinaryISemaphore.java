package lab2;

public class BinaryISemaphore implements ISemaphore {

    private boolean taken;

    public BinaryISemaphore(boolean taken) {
        this.taken = taken;
    }

    @Override
    public synchronized void V() {
        taken = false;
        notify();
    }

    @Override
    public synchronized void P() {
        while(taken){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        taken = true;
    }
}
