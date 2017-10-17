package lab1;

public class Lab1 {
    public static void main(){
        MyNumber target = new MyNumber(0L);
        CalcThread inc = new CalcThread(Operation.PLUS, 1L,target,100000000L);
        CalcThread dec = new CalcThread(Operation.MINUS, 1L,target,100000000L);
        inc.start();
        dec.start();
        try {
            inc.join();
            dec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(target);
    }
}
