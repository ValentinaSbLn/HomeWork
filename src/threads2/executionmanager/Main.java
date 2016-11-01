package threads2.executionmanager;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Valentina on 28.10.2016.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable [] th = new Runnable[5];
        List<Runnable> threads = IntStream.range(0,5)
                .mapToObj(i -> new Thread(() -> {
                    System.out.println("На остановке в автобус зашло: "+((new Random()).nextInt(10))+" и вышло: "+((new Random()).nextInt(9))+" человек");
                }))
                .collect(Collectors.toList());
        for(int i=0; i<5; i++){
            th[i]=threads.get(i);
        }
        ExecutionManagerImpl emp= new ExecutionManagerImpl();
        Context cont=emp.execute(() ->System.out.println("Сколько остановок было?"), th);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Выполнено: "+cont.getCompletedTaskCount());
        System.out.println("С ошибкой: "+cont.getFailedTaskCount());
        System.out.println("Остановлено: "+cont.getInterruptedTaskCount());
        System.out.println("Закончено? "+cont.isFinished());
    }


}
