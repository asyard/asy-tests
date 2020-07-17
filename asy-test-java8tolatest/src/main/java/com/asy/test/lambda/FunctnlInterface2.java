package com.asy.test.lambda;

@FunctionalInterface
public interface FunctnlInterface2 {
    String test2(String param1, int param2);


    // after java8, we can define static and default methods in interfaces
    // more than 1 static/default methods can exist
    static void staticMethod1() {
        System.out.println("inferface2 static method");
    }
}
