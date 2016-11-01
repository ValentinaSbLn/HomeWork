package threads2.executionmanager;

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
