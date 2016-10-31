package threads2.task;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args)  {

        TaskImpl<Integer> task = new TaskImpl<>(() -> {
            int r=(new Random()).nextInt(10);
            System.out.println("callable "+r);
            return r;
        });

        IntStream.range(0, 5)
                .mapToObj(i -> new Thread(() -> {
                    try {
                        System.out.println("task "+task.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }))
                .collect(Collectors.toList())
                .forEach(t->t.start());


    }
}


