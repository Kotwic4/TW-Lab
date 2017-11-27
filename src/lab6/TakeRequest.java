package lab6;

import java.util.concurrent.CompletableFuture;

public class TakeRequest implements MethodRequest {

    private Servant servant;
    private CompletableFuture<String> returnValue;
    private String value;

    public TakeRequest(Servant servant, CompletableFuture<String> returnValue) {
        this.servant = servant;
        this.returnValue = returnValue;
    }


    @Override
    public boolean guard() {
        return !servant.isEmpty();
    }

    @Override
    public void call() {
        String value = servant.take();
        returnValue.complete(value);
    }

}
