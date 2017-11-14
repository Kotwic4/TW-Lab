package lab5;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.*;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private int witdh = 800;
    private int height = 600;

    public Mandelbrot(int max_iter) throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(0, 0, witdh, height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ExecutorService pool = Executors.newFixedThreadPool(1000);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        List<List<Future<Integer>>> values = new LinkedList<>();
        for (int y = 0; y < getHeight(); y++) {
            List<Future<Integer>> list = new LinkedList<>();
            for (int x = 0; x < getWidth(); x++) {
                Future<Integer> val = pool.submit(new MandelbrotWorker(x,y,MAX_ITER,witdh,height,ZOOM));
                list.add(val);
            }
            values.add(list);
        }
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int iter = values.get(y).get(x).get();
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}