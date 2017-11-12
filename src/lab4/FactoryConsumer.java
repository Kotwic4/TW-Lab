package lab4;

public class FactoryConsumer implements Runnable{

    final ProductBuffer productBuffer;
    int finishStatus;
    int doingTime;
    int times;
    int index;

    public FactoryConsumer(ProductBuffer productBuffer, int finishStatus, int doingTime, int times) {
            this.productBuffer = productBuffer;
            this.finishStatus = finishStatus;
            this.doingTime = doingTime;
            this.index = 0;
            this.times = times;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++){
            synchronized (productBuffer.getFactoryProducts()[index]){
                while(productBuffer.getFactoryProducts()[index].status != finishStatus){
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
                productBuffer.getFactoryProducts()[index].status = -1;
                productBuffer.getFactoryProducts()[index].notifyAll();
                System.out.println("Consume product at" + index);
            }
            index++;
            if (index == productBuffer.getFactoryProducts().length) index = 0;
        }
    }
}
