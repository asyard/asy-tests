package com.asy.test;

import com.asy.test.data.Generator;
import com.asy.test.data.Person;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class PredicateTests {
    public static void main(String[] args) {
        //Predicate.java : Takes 1 argument. Returns true/false
        predicateTests();
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

        DoublePredicate testDoublePredicate = d -> Math.pow(d, 2d) < 235544d;
        System.out.println("double predicate result is : "+testDoublePredicate.test(7d));

        // bipredicate : takes 2 args and returns boolean
        BiPredicate<Long, Long> sumIsLessThanHundredPredicate = (x, y) -> x + y < 100;
        System.out.println(sumIsLessThanHundredPredicate.test(33L, 66L)); // true



    }

}
