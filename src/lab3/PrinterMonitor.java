package lab3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor implements IPrinterMonitor{

    private Queue<Integer> free;
    private PrinterStatus[] printers;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public PrinterMonitor(int size){
        printers = new PrinterStatus[size];
        free = new LinkedList<>();
        for(int i = 0; i < size; i++){
            printers[i] = PrinterStatus.Free;
            free.add(i);
        }
    }

    @Override
    public int take() {
        lock.lock();
        try {
            while (free.size() == 0)
                notEmpty.await();
            int x = free.remove();
            printers[x] = PrinterStatus.Taken;
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release(int i) {
        lock.lock();
        try{
            if(printers[i] == PrinterStatus.Free) throw new RuntimeException("You cannot release what is free");
            printers[i] = PrinterStatus.Free;
            free.add(i);
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }
}
