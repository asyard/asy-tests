package com.asy.test.streams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTests {

    /*public static void main(String[] args) {
        System.out.println(test(1,2));
    }

    public static int test(int x, int y) {
        int i = x < y ? x : null;
        return i;
    }*/

    public static void main(String[] args) {
        //testFindInList();
        testFindInList2();
        //testTakeWhile();
        //testDropWhile();
        //testIntStream();
    }

    private static void testIntStream() {
        IntStream.iterate(3, x -> x < 10, x -> x + 3).forEach(r -> System.out.print(r + " "));
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
        var explicitPersonList = List.of(new Claim("123", "Deneme"), new Claim("234", "Anime"));
        explicitPersonList.stream().filter(e -> e.getValue().contains("e")).forEach(e -> System.out.println(e.getId()));

        var refinedList = explicitPersonList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.toList());
        //System.out.println(refinedList);
        refinedList.stream().forEach(System.out::println);

        var refinedListStr = explicitPersonList.stream().filter(e -> e.getValue().contains("e")).map(x -> x.getId()).collect(Collectors.joining(","));
        System.out.println(refinedListStr);

    }

    private static void testFindInList() {
        Set<Claim> claims = new HashSet<>();
        claims.add(new Claim("5", "asd"));
        claims.add(new Claim("7", "asd2"));
        claims.add(new Claim("3", "asd3"));
        claims.add(new Claim("9", "asd4"));
        claims.add(new Claim("13", "asd5"));
        claims.add(new Claim("8", "asd6"));
        claims.add(new Claim("3", "asd7"));
        claims.add(new Claim("6", "asd8"));
        boolean claimIdExists = claims.stream().anyMatch(item -> "13".equals(item.getId()));
        System.out.println(claimIdExists);
    }
}

class Claim {
    String id;
    String value;

    public Claim(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}

