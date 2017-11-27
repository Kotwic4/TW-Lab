package lab6;

public class Servant {

    private String[] items;
    private int putptr, takeptr, count;

    public Servant(int size) {
        items = new String[size];
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        count = 0;
        putptr = 0;
        takeptr = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == items.length;
    }

    public String take() {
        String value = items[takeptr];
        if (++takeptr == items.length) takeptr = 0;
        --count;
        return value;
    }

    public void put(String value) {
        items[putptr] = value;
        if (++putptr == items.length) putptr = 0;
        ++count;
    }


}
