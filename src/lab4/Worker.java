package lab4;

public class Worker implements  Runnable {

    final ProductBuffer productBuffer;
    int status;
    int doingTime;
    int times;
    int index;

    public Worker(ProductBuffer productBuffer, int status, int doingTime, int times) {
        this.productBuffer = productBuffer;
        this.status = status;
        this.doingTime = doingTime;
        this.index = 0;
        this.times = times;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++){
            synchronized (productBuffer.getProducts()[index]){
                while(productBuffer.getProducts()[index].status != status){
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
                productBuffer.getProducts()[index].status++;
                productBuffer.getProducts()[index].notifyAll();
                System.out.println("Change status product at" + index + " to status: " + (status+1));
            }
            index++;
            if (index == productBuffer.getProducts().length) index = 0;
        }
    }
}
