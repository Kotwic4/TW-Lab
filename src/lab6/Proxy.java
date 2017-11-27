package lab6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Proxy {

    Servant servant;
    ActivationQueue activationQueue;

    public Proxy(Servant servant, ActivationQueue activationQueue) {
        this.servant = servant;
        this.activationQueue = activationQueue;
    }

    public Proxy(int size, ActivationQueue activationQueue) {
        this(new Servant(size), activationQueue);
    }

    Future<String> take() {
        CompletableFuture<String> returnValue = new CompletableFuture<>();
        MethodRequest methodRequest = new TakeRequest(servant, returnValue);
        activationQueue.enqueue(methodRequest);
        return returnValue;
    }

    Future<Boolean> put(String value) {
        CompletableFuture<Boolean> returnValue = new CompletableFuture<>();
        MethodRequest methodRequest = new PutRequest(servant, returnValue, value);
        activationQueue.enqueue(methodRequest);
        return returnValue;
    }

}
