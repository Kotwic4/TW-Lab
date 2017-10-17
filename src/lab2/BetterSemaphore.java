package lab2;

public class BetterSemaphore implements ISemaphore {

    private int value;
    private int number;
    private int nextNumber;

    public BetterSemaphore(int value) {
        if(value < 0) value = 0;
        this.value = value;
        number = 0;
        nextNumber = 0;
    }

    @Override
    public synchronized void P() {
        int i = nextNumber;
        nextNumber++;
        while(value == 0 && i == number){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        value--;
    }

    @Override
    public synchronized void V() {
        value++;
        notifyAll();
    }
}
