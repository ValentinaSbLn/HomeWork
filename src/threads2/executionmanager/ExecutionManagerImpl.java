package threads2.executionmanager;


/**
 * Created by Valentina on 28.10.2016.
 */
public class ExecutionManagerImpl implements ExecutionManager{
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        ThreadPoolWithContext context = new ThreadPoolWithContext(callback, tasks);
        context.start();
        return context;
    }
}
