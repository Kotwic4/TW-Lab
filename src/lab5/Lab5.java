package lab5;

import java.util.concurrent.ExecutionException;

public class Lab5
{
    public static void main(){
        Mandelbrot mandelbrot = null;
        try {
            mandelbrot = new Mandelbrot(800);
            mandelbrot.setVisible(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
