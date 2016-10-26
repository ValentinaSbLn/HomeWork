package threads;

/**
 * Created by Valentina on 26.10.2016.
 */
public class GeometricProgression implements Runnable {
    private final int num;

    public GeometricProgression(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        int result = 6;
        for (int j = 1; j < num; j++) {
            result = result * -2;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        System.out.println(Thread.currentThread() + " Result " + num + " = " + result);
        //System.out.println(" Result " + num + " = " + result);
    }
}

