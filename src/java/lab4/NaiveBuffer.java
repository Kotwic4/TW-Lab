package lab4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements IBuffer {

    private final int maxOperations;
    private int operations;
    private int available = 0;
    boolean end = false;
    private final int m;
    private final Lock lock = new ReentrantLock();
    private final Condition prod = lock.newCondition();
    private final Condition cons = lock.newCondition();

    public NaiveBuffer(int m, int maxOperations) {
        this.m = m;
        this.maxOperations = maxOperations;
    }

    @Override
    public void get(int value) throws EndTaskException {
        lock.lock();
        try {
            while(!end && available < value) {
                cons.await();
            }
            if(operations >= maxOperations) throw new EndTaskException();
            available -= value;
            operations++;
            prod.signalAll();
            if(operations >= maxOperations) {
                cons.signalAll();
                end = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void put(int value) throws EndTaskException {
        lock.lock();
        try {
            while(!end && 2*m - available < value) {
                prod.await();
            }
            if(operations >= maxOperations) throw new EndTaskException();
            available += value;
            operations++;
            cons.signalAll();
            if(operations >= maxOperations) {
                prod.signalAll();
                end = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
