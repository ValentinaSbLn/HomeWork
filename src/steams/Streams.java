package steams;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Valentina on 15.10.2016.
 */
public class Streams<T> implements StreamsInterface<T> {
    private final Collection<? extends T> data;

    private final Queue<Function<Streams, Streams>> queueFunction = new ArrayDeque<>();

    public Streams(Collection<? extends T> data) {
        this.data = data;
    }

    @Override
    public Streams<T> filter(Predicate<? super T> predicate) {
        queueFunction.add(p -> new Streams(Arrays.asList(p.data.stream().filter(e -> predicate.test((T) e)).toArray())));
        return this;
    }

    @Override
    public <R> Streams<R> transform(Function<? super T, ? extends R> function) {
        queueFunction.add(p -> {
                    List<R> funcList = new ArrayList();
                    p.data.forEach(e -> funcList.add(function.apply((T) e)));
                    return new Streams<>(funcList);
                }
        );
        return (Streams<R>) this;
    }

    @Override
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value) {
        Streams stream = this;
        while (!queueFunction.isEmpty()) {
            stream = queueFunction.remove().apply(stream);
        }
        Map<K, V> streamMap = new HashMap();
        Collection<? extends T> source = stream.data;
        source.forEach(element -> streamMap.put(key.apply(element), value.apply(element)));
        return streamMap;
    }

    public static <T> Streams<T> of(Collection<? extends T> source) {
        return new Streams<>(source);
    }
}
