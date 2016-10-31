package threads2.executionManager;


/**
 * Created by Valentina on 28.10.2016.
 */
public class ExecutionManagerImpl implements ExecutionManager{
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        ThreadPoolWithContext context = new ThreadPoolWithContext(callback, tasks);
        context.start();
        context.runCallBack();
        return context;
    }
}
