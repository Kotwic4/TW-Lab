package lab1;

public class Buffer {

    private String buff;

    private boolean empty;

    public Buffer(String buff) {
        this.buff = buff;
        empty = buff == null;
    }

    public synchronized String take() {
        while(empty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String ans = buff;
        buff = null;
        empty = true;
        notifyAll();
        return ans;
    }

    public synchronized void put(String s) {
        while(!empty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buff = s;
        empty = false;
        notifyAll();
    }
}
