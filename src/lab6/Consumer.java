package lab6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Consumer implements Runnable {

    private Proxy proxy;

    public Consumer(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void run() {
        while (true) {
            Future<String> message = proxy.take();
            try {
                System.out.println(message.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
