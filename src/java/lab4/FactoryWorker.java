package lab4;

public class FactoryWorker implements  Runnable {

    final ProductBuffer productBuffer;
    int status;
    int doingTime;
    int times;
    int index;

    public FactoryWorker(ProductBuffer productBuffer, int status, int doingTime, int times) {
        this.productBuffer = productBuffer;
        this.status = status;
        this.doingTime = doingTime;
        this.index = 0;
        this.times = times;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++){
            synchronized (productBuffer.getFactoryProducts()[index]){
                while(productBuffer.getFactoryProducts()[index].status != status){
                    try {
                        productBuffer.getFactoryProducts()[index].wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(doingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                productBuffer.getFactoryProducts()[index].status++;
                productBuffer.getFactoryProducts()[index].notifyAll();
                System.out.println("Change status product at" + index + " to status: " + (status+1));
            }
            index++;
            if (index == productBuffer.getFactoryProducts().length) index = 0;
        }
    }
}
