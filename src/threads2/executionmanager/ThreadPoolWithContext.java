package threads2.executionmanager;


import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Valentina on 28.10.2016.
 */
public class ThreadPoolWithContext implements Context, ThreadPool {
    private final AtomicInteger completedTaskCount = new AtomicInteger(0);
    private final AtomicInteger failedTaskCount = new AtomicInteger(0);
    private int interruptedTaskCount;
    private final int SIZE;

    private final Runnable callback;
    private final Queue<Runnable> taskList;
    private final List<Thread> threads = new ArrayList<>();
    private volatile CountDownLatch countDown;

    public ThreadPoolWithContext(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        SIZE = tasks.length;
        taskList = new LinkedList<>();
        taskList.addAll(Arrays.asList(tasks));
        countDown = new CountDownLatch(SIZE);
    }

    public void start() {
        CallBack clb = new CallBack();
        clb.start();

        for (int i = 0; i < SIZE; i++) {
            threads.add(new Worker());
            threads.get(i).start();
        }

    }

    public void interrupt() {
        synchronized (threads) {
            threads.forEach(Thread::interrupt);
        }
        interruptedTaskCount = taskList.size();
    }


    @Override
    public int getCompletedTaskCount() {
        return completedTaskCount.get();
    }

    private synchronized void addCompletedTaskCount() {
        completedTaskCount.incrementAndGet();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCount.get();
    }

    private synchronized void addFailedTaskCount() {
        completedTaskCount.incrementAndGet();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTaskCount;
    }

    @Override
    public boolean isFinished() {
        return SIZE == getCompletedTaskCount() + getInterruptedTaskCount();
    }

    private class CallBack extends Thread {
        @Override
        public void run() {
            try {
                countDown.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callback.run();

        }
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            Runnable r;
            synchronized (taskList) {
                if (Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                    return;
                }
                r = (Runnable) taskList.remove();
            }
            try {
                r.run();
                addCompletedTaskCount();
                countDown.countDown();
            } catch (RuntimeException e) {
                addFailedTaskCount();
            }
        }
    }

}
