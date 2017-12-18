package lab4;

public class Timer {
    public long sumGetTime=0;
    public long getTimeCounter=0;
    public long sumPutTime=0;
    public long putTimeCounter=0;

    synchronized void get(long value){
        sumGetTime += value;
        getTimeCounter++;
    }

    synchronized void put(long value) {
        sumPutTime += value;
        putTimeCounter++;
    }
}
