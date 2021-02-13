package com.asy.test.lambda;

import com.asy.test.data.Generator;
import com.asy.test.data.IdValuePair;
import com.asy.test.data.Person;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class FunctionalProgrammingTests {

    public static void main(String[] args) {
        //Consumer.java : Takes 1 argument. Does not return any value
        //consumerTests();

        //Predicate.java : Takes 1 argument. Returns true/false
        //predicateTests();

        // Function.java : Takes 1 argument. Returns a value.
        //functionTests();

        // notes:
        // unary, bi int-long-double consumer/predicate/function types -> see which one extends from other todo


    }

    private static void functionTests() {
        Function<Double, String> doublePrinterFunction = f -> "Value is : " + f;
        System.out.println(doublePrinterFunction.apply(7d));

        Function<Double, Double> powTwoFunction = f -> Math.pow(f, 2.0d);
        System.out.println(powTwoFunction.apply(2.5d));
        System.out.println(powTwoFunction.andThen(doublePrinterFunction).apply(2.5d)); // powFunction and then print
        System.out.println(doublePrinterFunction.compose(powTwoFunction).apply(2.5d)); // print after powFunction (same as above)

        Function<List<Person>, List<String>> namesOfGivenPeopleListWhoseNameBiggerThan5CharsFunction = f -> {
            List<String> names = new ArrayList<>();


            f.forEach(p -> {if(p.getName().length() > 5) names.add(p.getName());});
            /*Predicate<String> lengthGreaterThanFivePredicate = p -> p.length() > 5;
            f.forEach(p -> {if(lengthGreaterThanFivePredicate.test(p.getName())) names.add(p.getName());});*/

            /*for (Person p : f) {
                if (p.getName().length() > 5) {
                    names.add(p.getName());
                }
            }*/
            return names;
        };

        List<Person> personList = Generator.generatePersonList();

        System.out.println(namesOfGivenPeopleListWhoseNameBiggerThan5CharsFunction.apply(personList));

        //
        IntFunction multiplyByFiveFunction = i -> i * 5;
        System.out.println(multiplyByFiveFunction.apply(5));


        // Bifunction : takes 2 args and returns result
        BiFunction<Integer, Double, Double> powBiFunction = (a, b) -> Math.pow(a, b);
        System.out.println(powBiFunction.apply(3, 2.3d));

        BiFunction<List<Person>, Integer, List<String>> namesOfGivenPeopleListWhoseNameBiggerThanGivenCharsFunction = (l, x) -> {
            List<String> list = new ArrayList<>();
            l.forEach(p -> {
                if (p.getName().length() > x) {
                    list.add(p.getName());
                }
            });
            return list;
        };
        System.out.println(namesOfGivenPeopleListWhoseNameBiggerThanGivenCharsFunction.apply(personList, 4));
        System.out.println(Function.identity()); // meaningful with using stream api map(x->x)


        // unaryoperator : takes 1 arg and returns the same type of given arg
        UnaryOperator<Long> multiplyByTwoUnaryOperator = u -> u * 2;
        System.out.println(multiplyByTwoUnaryOperator.apply(7L));

        //UnaryOperator testUO = u -> "asd";
        //String s = (String) testUO.apply(2.3f);

        IntUnaryOperator multiplyByTwoIntUnaryOperator = i -> i * 2;
        System.out.println(multiplyByTwoIntUnaryOperator.applyAsInt(8));

        // binaryoperator : takes 2 args and returns the same type of given args
        BinaryOperator<Double> powBinaryOperator = (a, b) -> Math.pow(a, b);
        System.out.println(powBinaryOperator.apply(2.0, 2.0));

        Comparator<Integer> maxComparator = Comparator.naturalOrder();//(a, b) -> a.compareTo(b);
        System.out.println("-> "+BinaryOperator.maxBy(maxComparator).apply(3,5)); // 5
        System.out.println("-> "+BinaryOperator.minBy(maxComparator).apply(3,5)); // 3

        Integer a = 5, b = 2;
        System.out.println(a.compareTo(b));


        IntBinaryOperator multiplyIntBinaryOperator = (x, y) -> x * y;
        System.out.println(multiplyIntBinaryOperator.applyAsInt(23, 2));




    }

    private static void predicateTests() {
        Predicate longNumberPredicate = p -> p instanceof Long;
        System.out.println(longNumberPredicate.test("12")); // false
        System.out.println(longNumberPredicate.test(12));    // false
        System.out.println(longNumberPredicate.test(12L));    // true
        System.out.println(longNumberPredicate.negate().test(12L));    // false

        Predicate<Long> greaterThanHundredPredicate = p -> p > 100;

        System.out.println(longNumberPredicate.and(greaterThanHundredPredicate).test("12")); //false
        System.out.println(longNumberPredicate.and(greaterThanHundredPredicate).test(12L)); //false
        System.out.println(longNumberPredicate.and(greaterThanHundredPredicate).test(123L)); //true
        //System.out.println(greaterThanHundredPredicate.test("212")); //compile error
        //System.out.println(greaterThanHundredPredicate.and(longNumberPredicate).test("123")); //ClassCastException

        Predicate<Long> isEvenPredicate = p -> p %2 == 0;
        //System.out.println(greaterThanHundredPredicate.or(isEvenPredicate).test("13")); // compile error
        System.out.println(greaterThanHundredPredicate.or(isEvenPredicate).test(85L)); // false
        System.out.println(greaterThanHundredPredicate.or(isEvenPredicate).test(120L)); // true


        List<Person> personList = Generator.generatePersonList();
        Predicate<Person> femalePredicate = p -> p.getGender() == Person.Gender.FEMALE;
        Predicate<Person> vipPredicate = p -> p.isVip();

        personList.forEach(p -> {
            if (vipPredicate.test(p)) {
                System.out.println("vip : " + p.getName());
            }
        });

        // not vip and male
        personList.forEach(p -> {
            if (femalePredicate.negate().and(vipPredicate.negate()).test(p)) {
                System.out.println(p.getName() + " " + p.isVip());
            }
        });

        //
        IntPredicate lessThan255Predicate = i -> i < 255;
        System.out.println(lessThan255Predicate.test(323)); // false
        System.out.println(lessThan255Predicate.test(32));  // true

        // bipredicate : takes 2 args and returns boolean
        BiPredicate<Long, Long> sumIsLessThanHundredPredicate = (x, y) -> x + y < 100;
        System.out.println(sumIsLessThanHundredPredicate.test(33L, 66L)); // true



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

        // biconsumer takes 2 arguments
        BiConsumer biConsumer = (b, c) -> System.out.println(b + " " + c);
        biConsumer.accept("test", "biconsumer");

        BiConsumer<String, Person.Gender> personNameAndGenderBiconsumer = (n, g) -> System.out.println("name : " + n + ", gender : " + g);
        personNameAndGenderBiconsumer.accept(personList.get(0).getName(), personList.get(0).getGender());

        personList.subList(0, 3).forEach(p-> personNameAndGenderBiconsumer.accept(p.getName(), p.getGender()));



        //supplier is the opposite of consumer. Does not take any arg and returns sth

        Supplier<Integer> randomNumberSupplier = () -> {return new Random().nextInt(100);};
        System.out.println(randomNumberSupplier.get());





    }
}
