package lab6;

public class Scheduler implements Runnable {

    private ActivationQueue activationQueue;

    public Scheduler(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    @Override
    public void run() {
        while (true) {
            MethodRequest request = activationQueue.dequeue();
            if (request != null) {
                if (request.guard()) {
                    request.call();
                } else {
                    activationQueue.enqueue(request);
                }
            }
        }
    }
}
