package lab4;

public class Producent implements Runnable{

    final ProductBuffer productBuffer;
    int doingTime;
    int times;
    int index;

    public Producent(ProductBuffer productBuffer, int doingTime, int times) {
        this.productBuffer = productBuffer;
        this.doingTime = doingTime;
        this.index = 0;
        this.times = times;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++){
            synchronized (productBuffer.getProducts()[index]){
                while(productBuffer.getProducts()[index].status != -1){
                    try {
                        productBuffer.getProducts()[index].wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(doingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                productBuffer.getProducts()[index].status = 0;
                productBuffer.getProducts()[index].notifyAll();
                System.out.println("Create product at" + index);
            }
            index++;
            if (index == productBuffer.getProducts().length) index = 0;
        }
    }
}
