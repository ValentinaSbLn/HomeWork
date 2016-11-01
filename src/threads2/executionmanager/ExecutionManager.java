package threads2.executionmanager;

/**
 * Created by Valentina on 28.10.2016.
 */
public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
