package lab6;

import java.util.LinkedList;
import java.util.Queue;

public class ActivationQueue {

    private Queue<MethodRequest> queue = new LinkedList<>();

    public synchronized void enqueue(MethodRequest methodRequest) {
        queue.add(methodRequest);
    }

    public synchronized MethodRequest dequeue() {
        return queue.poll();
    }

}
