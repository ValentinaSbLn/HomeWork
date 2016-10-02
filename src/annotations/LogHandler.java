package annotations;

import reflections.ru.sberbank.javaschool.simplespring.PostConstruct;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentina on 02.10.2016.
 */
public class LogHandler implements InvocationHandler {
    private final Object delegate;
    Map<Integer, Integer> cache;

    public LogHandler(Object delegate) {
        this.delegate = delegate;
        cache=new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {

            if (cache.containsKey(args[0])) {
                System.out.println("В кеше есть");
                return cache.get(args[0]);
            } else {
                Object result = method.invoke(delegate, args);
                System.out.println("В кеше нет, добавляем");
                cache.put((Integer) args[0], (Integer) result);
                return result;
            }
        }
        System.out.println("Без кеша");
        Object result = method.invoke(delegate, args);
        return result;
    }
}