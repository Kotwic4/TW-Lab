package lab4;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer implements IBuffer {
    private final int maxOperations;
    private int operations;
    private int available = 0;
    private final int m;
    private Boolean end = false;
    private final Lock lock = new ReentrantLock();
    private final Condition prod = lock.newCondition();
    private final Condition firstProd = lock.newCondition();
    private final Condition cons = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private Boolean firstProdFlag = false;
    private Boolean firstConsFlag = false;

    public Buffer(int m, int maxOperations) {
        this.m = m;
        this.maxOperations = maxOperations;
    }

    @Override
    public void get(int value) throws EndTaskException {
        lock.lock();
        try {
            if(firstConsFlag && !end){
                cons.await();
            }
            firstConsFlag = true;
            while (available < value && !end) {
                firstCons.await();
            }
            if (operations >= maxOperations){
                throw new EndTaskException();
            }
            available -= value;
            operations++;
            firstConsFlag = false;
            cons.signal();
            firstProd.signal();
            if (operations >= maxOperations){
                end = true;
                prod.signalAll();
                cons.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(int value) throws EndTaskException {
        lock.lock();
        try {
            if(firstProdFlag && !end){
                prod.await();
            }
            firstProdFlag = true;
            while (m * m - available < value && !end) {
                firstProd.await();
            }
            if (operations >= maxOperations){
                throw new EndTaskException();
            }
            available += value;
            operations++;
            firstProdFlag = false;
            prod.signal();
            firstCons.signal();
            if (operations >= maxOperations){
                end = true;
                prod.signalAll();
                cons.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
