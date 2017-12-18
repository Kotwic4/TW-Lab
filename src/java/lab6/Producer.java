package lab6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer implements Runnable {

    private Proxy proxy;
    private int i = 0;

    public Producer(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void run() {
        while (true) {
            Future<Boolean> boo = proxy.put("messeage" + i);
            try {
                if (boo.get()) {
                    i++;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
