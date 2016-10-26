package threads;

/**
 * Created by Valentina on 26.10.2016.
 */
public interface ThreadPool {
    /**
     *Метод запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди
     */
    void start(); //

    /**
     * Метод складывает задание в очередь. Освободившийся поток должен выполнить это задание. Каждое задание должны быть выполнено ровно 1 раз
     * @param runnable
     */
    void execute(Runnable runnable);

}
