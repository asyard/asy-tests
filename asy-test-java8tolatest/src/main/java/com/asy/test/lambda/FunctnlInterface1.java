package com.asy.test.lambda;

// An interface with only one abstract method is called Functional Interface.
// It is not mandatory to use @FunctionalInterface, but it’s best practice to use it
// with functional interfaces to avoid addition of extra methods accidentally
@FunctionalInterface
public interface FunctnlInterface1 {
    /*public*/ void test();
    //void test2(); //: compilation error if more than 1 abstract method exists

    // with java8, we can define default methods in interfaces
    // Default methods allow us to add new methods to an interface that are automatically
    // available in the implementations. Thus, there's no need to modify the implementing classes.
    /*public*/ default void testDefault1() {
        System.out.println("default method 1");
        test();
    }

    // more than 1 default method can exist
    default void testDefault2() {
        System.out.println("default method 2");
    }

    /*private default void testDefault3() {
        // compile error. Illegal combination of modifiers: 'default' and 'private'
    }*/

}
