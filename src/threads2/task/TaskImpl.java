package threads2.task;

import java.util.concurrent.Callable;

/**
 * Created by Valentina on 26.10.2016.
 */
public class TaskImpl<T> implements Task {
    private volatile T result;
    private final Callable<? extends T> callable;
    private volatile boolean exception = false;

    public TaskImpl(Callable<? extends T> callable) {

        this.callable = callable;
    }

    public T get()  {
        if (result == null) {
            if (exception)
                throw new ResultNotCompletedException("Task closed with exception");
            synchronized (this) {
                if (result == null) {
                    try {
                        result = callable.call();
                    } catch (Exception ex) {
                        exception = true;
                        throw new ResultNotCompletedException(ex.getMessage());
                    }
                }
            }
        }
        return result;
    }
}
