package com.asy.test;

import com.asy.test.data.Generator;
import com.asy.test.data.IdValuePair;
import com.asy.test.data.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.*;

public class ConsumerTests {
    public static void main(String[] args) {
        //Consumer.java : Takes 1 argument. Does not return any value
        consumerTests();
    }

    private static void consumerTests() {
        // consumer has accept method. Accepts any void operation
        Consumer<String> nameAndLengthPrinterConsumer = (c) -> System.out.println(String.format("Length of '%s' is %d", c, c.length()));
        nameAndLengthPrinterConsumer.accept("test consumer");

        Consumer<String> consumer2 = c -> {
            System.out.println("consumer2 is starting");
            System.out.println("Value is : " + c);
            System.out.println("consumer2 ended");
        };

        consumer2.accept("Val2");

        String[] strList = {"str1", "str 2", "str  3"};
        Arrays.asList(strList).forEach(nameAndLengthPrinterConsumer);
        Arrays.asList(strList).forEach(System.out::println);
        Arrays.asList(strList).forEach(s ->System.out.println(s));

        // consumer has andThen default method
        List<IdValuePair> sampleData = Generator.generateIdValueList(5);
        Consumer<IdValuePair> idConsumer = c -> System.out.println(c.getId());
        Consumer<IdValuePair> valueConsumer = c -> System.out.println(c.getValue());
        sampleData.forEach(idConsumer.andThen(valueConsumer));

        Consumer<Person> personConsumer = c -> System.out.println(c);
        List<Person> personList = Generator.generatePersonList();
        //personList.forEach(System.out::println);
        // print only vip
        personList.forEach(p -> {
            if (p.isVip()) {
                personConsumer.accept(p);
            }
        });

        Consumer<Person> nameConsumer = c -> System.out.print(c.getName() + " "+(c.getMiddleName().isPresent()? c.getMiddleName().get() + " " : "") +c.getSurname() +", ");
        Consumer<Person> genderConsumer = c -> System.out.print(c.getGender() + ",");
        personList.forEach(nameConsumer);

        System.out.println();
        personList.forEach(p -> {
            if (!p.isVip() && p.getGender() == Person.Gender.FEMALE) {
                nameConsumer.andThen(genderConsumer).accept(p);
            }
        });

        System.out.println();

        //Consumer consumer3err1 = (String s) -> System.out.println(s); Incompatible parameter types in lambda expression: expected Object but found String
        //Consumer<String> consumer3err2 = String s -> System.out.println(s); //String s must be in parenthesis
        Consumer<String> consumer3 = (String s) -> System.out.println(s);
        Consumer<String> consumer4 = (final String s) -> System.out.println(s);

        System.out.println();
        //
        IntConsumer intConsumer = ic -> System.out.println(ic + 1);
        int val = 3;
        intConsumer.accept(val);
        intConsumer.andThen(intConsumer).accept(val);

        // does not change
        IntConsumer intConsumer2 = ic -> ic = 1;
        Integer val2 = 5;
        System.out.println("val2 before : " + val2);
        intConsumer2.accept(val2);
        System.out.println("val2 after : " + val2);

        // does change
        PersonConsumer pc = p -> System.out.println(p.toString());
        pc.accept(personList.get(0));

        PersonConsumer pc2 = p -> {
            p.setMagicNumber(1L);
            p.setUniqueId("unq");
            p.setVip(true);
        };

        pc2.accept(personList.get(0));
        System.out.println(personList.get(0));

        // Double consumer
        DoubleConsumer doubleConsumerSqr = d -> System.out.println(d * 2);
        doubleConsumerSqr.accept(5d);

        //todo DoubleConsumer doubleConsumerErr = d -> d * 2; //Bad return type in lambda expression: double cannot be converted to void

        //Long consumer
        LongConsumer longConsumer = l -> System.out.println(l - 2);
        longConsumer.accept(5l);

        // biconsumer takes 2 arguments
        BiConsumer biConsumer = (b, c) -> System.out.println(b + " " + c);
        biConsumer.accept("test", "biconsumer");

        BiConsumer<String, Person.Gender> personNameAndGenderBiconsumer = (n, g) -> System.out.println("name : " + n + ", gender : " + g);
        personNameAndGenderBiconsumer.accept(personList.get(0).getName(), personList.get(0).getGender());

        personList.subList(0, 3).forEach(p-> personNameAndGenderBiconsumer.accept(p.getName(), p.getGender()));



        //supplier is the opposite of consumer. Does not take any arg and returns sth.
        //@see supplierTests()

        Supplier<Integer> randomNumberSupplier = () -> {return new Random().nextInt(100);};
        System.out.println(randomNumberSupplier.get());

    }
}

@FunctionalInterface
interface PersonConsumer {
    void accept(Person person);

    default PersonConsumer andThen(PersonConsumer after) {
        Objects.requireNonNull(after);
        return (t) -> { accept(t); after.accept(t); };
    }
}