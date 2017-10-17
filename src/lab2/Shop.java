package lab2;

public class Shop {
    ISemaphore semaphore;

    public Shop(int n){
        if(n < 0) n = 0;
        semaphore = new Semaphore(n);
    }

    public void takeCart(){
        semaphore.P();
    }

    public void returnCart(){
        semaphore.V();
    }
}
