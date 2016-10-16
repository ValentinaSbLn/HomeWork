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
        System.out.println("before item: "+ Arrays.toString(someCollection.toArray()));
        Map<String, Person> mapPerson = Streams.of(someCollection)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);
        for (Map.Entry entry : mapPerson.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("after item: "+ Arrays.toString(someCollection.toArray()));
    }
}
