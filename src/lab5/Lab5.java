package lab5;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Lab5
{
    public static void main(){
        int[] maxIters = {800,200,100};
        int[] threadNumbers = {1,2,4,20};
        for(int maxIter : maxIters){
            for(int threadNumber : threadNumbers){
                test(maxIter,threadNumber);
            }
            test(maxIter);
        }
    }

    private static double getMean(List<Long> values)
    {
        double sum = 0.0;
        for(double x : values)
            sum += x;
        return sum/values.size();
    }

    private static double getVariance(List<Long> values)
    {
        double mean = getMean(values);
        double temp = 0;
        for(double x :values)
            temp += (x-mean)*(x-mean);
        return temp/(values.size()-1);
    }

    private static double getStdDev(List<Long> values)
    {
        return Math.sqrt(getVariance(values));
    }

    private static void test(int maxIter, int threadNumber){
        List<Long> results = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Long start = System.nanoTime();
            try {
                Mandelbrot mandelbrot = new Mandelbrot(maxIter,threadNumber);
                mandelbrot.setVisible(true);
                mandelbrot.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Long end = System.nanoTime();
            results.add(end-start);
        }
        System.out.println(String.format("maxIter: %d, threadNumber: %d, mean: %f, dev: %f",
                maxIter,threadNumber,getMean(results),getStdDev(results)));
    }

    private static void test(int maxIter){
        List<Long> results = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Long start = System.nanoTime();
            try {
                Mandelbrot mandelbrot = new Mandelbrot(maxIter);
                mandelbrot.setVisible(true);
                mandelbrot.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Long end = System.nanoTime();
            results.add(end-start);
        }
        System.out.println(String.format("maxIter: %d, threadNumber: single, mean: %f, dev: %f",
                maxIter,getMean(results),getStdDev(results)));
    }
}
