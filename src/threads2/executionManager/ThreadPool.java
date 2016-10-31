package threads2.executionManager;

/**
 * Created by Valentina on 31.10.2016.
 */
public interface ThreadPool {
    void start();
    void runCallBack();
    void execute(Runnable runnable);

}
