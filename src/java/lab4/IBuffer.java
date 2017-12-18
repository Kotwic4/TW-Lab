package lab4;

public interface IBuffer {

    void get(int value) throws EndTaskException;
    void put(int value) throws EndTaskException;

}
