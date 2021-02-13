package com.asy.test.streams;

import com.asy.test.data.Generator;
import com.asy.test.data.IdValuePair;
import com.asy.test.data.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTests {

    private static int testVariable = IntStream.rangeClosed(1, 10).sum();

    /*public static void main(String[] args) {
        System.out.println(test(1,2));
    }

    public static int test(int x, int y) {
        int i = x < y ? x : null;
        return i;
    }*/

    public static void main(String[] args) {
        //testFindInList();
        //testFindInList2();
        //testFindInList3();
        //test_map_compare();
        //test_flatmap();
        //testTakeWhile();
        //testDropWhile();
        //testIntStream();
        //testReduce();
        testMapFilterReduce();
    }

    private static void testMapFilterReduce() {
        
    }

    private static void test_flatmap() {
        // regular map
        List<String> myList = Stream.of("a", "b")
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // if we have list in the list like :
        List<List<String>> list = Arrays.asList(
                Arrays.asList("a", "f", "e", "b"),
                Arrays.asList("b", "c", "a"));

        System.out.println(list);

        // gives error :
        //list.stream().map(String::toString).collect(Collectors.toList());

        // this is ok :
        List<String> collected = list.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(collected);

        // distinct and also sorted :
        List<String> collected_distinct = list.stream().flatMap(List::stream).distinct().sorted().collect(Collectors.toList());
        System.out.println(collected_distinct);

    }

    private static void test_map_compare() {
        List<Person> personList = Generator.generatePersonList();

        //personList.forEach(s -> System.out.print(s.getName() + " " + s.getSurname()));
        //personList.forEach(s -> {System.out.print(s.getName() + " " + s.getSurname()); });
        //System.out.println("");

        //String streamcollect = personList.stream().map(s -> s.getName() + " " + s.getSurname()).collect(Collectors.joining(","));
        //System.out.print(streamcollect);
        //System.out.println();


        StringJoiner joiner = new StringJoiner(",");
        personList.forEach(p -> joiner.add(p.getName() + " " + p.getSurname()));
        System.out.println(joiner.toString()); // inside : String.join(",", .. );

        Comparator<Person> personSurnameReverseComparator
                = Comparator.comparing(
                Person::getSurname, (s1, s2) -> {
                    return s2.compareTo(s1);
                });

        List<String> collectedList = personList.stream()
                .sorted(/*Comparator.reverseOrder()*/ personSurnameReverseComparator)
                .map(p -> p.getName()+"("+p.getSurname()+")")
                .collect(Collectors.toList());
        System.out.println("sorted by surname desc : "+collectedList);

        collectedList = personList.stream()
                .sorted(personSurnameReverseComparator.reversed())
                .map(p -> p.getName()+"("+p.getSurname()+")")
                .collect(Collectors.toList());
        System.out.println("sorted by surname asc : "+collectedList);


        //Comparator<String> stringLowercaseComparator = Comparator.comparing(String::toLowerCase);

        Locale tr = new Locale("tr");
        Comparator<String> stringLowercaseComparatorUsingLocale = Comparator.comparing(s -> s.toLowerCase(tr));
        List<String> collectedList2 = personList.stream()
                .map(p -> p.getName()+"("+p.getSurname()+")")
                .sorted(stringLowercaseComparatorUsingLocale)
                .collect(Collectors.toList());
        System.out.println("sorted by name : "+collectedList2);


        List<String> filteredNames = personList.stream()
                .filter(p -> p.getGender().equals(Person.Gender.FEMALE))
                .map(Person::getName)
                .map(String::toUpperCase)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        System.out.println("female names, uppercase and ordered : "+filteredNames);

    }

    private static void testFindInList3() {
        List<Person> personList = Generator.generatePersonList();

        Map<String, Boolean> nameAndVipStatusMapAccordingtoMagicNumber = personList.stream()
                .filter(p -> p.getMagicNumber() > 23 && p.getMagicNumber() < 250)
                .collect(Collectors.toMap(Person::getName, Person::isVip));

        System.out.println(nameAndVipStatusMapAccordingtoMagicNumber);

    }

    private static void testReduce() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        // reduce  : applies a binary operator to each element in the stream
        Integer reduced = integers.stream().reduce(0, (a, b) -> a + b); //identity element will be used as 'b' for the first a,b pair for 'a'.
        System.out.println(reduced);

        Optional optionalReducedResultWithoutIdentityParameter = integers.stream().reduce((a, b) -> a + b);
        System.out.println(optionalReducedResultWithoutIdentityParameter);

        // collect : a "collection" is created and each element is "added" to that collection.
        Integer collected = integers.stream().collect(Collectors.summingInt(Integer::intValue));
        System.out.println(collected);

        IntStream intStream = integers.stream().mapToInt(Integer::intValue);
        System.out.println(intStream.sum());


        List<Person> personList = Generator.generatePersonList();
        Optional<Person> personWithHighestMagicNumberAsOptional = personList.stream().reduce((p,q) -> {
           if(p.getMagicNumber() > q.getMagicNumber()) return p; else return q;
        });
        System.out.println(personWithHighestMagicNumberAsOptional);

    }

    private static void testIntStream() {
        System.out.println(testVariable);

        IntStream.iterate(3, x -> x < 10, x -> x + 3).forEach(r -> System.out.print(r + " "));

        IntStream.range(0, 20).boxed().collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();

        String intStr = IntStream.rangeClosed(0, 20).boxed().map(p -> p.toString()).collect(Collectors.joining(","));
        System.out.println(intStr);

        System.out.println("sum of 1-to-10 :"+IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).sum());
        IntSummaryStatistics stats = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).summaryStatistics();
        System.out.println(stats);



    }

    private static final List<String> TURKISH_CHARACTERS = Arrays.asList("ç", "Ç", "ğ", "Ğ", "ı", "İ", "ş", "Ş", "ü", "Ü");
    private static void testDropWhile() {
        Stream<String> alphabet = Stream.of("a", "b", "c", "ç", "e", "f", "g");
        alphabet.dropWhile(StreamTests::checkTurkishCharacter).forEach(System.out::print);
    }

    private static boolean checkTurkishCharacter(String character){
        return !TURKISH_CHARACTERS.contains(character);
    }

    private static void testTakeWhile() {
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.takeWhile(n -> n < 6).forEach(System.out::print);
    }

    private static void testFindInList2() {
        var idValuePairList = List.of(new IdValuePair("123", "Deneme"), new IdValuePair("234", "Anime"));
        idValuePairList.stream().filter(e -> e.getValue().contains("e")).forEach(e -> System.out.println(e.getId()));

        var refinedList = idValuePairList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.toList());
        //System.out.println(refinedList);
        refinedList.stream().forEach(System.out::println);

        var refinedListStr = idValuePairList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.joining(","));
        System.out.println(refinedListStr);

    }

    private static void testFindInList() {
        Set<IdValuePair> testSet = new HashSet<>();
        testSet.add(new IdValuePair("5", "asd"));
        testSet.add(new IdValuePair("7", "asd2"));
        testSet.add(new IdValuePair("3", "asd3"));
        testSet.add(new IdValuePair("9", "asd4"));
        testSet.add(new IdValuePair("13", "asd5"));
        testSet.add(new IdValuePair("8", "asd6"));
        testSet.add(new IdValuePair("3", "asd7"));
        testSet.add(new IdValuePair("6", "asd8"));
        System.out.println("Total item count : " + testSet.stream().count());
        boolean claimIdExists = testSet.stream().anyMatch(item -> "13".equals(item.getId()));
        System.out.println("anyMatch:"+claimIdExists);


        System.out.println("allMatch (each value starts with asd?):" + testSet.stream().allMatch(item -> item.getValue().startsWith("asd")));
        System.out.println("allMatch (each id length is 1?):"+testSet.stream().allMatch(item -> item.getId().length()==1));
        System.out.println("noneMatch (id=10):"+testSet.stream().noneMatch(item -> "10".equals(item.getId())));
        System.out.println("--eof1--");





        // peek : This method exists mainly to support debugging, where you want to see the elements as they flow past a certain point in a pipeline
        // Alternatively, we could have used map(), but peek() is more convenient since we don't want to replace the element.
        testSet.stream().peek(System.out::println).collect(Collectors.toList());
        System.out.println("--eof2--");

        //intermediate vs. Terminal Operations
        testSet.stream().peek(System.out::println); // All intermediate operations are lazy, and, as a result, no operations will have any effect until the pipeline starts to work.
        System.out.println("--eof3 (nothing printed)--");

        // Terminal operations mean the end of the stream lifecycle. Most importantly for our scenario, they initiate the work in the pipeline.
        testSet.stream().peek(System.out::println).collect(Collectors.toList());
        System.out.println("--eof4--");


        //Stream streamTest = testSet.stream();
        //streamTest.forEach(System.out::println);
        //streamTest.forEach(System.out::println); // a stream can be operated only once : stream has already been operated upon or closed
    }
}