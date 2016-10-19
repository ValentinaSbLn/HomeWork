package steams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Valentina on 16.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        List<Person> someCollection = Arrays.asList(new Person("Ivan", 21), new Person("Darya", 52), new Person("Igor", 12));
        Map<String, Person> mapPerson = Streams.of(someCollection)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);

        mapPerson.forEach((k,v)-> System.out.println(k+" "+v));

        List<Integer> intCollection = Arrays.asList(5,12,20,45);

        Map<String, Integer> mapInteger = Streams.of(intCollection)
                .filter(i->i%3==0)
                .transform(i->i*2)
                .toMap(i -> i.toString(), i -> i/2 );

        mapInteger.forEach((k,v)-> System.out.println(k+" "+v));

    }
}
