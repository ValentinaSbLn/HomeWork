package steams;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Valentina on 19.10.2016.
 */
public interface StreamsInterface <T>{
    StreamsInterface<T> filter(Predicate<? super T> predicate);
    <R> StreamsInterface<R> transform(Function<? super T, ? extends R> transformer);
    <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value);
}
