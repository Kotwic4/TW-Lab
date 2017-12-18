package lab2;

import lab1.MyNumber;
import lab1.Operation;

import java.util.LinkedList;

public class Lab2 {
    public static void main(){
        //ex1();
        ex2();
    }

    private static void ex1(){
        MyNumber target = new MyNumber(0L);
        ISemaphore ISemaphore = new BinaryISemaphore(false);
        CalcThread inc = new CalcThread(Operation.PLUS, 1L,target,1000000L, ISemaphore);
        CalcThread dec = new CalcThread(Operation.MINUS, 1L,target,1000000L, ISemaphore);
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

    private static void ex2(){
        Shop shop = new Shop(2);
        LinkedList<Client> clientArray = new LinkedList<>();
        clientArray.add(new Client(shop,"Ala"));
        clientArray.add(new Client(shop,"Bob"));
        clientArray.add(new Client(shop,"John"));
        clientArray.add(new Client(shop,"Alice"));
        for(Client client : clientArray){
            client.start();
        }
        for(Client client : clientArray){
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
