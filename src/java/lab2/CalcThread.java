package lab2;

import lab1.MyNumber;
import lab1.Operation;

public class CalcThread extends Thread {
    private Operation operation;
    private Long value;
    private final MyNumber target;
    private Long times;
    private ISemaphore ISemaphore;

    public CalcThread(Operation operation, Long value, MyNumber target, Long times, ISemaphore ISemaphore) {
        super();
        this.operation = operation;
        this.value = value;
        this.target = target;
        this.times = times;
        this.ISemaphore = ISemaphore;
    }

    @Override
    public void run() {
        for(Long i = 0L; i < times; i++){
            ISemaphore.P();
            calc();
            ISemaphore.V();
        }
    }

    private void calc(){
        switch(operation){
            case PLUS:
                target.add(value);
                break;
            case MINUS:
                target.subtract(value);
                break;
        }
    }

}
