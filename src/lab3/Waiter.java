package lab3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter implements IWaiter {

    int peopleAtTable = 0;
    private Queue<Integer> queue;
    int currentPair = -1;
    boolean bothEat = false;
    private boolean peopleWait[];
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private final Condition eat = lock.newCondition();

    public Waiter(int size) {
        queue = new LinkedList<>();
        peopleWait = new boolean[size];
        for(int i = 0; i < size ; i++) peopleWait[i] = false;
    }

    @Override
    public void takeTable(int pairNumber) {
        lock.lock();
        try {
            if(peopleWait[pairNumber]){
                peopleWait[pairNumber] = false;
                if(peopleAtTable == 0){
                    currentPair = pairNumber;
                    peopleAtTable++;
                    empty.signalAll();
                    return;
                }
                else{
                    queue.add(pairNumber);
                }
            }
            else{
                peopleWait[pairNumber] = true;
            }
            while(currentPair != pairNumber){
                empty.await();
            }
            peopleAtTable++;
            if(peopleAtTable == 2){
                bothEat = true;
                eat.signalAll();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void releaseTable() {
        lock.lock();
        while(!bothEat){
            try {
                eat.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        peopleAtTable--;
        if(peopleAtTable == 0){
            if(queue.size() > 0){
                currentPair = queue.poll();
                empty.signalAll();
            }
            else{
                currentPair = -1;
            }
        }
        lock.unlock();
    }
}
