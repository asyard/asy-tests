package com.asy.test.streams;

import com.asy.test.data.IdValuePair;

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
        //testTakeWhile();
        //testDropWhile();
        testIntStream();
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
        var explicitPersonList = List.of(new IdValuePair("123", "Deneme"), new IdValuePair("234", "Anime"));
        explicitPersonList.stream().filter(e -> e.getValue().contains("e")).forEach(e -> System.out.println(e.getId()));

        var refinedList = explicitPersonList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.toList());
        //System.out.println(refinedList);
        refinedList.stream().forEach(System.out::println);

        var refinedListStr = explicitPersonList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.joining(","));
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
        boolean claimIdExists = testSet.stream().anyMatch(item -> "13".equals(item.getId()));
        System.out.println(claimIdExists);
    }
}