package com.asy.test;

import java.util.Optional;

public class OptionalTests {
    public static void main(String[] args) {
        Integer[] list = null;
        Optional<Integer[]> optionalList1 = Optional.ofNullable(list);
        System.out.println(optionalList1.isPresent());

        list = new Integer[10];
        Optional<Integer> optionalVal1 = Optional.ofNullable(list[4]);
        System.out.println(optionalVal1.isPresent());

        //Optional.of(null); -> NullPointerException

        Optional sample1 = optionalVal1.isPresent() ? optionalVal1 : Optional.empty();
        System.out.println(sample1);

        Integer sample2 = optionalVal1.orElse(5);
        System.out.println(sample2);

        Integer sample3 = optionalVal1.orElseGet(()->5);
        System.out.println(sample3);

        try {
            Integer sample4 = optionalVal1.orElseThrow();
        } catch (Exception e) {
            System.err.println(e + ">" + e.getMessage());
        }

        optionalVal1.ifPresent((i) -> System.out.println("exist"));

        optionalVal1.ifPresentOrElse((i) -> System.out.println("exist"), ()-> System.out.println("nope"));

    }
}
