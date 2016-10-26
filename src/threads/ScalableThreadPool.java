package threads;

/**
 * Created by Valentina on 26.10.2016.
 */

import java.util.*;

import static java.lang.Thread.State.WAITING;

public class ScalableThreadPool implements ThreadPool {
    private final int minThrd;
    private final int maxThrd;
    private int nThrd;
    private final List<ScalablePoolWorker> threads;
    private final Queue queue;


    public ScalableThreadPool(int minThrd, int maxThrd) {
        this.nThrd = minThrd;
        this.minThrd = minThrd;
        this.maxThrd = maxThrd;

        queue = new ArrayDeque<>();
        threads = new ArrayList<>();
    }

    @Override
    public void start() {
        for (int i = 0; i < nThrd; i++) {
            threads.add(new ScalablePoolWorker());
            threads.get(i).start();
        }
    }

    private void removeThreads(int n) {

        Iterator<ScalablePoolWorker> iterator = threads.iterator();
        int rem = 0;
        while (iterator.hasNext()) {
            ScalablePoolWorker thread = iterator.next();
            if (!thread.isWorking() && thread.getState() == WAITING && rem < n) {
                iterator.remove();
                rem++;
                nThrd--;
                System.out.println("Удален " + thread.getName());
                thread.interrupt();
            }
        }
    }

    public void execute(Runnable runnable) {
        synchronized (queue) {

            if (nThrd != minThrd && queue.isEmpty()) {
                removeThreads(nThrd - minThrd);
            }

            queue.offer(runnable);
            if (queue.size() > nThrd && nThrd < maxThrd) {
                threads.add(new ScalablePoolWorker());
                nThrd++;
                threads.get(threads.size() - 1).start();
                System.out.println("Добавлен поток " + threads.get(threads.size() - 1).getName());
            }
            queue.notify();
        }
    }

    private class ScalablePoolWorker extends Thread {
        private boolean work = true;

        public boolean isWorking() {
            return work;
        }

        public void run() {
            Runnable r;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                            if (Thread.currentThread().isInterrupted()) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    r = (Runnable) queue.remove();
                }
                try {

                    r.run();
                } catch (RuntimeException e) {
                    throw new RuntimeException("Exception for method " + Thread.currentThread() + " run()", e);
                } finally {
                    work = false;
                }
            }
        }

    }
}
