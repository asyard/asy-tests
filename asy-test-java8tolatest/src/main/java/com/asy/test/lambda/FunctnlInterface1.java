package com.asy.test.lambda;

// An interface with only one abstract method is called Functional Interface.
// It is not mandatory to use @FunctionalInterface, but it’s best practice to use it
// with functional interfaces to avoid addition of extra methods accidentally
@FunctionalInterface
public interface FunctnlInterface1 {
    void test();
    //void test2(); //: compilation error if more than 1 abstract method exists

    default void testDefault1() {
        System.out.println("default method 1");
        test();
    }

    default void testDefault2() {
        System.out.println("default method 2");
    }

}
