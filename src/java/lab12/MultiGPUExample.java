package lab12;

import java.util.ArrayList;
import java.util.concurrent.*;

class GPU_MOCK_CALCULATE implements Callable<Boolean> {
    private float[][] array;
    private int deviceId;
    private int dimX;
    private int dimY;

    public GPU_MOCK_CALCULATE(float[][] array, int deviceId, int dimX, int dimY) {
        this.array = array;
        this.deviceId = deviceId;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    @Override
    public Boolean call() throws Exception {
        for (int y = 1; y < dimY - 1; ++y) {
            for (int x = 1; x < dimX - 1; ++x) {
                array[deviceId][y * dimX + x] += array[deviceId][(y - 1) * dimX + x] +
                        array[deviceId][(y + 1) * dimX + x] +
                        array[deviceId][y * dimX + (x - 1)] +
                        array[deviceId][(y - 1) * dimX + (x + 1)] -
                        4 * array[deviceId][(y - 1) * dimX + x];
            }
        }
        return new Boolean(true);
    }
}

class GPU_MOCK_SEND_L_TO_H implements Callable<Boolean> {
    private float[][] array;
    private int deviceId;
    private int dimX;
    private int dimY;

    public GPU_MOCK_SEND_L_TO_H(float[][] array, int deviceId, int dimX, int dimY) {
        this.array = array;
        this.deviceId = deviceId;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    @Override
    public Boolean call() throws Exception {
        for (int x = 0; x < dimX; ++x) {
            array[deviceId + 1][x] = array[deviceId][dimY - 1];
        }
        return new Boolean(true);
    }
}

class GPU_MOCK_SEND_H_TO_L implements Callable<Boolean> {
    private float[][] array;
    private int deviceId;
    private int dimX;
    private int dimY;

    public GPU_MOCK_SEND_H_TO_L(float[][] array, int deviceId, int dimX, int dimY) {
        this.array = array;
        this.deviceId = deviceId;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    @Override
    public Boolean call() throws Exception {
        for (int x = 0; x < dimX; ++x) {
            array[deviceId - 1][x] = array[deviceId][dimY - 1];
        }
        return new Boolean(true);
    }
}

class GPU_MOCK implements Callable<Boolean>{

    private float[][] array;
    public final int deviceId;
    public int status;
    private int dimX;
    private int dimY;
    private int iterations;

    public void setPrevious(GPU_MOCK previous) {
        this.previous = previous;
    }

    public void setNext(GPU_MOCK next) {
        this.next = next;
    }

    private GPU_MOCK previous = null;
    private GPU_MOCK next = null;

    public GPU_MOCK(float[][] array, int deviceId, int dimX, int dimY, int iterations) {
        this.array = array;
        this.deviceId = deviceId;
        this.dimX = dimX;
        this.dimY = dimY;
        this.iterations = iterations;
        this.status = 0;
    }


    @Override
    public Boolean call() throws Exception {
        for(int i = 0; i < iterations; i++){
            //calc
            new GPU_MOCK_CALCULATE(array, deviceId, dimX, dimY).call();
            synchronized (this){
                status++;
                notifyAll();
            }
            if(previous != null){
                synchronized (previous){
                    while(previous.status < this.status){
                        previous.wait();
                    }
                }
                new GPU_MOCK_SEND_H_TO_L(array, deviceId, dimX, dimY).call();
            }
            if(next != null){
                synchronized (next){
                    while(next.status < this.status){
                        next.wait();
                    }
                }
                new GPU_MOCK_SEND_L_TO_H(array, deviceId, dimX, dimY).call();
            }
            synchronized (this){
                status++;
                notifyAll();
            }
            //wait for copy
            if(previous != null){
                synchronized (previous){
                    while(previous.status < this.status){
                        previous.wait();
                    }
                }
            }
            if(next != null){
                synchronized (next){
                    while(next.status < this.status){
                        next.wait();
                    }
                }
            }

        }
        return new Boolean(true);
    }
}

public class MultiGPUExample {
    static float[][] arrayDivided;
    static int numberOfGPUs = 2;
    static int dimX = 1000;
    static int dimY = 1000 / numberOfGPUs;
    static int numberOfIterations = 100;

    private static void initializeData() {
        arrayDivided = new float[numberOfGPUs][dimY * dimX];
    }

    public static void main(String[] argv) throws InterruptedException, ExecutionException {
        initializeData();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfGPUs);

        long startTime = System.currentTimeMillis();

        //Execute calculations
        ArrayList<FutureTask<Boolean>> listOfFutCalc = new ArrayList<FutureTask<Boolean>>();
        ArrayList<GPU_MOCK> listOfGPU = new ArrayList<>();

        for (int j = 0; j < numberOfGPUs; ++j) {
            listOfGPU.add(new GPU_MOCK(arrayDivided, j, dimX, dimY, numberOfIterations));
        }

        for (int j = 0; j < numberOfGPUs; ++j) {
            if(j > 0){
                listOfGPU.get(j).setPrevious(listOfGPU.get(j-1));
            }
            if(j < numberOfGPUs -1){
                listOfGPU.get(j).setNext(listOfGPU.get(j+1));
            }
        }

        for (int j = 0; j < numberOfGPUs; ++j) {
            FutureTask<Boolean> futureTask = new FutureTask<Boolean>(listOfGPU.get(j));
            listOfFutCalc.add(futureTask);
            executor.execute(futureTask);
        }

        for (FutureTask<Boolean> ft : listOfFutCalc) {
            ft.get();
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + estimatedTime);
    }

}
