package threads2.executionManager;

/**
 * Created by Valentina on 28.10.2016.
 */
public interface Context {

    int getCompletedTaskCount();

    int getFailedTaskCount();

    int getInterruptedTaskCount();

    void interrupt();

    boolean isFinished();

}
