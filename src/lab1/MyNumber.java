package lab1;

public class MyNumber {
    private Long value;

    public MyNumber(Long value) {
        this.value = value;
    }

    public void add(Long value){
        this.value += value;
    }

    public void subtract(Long value){
        this.value -= value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MyNumber{" +
                "value=" + value +
                '}';
    }
}
