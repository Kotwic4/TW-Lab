package lab3;

import lab1.IBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T>{

    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final T[] items;
    private int putptr, takeptr, count;

    public BoundedBuffer(int size){
        items = (T[]) new Object[size];
        for(int i = 0; i < size; i++){
            items[i] = null;
        }
        count = 0;
        putptr = 0;
        takeptr = 0;
    }

    public T take() {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            T x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void put(T s) {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = s;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
