package threads;

/**
 * Created by Valentina on 26.10.2016.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
//            ThreadPool pool = new FixedThreadPool(20);
//            pool.start();
//
//            for (int i = 1; i < 10; i++) {
//                final int finalI = i;
//                pool.execute(new GeometricProgression(finalI));
//            }
//
//
//            Thread.sleep(5_000);
//            System.out.println("Main done");

        ThreadPool pool = new ScalableThreadPool(5, 10);
        pool.start();

        for (int i = 1; i < 19; i++) {
            final int num = i;
            pool.execute(new GeometricProgression(num));
            if (i == 10) Thread.sleep(10_000);
        }
        Thread.sleep(5_000);
        System.out.println("Main done");
    }

}
