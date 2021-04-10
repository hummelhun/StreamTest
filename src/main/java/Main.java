import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Java");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.forEach(System.out::println);

        System.out.println(
        numbers.stream()
                .map(String::valueOf)
                .reduce("", String::concat)
        );

        int sum = numbers.stream()
                .filter(n -> n%2==0)
                .map(n -> n*2)
                .reduce(0, Integer::sum);
        System.out.println(sum);

        sum = numbers.stream()
                .filter(n -> n%2==0)
                .mapToInt(n -> n*2)
                .sum();
        System.out.println(sum);

        OptionalDouble avg = numbers.stream()
                .filter(n -> n%2==0)
                .mapToDouble(n -> n*2)
                .average();
        System.out.println(avg.getAsDouble());

        int first = numbers.stream()
                .filter(n -> n%2==0)
                .findFirst()
                .get();
        System.out.println(first);

        System.out.println(
                IntStream.rangeClosed(1, 100)
                         .filter(n ->n>5)
                         .filter(n-> n<9)
                         .filter(n-> n%2==1)
                         .findFirst()
                         .getAsInt()
        );
        List<String> fizzbuzz =
        IntStream.rangeClosed(1, 100)
                 .mapToObj(n -> n%5==0 ? (n%7==0 ? "Fizbuzz" : "Fizz") : (n%7==0 ? "Buzz" : String.valueOf(n)))
                 .collect(toCollection(ArrayList::new));
        fizzbuzz.forEach(System.out::println);

        Predicate<Integer> odd = i -> i%2==0;


        IntStream.rangeClosed(1, 100)
                 .filter(n ->odd.test(n))
                 .forEach(System.out::println);

        List<String> names = Arrays.asList("name7", "name2", "name0", "name10", "name6", "Bob", "Hob", "Aob");
        System.out.println(names.getClass());

        names = names.stream()
                     .sorted(Comparator.comparing(String::length)
                                       .thenComparing(String::valueOf))
                     .collect(toList());

        names.forEach(System.out::println);

        List<Integer> ints = Arrays.asList(999, 10, 1, 11, 3, 5, 6, 100, 2, 1, 1, 1);
        ints = ints.stream()
                     .sorted()
                     .collect(toList());

        ints.forEach(System.out::println);


        List<Integer> myList=
                ints.stream()
                    .filter(n -> n%3==0)
                    .collect(toList());
        myList.forEach(System.out::println);

        System.out.println(
                ints.stream()
                    .reduce(0, (pool, act) -> pool+act)
        );

        ints = ints.stream()
                .filter(n -> n%2==0)
                .map(n ->n*2)
                .collect(toList());
        System.out.println(ints);

        List<Person> people = Arrays.asList(
                new Person("Sara", Gender.FEMALE, 20),
                new Person("Sara", Gender.FEMALE, 22),
                new Person("Bob", Gender.MALE, 20),
                new Person("Paula", Gender.FEMALE, 32),
                new Person("Paul", Gender.MALE, 32),
                new Person("Jack", Gender.MALE, 2),
                new Person("Jack", Gender.MALE, 72),
                new Person("Jill", Gender.FEMALE, 11)
        );



        // Exploring Collectors by Venkat Subramaniam https://www.youtube.com/watch?v=pGroX3gmeP8

        System.out.println(
            people.stream()
                .filter(p -> p.getAge()>20)
                .collect(toList())
        );

        System.out.println(
                people.stream()
                    .collect(groupingBy(Person::getName, mapping(Person::getAge, toList())))
        );

        System.out.println(
                people.stream()
                .filter(p -> p.getAge()>30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList())

        );


        System.out.println(
        people.stream()
                .filter(p ->p.getAge()> 30)
                .map(p -> p.getName())
                .map(String::toUpperCase )
                .collect(Collectors.joining(", "))
        );

        System.out.println("\n"+
                people.stream()
                .collect(Collectors.partitioningBy(p -> p.getAge()%2==0))
        );
        System.out.println("\nNév alapján csoportosítva és az adott névhez tartozók kora Map(String, List<Integer>)\n"+
                people.stream()
                .collect(Collectors.groupingBy(Person::getName, mapping(Person::getAge, Collectors.toList())))
        );

        System.out.println("\nElőforduló nevek számossága Map(String, Long)\n"+
                people.stream()
                        .collect(Collectors.groupingBy(Person::getName, counting()))
        );

        System.out.println("\nElőforduló nevek számossága Map(String, Integer) (!!! Long::intValue())\n"+
                people.stream()
                        .collect(Collectors.groupingBy(Person::getName, collectingAndThen(counting(), i -> i.intValue())))
        );

        System.out.println("\nVisszadja nem csak azt hogy mennyi idős a legidősebb, de magát a objektumot adja vissza\n"+
                people.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Person::getAge)))
        );

        System.out.println("\nVisszadja legidősebb, nevét. Optionalból alakít vissza stringet\n"+
                people.stream()
                        .collect(collectingAndThen(maxBy(Comparator.comparing(Person::getAge)), p -> p.map(Person::getName).orElse("")))
        );









    }
}

