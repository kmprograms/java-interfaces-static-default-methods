import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {

        // PREDICATE
        System.out.println("---------------------------------------------");
        System.out.println("PREDICATE");
        System.out.println("---------------------------------------------");

        Predicate<String> p1 = expression -> expression.length() > 5;
        Predicate<String> p2 = expression -> expression.toUpperCase().equals(expression);
        Predicate<String> p3 = expression -> expression.startsWith("A");
        Predicate<String> p4 = p1.negate().and(p2).or(p3);
        System.out.println("1.1 -> " + p4.test("ALA"));
        System.out.println("1.2 -> " + p4.test("ALEKSANDRA"));
        System.out.println("1.3 -> " + p4.test("Aleksandra"));
        System.out.println("1.4 -> " + p4.test("Boguslaw"));

        Predicate<String> p5 = Predicate.not(p1);
        System.out.println("1.5 -> " + p5.test("ALA"));

        Person expectedPerson = new Person("ADAM", 10);
        Person person1 = new Person("ADAM", 10);
        Person person2 = new Person("ADAM", 11);
        Predicate<Person> p6 = Predicate.isEqual(expectedPerson);
        System.out.println("1.5 -> " + p6.test(person1));
        System.out.println("1.6 -> " + p6.test(person2));

        // BINARY OPERATOR + BI_FUNCTION
        System.out.println("---------------------------------------------");
        System.out.println("BINARY OPERATOR + BI_FUNCTION");
        System.out.println("---------------------------------------------");

        BinaryOperator<Person> bo1 = BinaryOperator.maxBy(Comparator.comparing(Person::getAge));
        BinaryOperator<Person> bo2 = BinaryOperator.minBy(Comparator.comparing(Person::getAge));

        Person pp1 = new Person("ADAM", 10);
        Person pp2 = new Person("EWA", 20);
        System.out.println("2.1 -> " + bo1.apply(pp1, pp2));
        System.out.println("2.2 -> " + bo2.apply(pp1, pp2));

        BiFunction<Person, Person, String> bf1 = bo1.andThen(Person::getName);
        String maxAgePersonName = bf1.apply(pp1, pp2);
        System.out.println("2.3 -> " + maxAgePersonName);


        // UNARY OPERATOR + FUNCTION
        System.out.println("---------------------------------------------");
        System.out.println("UNARY OPERATOR + FUNCTION");
        System.out.println("---------------------------------------------");
        // przeksztalcenie tozsamosciowe
        UnaryOperator<Person> uo1 = UnaryOperator.identity();
        Person pp3 = new Person("ADAM", 10);
        Person pp4 = uo1.apply(pp3);
        System.out.println("3.1 -> " + pp4);

        UnaryOperator<Person> uo2 = p -> new Person(p.getName().toLowerCase(), p.getAge());
        Function<Person, String> f1 = uo2.andThen(Person::getName);
        System.out.println("3.2 -> " + f1.apply(pp3));

        UnaryOperator<String> uo3 = expression -> expression.toLowerCase();
        Function<Person, String> f2 = uo3.compose(Person::getName); // najpierw pobierze name z Person a potem
        // zwroci nowy napis ktory bedzie zawieral tylko i wylacznie male litery
        Person pp5 = new Person("ROMAN", 11);
        System.out.println("3.3 -> " + f2.apply(pp5));

        // CONSUMER
        System.out.println("---------------------------------------------");
        System.out.println("CONSUMER");
        System.out.println("---------------------------------------------");
        Consumer<Person> onlyName = p -> System.out.println(p.getName());
        Consumer<Person> onlyAge = p -> System.out.println(p.getAge());
        Consumer<Person> nameAndAge = onlyName.andThen(onlyAge);
        Person pp6 = new Person("JAN", 20);
        nameAndAge.accept(pp6);

        // COMPARATOR
        System.out.println("---------------------------------------------");
        System.out.println("COMPARATOR");
        System.out.println("---------------------------------------------");
        List<String> expressions = Arrays.asList(null, "ANDRZEJ", null, "OLA", "IGOR");

        System.out.println("4.1");
        expressions
                .stream()
                .sorted(Comparator.nullsFirst(Comparator.reverseOrder()))
                .forEach(System.out::println);

        System.out.println("4.2");
        // bez nullsLast lub nullsFirst wystapi NullPointerException
        expressions
                .stream()
                .sorted(Comparator.nullsLast(Comparator.comparingInt(String::length)))
                .forEach(System.out::println);


    }
}
