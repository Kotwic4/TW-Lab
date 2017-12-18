package lab5;

import java.util.concurrent.Callable;

public class MandelbrotWorker implements Callable<Integer> {

    private int x,y,max,witdh,height;
    private double zoom;

    public MandelbrotWorker(int x, int y, int max, int witdh, int height, double zoom) {
        this.x = x;
        this.y = y;
        this.max = max;
        this.witdh = witdh;
        this.height = height;
        this.zoom = zoom;
    }

    @Override
    public Integer call() throws Exception {
        int iter = max;
        double zx, zy, cX, cY, tmp;
        zx = zy = 0;
        cX = (x - (witdh/2)) / zoom;
        cY = (y - (height/2)) / zoom;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }
}
