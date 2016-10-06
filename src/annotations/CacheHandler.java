package annotations;

import reflections.ru.sberbank.javaschool.simplespring.PostConstruct;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentina on 02.10.2016.
 */
public class CacheHandler implements InvocationHandler {
    private final Object delegate;
    private Map<String, Map<Integer, Integer>> cache;

    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        cache = new HashMap<>();
        Method[] methods = delegate.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Cache.class)) {
                cache.put(m.getName(), new HashMap<>());
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (cache.containsKey(method.getName())) {

            if (cache.get(method.getName()).containsKey(args[0])) {
                System.out.println("Метод "+method.getName()+" кэшируется. Для аргумента "+args[0]+ " результат загружается из кэша" );
                return cache.get(method.getName()).get(args[0]);
            } else {
                Object result = method.invoke(delegate, args);
                System.out.println("Метод "+method.getName()+" кэшируется. Для аргумента "+args[0]+ " результат вычисления в кэше отсутствует. Заносим" );

                cache.get(method.getName()).put((Integer) args[0], (Integer) result);
                return result;
            }
        }
        System.out.println("Метод "+method.getName()+" не кэшируется.");
        Object result = method.invoke(delegate, args);
        return result;
    }
}