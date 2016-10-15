package annotations;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentina on 15.10.2016.
 */
public class CacheHandlerWithClass implements InvocationHandler {
    private final Object delegate;

    private Map <CacheKey, Object> cache;

    public CacheHandlerWithClass(Object delegate) {
        this.delegate = delegate;
        cache = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isAnnotationPresent(Cache.class)) {

                if (cache.containsKey(new CacheKey(method.getName(), args))) {
                    System.out.println("Метод " + method.getName() + " кэшируется. Для аргумента " + args[0] + " результат загружается из кэша");
                    return cache.get(new CacheKey(method.getName(), args));
                } else {
                    Object result = method.invoke(delegate, args);
                    System.out.println("Метод " + method.getName() + " кэшируется. Для аргумента " + args[0] + " результат вычисления в кэше отсутствует. Заносим");

                    cache.put(new CacheKey(method.getName(), args), result);
                    cacheSerialize(method, method.getDeclaredAnnotation(Cache.class).cacheType());

                    return result;
                }
        }

        System.out.println("Метод "+method.getName()+" не кэшируется.");

        Object result = method.invoke(delegate, args);
        return result;
    }



    private void cacheSerialize(Method method, CacheType cacheType)  throws Throwable {

        if (cacheType.equals(CacheType.FILE)) {

            try (FileOutputStream fos = new FileOutputStream("CacheSerialize.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fos)) {
                    out.writeObject(cache);
            }
        }
    }
}
