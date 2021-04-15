package com.asy.test.lambda;

@FunctionalInterface
public interface FunctnlInterface2 {

    String test2(String param1, int param2);

    // with java8, we can define static methods in interfaces
    // The idea behind static interface methods is to provide a simple mechanism that
    // allows us to increase the degree of cohesion of a design by putting together
    // related methods in one single place without having to create an object.
    // Pretty much the same can be done with abstract classes. The main difference
    // lies in the fact that abstract classes can have constructors, state, and behavior.
    /*public*/ static void staticMethod1() {
        System.out.println("inferface2 static method");
    }

    // more than 1 static method can exist
    static void staticMethod2() {
        System.out.println("inferface2 static method 2");
    }

    private static void staticMethod3() {
        System.out.println("inferface2 static method 3 - private");
    }
}
