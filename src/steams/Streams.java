package steams;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Valentina on 15.10.2016.
 */
public class Streams <T>{

    private final List<T> list;

    public Streams(List<T> list) {
        this.list = list;
    }

    /**
     * Статический метод, который принимает коллекцию и создает новый объект Streams
     * @param list<T>
     * @return новый объект Streams
     */
    public static <T> Streams<T> of(List<? extends T> list) {
        List<T> streamList = new ArrayList<>();
        streamList.addAll(list);
        return new Streams<>(streamList);
    }

    /**
     * Метод, который оставляет в коллекции только те элементы, которые удовлетворяют условию в лямбде.
     * @param predicate - лямбда выражение с условием для фильтрации данных
     * @return  объект Streams
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        Iterator<T> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (!predicate.test(next))
                iterator.remove();
        }
        return this;
    }

    /**
     * Метод, который преобразует элемент в другой.
     * @param function - параметр, по которому происходит преобразование
     * @return объект Streams с эелементами типа R
     */
    public <R> Streams<R> transform(Function<? super T, ? extends R> function) {
        List<R> funcList = new ArrayList();
        for (T t : this.list) {
            funcList.add(function.apply(t));
        }
        return new Streams(funcList);
    }

    /**
     *Метод, который записывает данные из Stream в Map.
     * @param key - лямбда выражение, на основе которой строится ключ Map
     * @param value - лямбда выражение, на основе которой строится значение Map
     * @return Map<K, V> streamMap
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value) {

        Map<K, V> streamMap = new HashMap();
        for (T t : this.list) {
            streamMap.put(key.apply(t), value.apply(t));
        }
        return streamMap;
    }
}
