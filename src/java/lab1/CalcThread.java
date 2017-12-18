package lab1;

public class CalcThread extends Thread {
    private Operation operation;
    private Long value;
    private final MyNumber target;
    private Long times;

    public CalcThread(Operation operation, Long value, MyNumber target, Long times) {
        super();
        this.operation = operation;
        this.value = value;
        this.target = target;
        this.times = times;
    }

    @Override
    public void run() {
        for(Long i = 0L; i < times; i++){
            synchronized (target){
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
    }
}
