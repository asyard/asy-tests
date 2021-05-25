package com.asy.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class SupplierTests {
    public static void main(String[] args) {
        //Supplier.java : represents a function which does not take in any argument but produces a value of type T
        supplierTests();
    }

    private static void supplierTests() {
        Supplier<Double> doubleSupplier = () -> Math.random();
        System.out.println(doubleSupplier.get());

        //todo we must use 'return' when we use Statement lambda
        Supplier<Double> doubleSupplier2 = () -> {
            return Math.random();
        };
        System.out.println(doubleSupplier2.get());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Supplier<String> dateTimeSup = () -> dtf.format(LocalDateTime.now());
        System.out.println(dateTimeSup.get());

        List<String> strList = supplier().get();
        strList.add("str");
        System.out.println(strList);

        List<Integer> integerList = supplier().get();
        integerList.add(1);
        System.out.println(integerList);

        BooleanSupplier sp = () -> true;
        sp.getAsBoolean();


    }


    private static <T> Supplier<List> supplier() {
        return () -> new ArrayList<T>();
    }
}
