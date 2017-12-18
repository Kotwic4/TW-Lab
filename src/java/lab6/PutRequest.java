package lab6;

import java.util.concurrent.CompletableFuture;

public class PutRequest implements MethodRequest {

    private Servant servant;
    private CompletableFuture<Boolean> returnValue;
    private String value;

    public PutRequest(Servant servant, CompletableFuture<Boolean> returnValue, String value) {
        this.servant = servant;
        this.returnValue = returnValue;
        this.value = value;
    }


    @Override
    public boolean guard() {
        return !servant.isFull();
    }

    @Override
    public void call() {
        servant.put(value);
        returnValue.complete(Boolean.TRUE);
    }
}
