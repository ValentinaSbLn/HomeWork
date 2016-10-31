package threads2.executionManager;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Valentina on 28.10.2016.
 */
public class ThreadPoolWithContext implements Context, ThreadPool {
    private AtomicInteger CompletedTaskCount = new AtomicInteger(0);
    private AtomicInteger FailedTaskCount = new AtomicInteger(0);
    private int InterruptedTaskCount;

    private final Object lock = new Object();
    private volatile int size;

    private final Runnable callback;
    private final Queue<Runnable> taskList;
    private final List<Thread> threads = new ArrayList<>();

    public ThreadPoolWithContext(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        size = tasks.length;
        taskList = new LinkedList<>();
        taskList.addAll(Arrays.asList(tasks));
    }

    public void start() {
        for (int i = 0; i < size; i++) {
            threads.add(new Worker());
            threads.get(i).start();
        }
        CallBack clb = new CallBack();
        clb.start();
    }

    public void interrupt() {
        synchronized (threads) {
            threads.forEach(Thread::interrupt);
        }
        InterruptedTaskCount = taskList.size();
    }

    public void runCallBack() {
        while (threads.stream().filter(Thread::isAlive).findAny().isPresent()) {
        }
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskList) {
            taskList.add(runnable);
            size = size + 1;
        }
        synchronized (threads) {
            threads.add(new Worker());
            threads.get(threads.size() - 1).start();
        }
    }


    @Override
    public int getCompletedTaskCount() {
        return CompletedTaskCount.get();
    }

    private synchronized void addCompletedTaskCount() {
        CompletedTaskCount.incrementAndGet();
    }

    @Override
    public int getFailedTaskCount() {
        return FailedTaskCount.get();
    }

    private synchronized void addFailedTaskCount() {
        CompletedTaskCount.incrementAndGet();
    }

    @Override
    public int getInterruptedTaskCount() {
        return InterruptedTaskCount;
    }

    @Override
    public boolean isFinished() {
        return size == getCompletedTaskCount() + getInterruptedTaskCount();
    }

    private class CallBack extends Thread {
        @Override
        public void run() {

            synchronized (lock) {
                try {

                    while (threads.stream().filter(Thread::isAlive).findFirst().isPresent()) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                callback.run();
            }
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
            } catch (RuntimeException e) {
                addFailedTaskCount();
            }
        }
    }

}
