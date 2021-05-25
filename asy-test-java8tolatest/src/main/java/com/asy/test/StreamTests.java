package com.asy.test;

import com.asy.test.data.Generator;
import com.asy.test.data.IdValuePair;
import com.asy.test.data.Person;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.*;

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
        //testMapFilterReduce();
        //testOps();
        //testCollectionOps();
        testParallelStream();
    }

    private static void testParallelStream() {
        System.out.println("Available processors : " + Runtime.getRuntime().availableProcessors());
        int parallelSum = IntStream.rangeClosed(1, 5000).parallel().sum(); // thread count = available processor count : Runtime.getRuntime().availableProcessors()
        System.out.println(parallelSum);

        List<String> strList = List.of("A", "b", "C", "D", "e");

        // parallel uses : fork/join pool, which is introduced in java7
        ForkJoinPool myPool = new ForkJoinPool(20); // increase thread count
        try {
            myPool.submit(() ->
                    strList.parallelStream().forEach(s -> System.out.println("isLower:" + s.equals(s.toLowerCase())))).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // sequential vs parallel performance
        Instant start = Instant.now();
        int sequentialSum2 = IntStream.rangeClosed(1, 50000).sum();
        Instant end = Instant.now();
        System.out.println(sequentialSum2);
        System.out.println("sequential (ns) : " + Duration.between(start, end).getNano());


        start = Instant.now();
        int parallelSum2 = IntStream.rangeClosed(1, 50000).parallel().sum();
        end = Instant.now();
        System.out.println(parallelSum2);
        System.out.println("parallel (ns) : " + Duration.between(start, end).getNano());


        int[] intsArray = ThreadLocalRandom.current().ints().limit(1000).toArray();

        start = Instant.now();
        Arrays.stream(intsArray).sorted();
        end = Instant.now();
        System.out.println("sequential sort (ns) : " + Duration.between(start, end).getNano());

        start = Instant.now();
        Arrays.stream(intsArray).parallel().sorted();
        end = Instant.now();
        System.out.println("parallel sort (ns) : " + Duration.between(start, end).getNano());

    }

    private static void testCollectionOps() {
        //joining
        List<String> strList = Arrays.asList("A", "B", "C", "D", "E");
        String collected1 = strList.stream().collect(Collectors.joining(","));
        System.out.println(collected1);

        String collected2 = Stream.of("A", "B", "C", "D", "E").collect(Collectors.joining(","));
        System.out.println(collected2);

        String collected3 = String.join(",", strList);
        System.out.println(collected3);

        StringJoiner joiner = new StringJoiner(",");
        strList.forEach(p -> joiner.add(p));
        System.out.println(joiner.toString()); // inside : String.join(",", .. );

        String collected5 = Stream.of("A", "B", "C", "D", "E").collect(Collectors.joining(",", "[", "]"));
        System.out.println(collected5);

        //counting : counts the number of elements in a stream
        Long count1 = Stream.of("AA", "B", "C", "D", "E").collect(Collectors.counting());
        System.out.println(count1);

        Long count2 = Stream.of("AA", "B", "C", "D", "E").count();
        System.out.println(count2);

        Map<Integer, Long> countmap3 = Stream.of("AA", "B", "C", "D", "E").collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(countmap3);

        //mapping
        List<Person> personList = Generator.generatePersonList();

        List<String> collectedList4 = personList.stream()
                .map(p -> p.getName() + "(" + p.getSurname() + ")")
                .collect(Collectors.toList());
        System.out.println(collectedList4);

        List<String> collectedList5 = personList.stream()
                .collect(Collectors.mapping(p -> p.getName() + "(" + p.getSurname() + ")", Collectors.toList()));
        System.out.println(collectedList5);

        Map<Boolean, List<String>> collectedList6 = personList.stream()
                .collect(Collectors.groupingBy(Person::isVip, Collectors.mapping(p -> p.getName() + "(" + p.getSurname() + ")", Collectors.toList())));
        System.out.println(collectedList6);

        //minBy, min
        Optional<String> minval = Stream.of("C", "B", "A", "G", "D").collect(Collectors.minBy((a, b) -> a.compareTo(b)));
        System.out.println(minval.get());

        Optional<String> minval2 = Stream.of("C", "B", "A", "G", "D").min(Comparator.naturalOrder());
        System.out.println(minval2.get());

        //maxBy, max
        Optional<String> maxval = Stream.of("C", "B", "A", "G", "D").collect(Collectors.maxBy((a, b) -> a.compareTo(b)));
        System.out.println(maxval.get());

        Optional<String> maxval2 = Stream.of("C", "B", "A", "G", "D").max(Comparator.naturalOrder());
        System.out.println(maxval2.get());

        Optional<Person> maxBy3 = personList.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getMagicNumber)));
        System.out.println(maxBy3.get());

        Optional<Person> maxBy4 = personList.stream().max(Comparator.comparing(Person::getMagicNumber));
        System.out.println(maxBy4.get());

        //summing
        Integer collectedSum = personList.stream().collect(Collectors.summingInt(m -> m.getMagicNumber().intValue()));
        System.out.println(collectedSum);

        //averaging
        Double collectedAvg = personList.stream().collect(Collectors.averagingInt(m -> m.getMagicNumber().intValue()));
        System.out.println(collectedAvg);

        //grouping

            //grouping-classifier
            Map<Integer, List<String>> strLengthsGrouped = Stream.of("AA", "BBB", "CCC", "DDDD", "EE", "FFF", "G").collect(Collectors.groupingBy(String::length));
            System.out.println(strLengthsGrouped);

            Map<Long, List<Person>> personMagicNumberGrouped = personList.stream().collect(Collectors.groupingBy(Person::getMagicNumber));
            personMagicNumberGrouped.entrySet().forEach(System.out::println);
            System.out.println(personMagicNumberGrouped);
            //personMagicNumberGrouped.forEach((k,v) -> System.out.println("["+k+"] = " + v));

            //grouping-classifier, downstream
            List<String> namesList = personList.stream().map(Person::getName).collect(Collectors.toList());
            System.out.println(namesList);
            Map<Integer, List<String>> namesLengthGrouped = namesList.stream().collect(Collectors.groupingBy(String::length));
            System.out.println(namesLengthGrouped);

            Map<Integer, List<String>> namesLengthGroupedWithStrFiltering = namesList.stream().
                    collect(Collectors.groupingBy(String::length, Collectors.filtering(s -> s.contains("e"), Collectors.toList())));
            System.out.println(namesLengthGroupedWithStrFiltering);

            System.out.println("---");
            Map<Long, Optional<Person>> personMaxMagicNumberGrouped = personList.stream().
                    collect(Collectors.groupingBy(Person::getMagicNumber, Collectors.maxBy(Comparator.comparing(Person::getMagicNumber))));
                //personMaxMagicNumberGrouped.entrySet().stream().forEach(System.out::println);
                personMaxMagicNumberGrouped.forEach((k, v) -> System.out.println(k + "-" + v));

            System.out.println("---");
            Map<Long, Person> personMaxMagicNumberGrouped2 = personList.stream().
                    collect(Collectors.groupingBy(Person::getMagicNumber,
                            Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Person::getMagicNumber)), Optional::get)));
            personMaxMagicNumberGrouped2.forEach((k, v) -> System.out.println(k + "-" + v));

            System.out.println("---");
            Map<Boolean, Double> personGroupedByVipAndMagicNumberAvg3 = personList.stream().collect(Collectors.groupingBy(Person::isVip, Collectors.averagingLong(Person::getMagicNumber)));
            System.out.println(personGroupedByVipAndMagicNumberAvg3);

            System.out.println("---");
            Map<Boolean, LongSummaryStatistics> personGroupedByVipAndMagicNumberSumStats4 = personList.stream().collect(Collectors.groupingBy(Person::isVip, Collectors.summarizingLong(Person::getMagicNumber)));
            System.out.println(personGroupedByVipAndMagicNumberSumStats4);

            //grouping-classifier, mapfactory, downstream
            LinkedHashMap<Integer, List<String>> namesLengthGroupedWithMapFactoryAndStrFiltering = namesList.stream().
                    collect(Collectors.groupingBy(String::length, LinkedHashMap::new, Collectors.filtering(s -> s.contains("e"), Collectors.toList())));
            System.out.println(namesLengthGroupedWithMapFactoryAndStrFiltering);


            //partitioning
            Map<Boolean, List<Person>> personPartitionedByHavingELetterInName = personList.stream().collect(Collectors.partitioningBy(p -> p.getName().contains("e")));
            personPartitionedByHavingELetterInName.forEach((k, v) -> System.out.println(k + ":" + v));

            // to set: for removing duplicates
            Map<Boolean, Set<Person>> personSetPartitionedByHavingELetterInName = personList.stream().
                    collect(Collectors.partitioningBy(p -> p.getName().contains("e"), Collectors.toSet()));
            personSetPartitionedByHavingELetterInName.forEach((k, v) -> System.out.println(k + ":" + v));


    }

    private static void testOps() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> max1 = integers.stream().max(Integer::compareTo);
        System.out.println(max1.get());

        Optional<Integer> integerReduced = integers.stream().reduce((a, b) -> a >= b ? a : b);
        System.out.println(integerReduced.get());

        Optional<Integer> reduce2 = integers.stream().reduce(Integer::max);
        System.out.println(reduce2.get());


        OptionalInt max2 = IntStream.of(1, 2, 3, 4, 5).max();
        System.out.println(max2.getAsInt());



        Integer sum1 = integers.stream().reduce((a, b) -> a + b).get();
        System.out.println(sum1);

        Integer sum2 = integers.stream().reduce(Integer::sum).get();
        System.out.println(sum2);

        int sum3 = IntStream.of(1, 2, 3, 4, 5).sum();
        System.out.println(sum3);

        int sum4 = integers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum4);

        Integer sum5 = integers.stream().collect(Collectors.summingInt(Integer::intValue));
        System.out.println(sum5);


        List<Integer> testintlist = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> limitliststream = testintlist.stream().limit(7);
        limitliststream.forEach(System.out::print);
        System.out.println();

        Stream<Integer> skippedliststream = testintlist.stream().skip(3);
        skippedliststream.forEach(System.out::print);
        System.out.println();

        Stream<Integer> limitedStream = Stream.iterate(0, x -> x + 2).limit(10); // 10 elems, even stream
        limitedStream.forEach(System.out::print);
        System.out.println();

        // infinite
        // Stream<Integer> unlimitedStream = Stream.iterate(0, x -> x + 2);
        // unlimitedStream.forEach(System.out::println);


        ThreadLocalRandom random = ThreadLocalRandom.current();
        Stream<Integer> randomGeneratedStream = Stream.generate(() -> random.nextInt(5)).limit(10);
        randomGeneratedStream.forEach(System.out::print);
        System.out.println();

        //reverse array
        int[] array = {1, 2, 3, 4, 5};
        int[] array_reversed = IntStream.range(0, array.length)
                .map(i -> array[array.length - 1 - i])
                .toArray();

        Arrays.stream(array_reversed).forEach(System.out::print);

    }

    private static void testMapFilterReduce() {
        List<Person> personList = Generator.generatePersonList();
        long result = personList.stream()
                .filter(Person::isVip)
                .map(Person::getMagicNumber)
                .reduce(0L, (p,q) -> (p+q));

        long result2 = personList.stream()
                .filter(Person::isVip)
                .map(Person::getMagicNumber)
                .reduce(0L, Long::sum);

        Optional resultOpt = personList.stream()
                .filter(Person::isVip)
                .map(Person::getMagicNumber)
                .reduce(Long::sum);


        LongStream result3 = personList.stream()
                .filter(Person::isVip)
                .map(Person::getMagicNumber)
                .mapToLong(Long::longValue);

        System.out.println(result);
        System.out.println(result2);
        System.out.println(result3.sum());
        System.out.println(resultOpt.get());

        List<String> list = Arrays.asList("25", "225", "1000", "20", "15");
        long result4 = list.stream().mapToLong(num -> Long.parseLong(num)).sum();
        System.out.println(result4);


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


        Optional<Person> anyPerson = personList.stream().filter(p -> p.getMagicNumber() > 23).findAny();
        System.out.println(anyPerson.orElse(personList.get(0)));

        Optional<Person> firstPerson = personList.stream().filter(p -> p.getMagicNumber() > 23).findFirst();
        System.out.println(firstPerson.orElse(personList.get(0)));

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
        System.out.println();

        IntStream.range(1, 5).forEach(System.out::print);
        System.out.println();

        IntStream.rangeClosed(1, 5).forEach(System.out::print); // 5 is included
        System.out.println();


        // boxed
        IntStream.rangeClosed(1, 5).forEach((int x)-> System.out.print(x)); // primitive int
        System.out.println();
        IntStream.rangeClosed(1, 5).mapToObj(Integer::valueOf).forEach((Integer x) -> System.out.print(x)); // mapping to Integer
        System.out.println();
        IntStream.rangeClosed(1, 5).boxed().forEach((Integer x) -> System.out.print(x)); // boxing to Integer
        System.out.println();
        //IntStream.rangeClosed(1, 5).boxed().sum(); // compile error
        IntStream.rangeClosed(1, 5).boxed().mapToInt(Integer::valueOf).sum(); // unboxing to primitive int


        IntStream.range(0, 20).boxed().collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();

        IntStream.range(0, 20).boxed().collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();

        String intStr = IntStream.rangeClosed(0, 20).boxed().map(p -> p.toString()).collect(Collectors.joining(","));
        System.out.println(intStr);

        System.out.println("sum of 1-to-10 :" + IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).sum());
        IntSummaryStatistics stats = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).summaryStatistics();
        System.out.println(stats);

        System.out.println(IntStream.of(1,2,3,4,5).min().getAsInt());
        System.out.println(IntStream.of(1,2,3,4,5).max().getAsInt());
        System.out.println(IntStream.of(1,2,3,4,5).sum());
        System.out.println(IntStream.of(1,2,3,4,5).average().getAsDouble());




        // bonus
        DoubleStream doubleStream = LongStream.of(2, 3, 4, 5).asDoubleStream();
        doubleStream.forEach(System.out::print);







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

        // intermediate vs. Terminal Operations
        // filter, peek, map, flatmap etc : intermediate
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