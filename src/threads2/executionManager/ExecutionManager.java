package threads2.executionManager;

/**
 * Created by Valentina on 28.10.2016.
 */
public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
