package lab5;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.*;

public class Mandelbrot extends JFrame {

    private final double ZOOM = 200;
    private BufferedImage I;
    private int witdh = 800;
    private int height = 600;

    public Mandelbrot(int maxIter, int threadsNumber) throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(0, 0, witdh, height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ExecutorService pool = Executors.newFixedThreadPool(threadsNumber);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<List<Future<Integer>>> values = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            List<Future<Integer>> list = new ArrayList<>();
            for (int x = 0; x < getWidth(); x++) {
                Future<Integer> val = pool.submit(new MandelbrotWorker(x,y,maxIter,witdh,height,ZOOM));
                list.add(val);
            }
            values.add(list);
        }
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int val = values.get(y).get(x).get();
                I.setRGB(x, y, val | (val << 8));
            }
        }
    }

    public Mandelbrot(int maxIter) throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(0, 0, witdh, height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<List<Future<Integer>>> values = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            List<Future<Integer>> list = new ArrayList<>();
            for (int x = 0; x < getWidth(); x++) {
                Future<Integer> val = pool.submit(new MandelbrotWorker(x,y,maxIter,witdh,height,ZOOM));
                list.add(val);
            }
            values.add(list);
        }
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int val = values.get(y).get(x).get();
                I.setRGB(x, y, val | (val << 8));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}