package threads;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Valentina on 26.10.2016.
 */
public class FixedThreadPool implements ThreadPool {
    private final int nThrd;
    private final PoolWorker[] threads;
    private final Queue queue;


    public FixedThreadPool(int nThrd) {
        this.nThrd = nThrd;
        queue = new ArrayDeque<>();
        threads = new PoolWorker[nThrd];
    }

    @Override
    public void start() {
        for (int i = 0; i < nThrd; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ie) {
                            System.out.println(ie);
                            Thread.currentThread().interrupt();
                        }
                    }
                    r = (Runnable) queue.remove();
                }
                try {
                    r.run();
                } catch (RuntimeException e) {
                    throw new RuntimeException("Exception for method " + Thread.currentThread() + " run()", e);
                }
            }
        }
    }
}
