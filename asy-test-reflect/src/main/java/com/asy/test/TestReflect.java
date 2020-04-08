package com.asy.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestReflect {

    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.asy.test.TargetClass");
        System.out.println(clazz);

        Method[] methods = clazz.getMethods();
        printMethods(methods);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        printMethods(declaredMethods);

        //Object instance = clazz.newInstance();

        System.out.println("10.2.3.4".matches("10.2.*.*"));
        System.out.println("1231s244".matches("[0-9]+"));
    }

    public static void printMethods(Method[] methods) {
        if (methods == null) {
            System.out.println("methods null");
            return;
        }

        //java8 joiner
        StringJoiner sj = new StringJoiner(",");
        Arrays.stream(methods).forEach(p -> sj.add(p.getName()));
        System.out.println(sj);

        //java 8 streams
        String chString = Stream.of(methods).map(p-> p.getName()).collect(Collectors.joining(","));
        System.out.println(chString);

        // oldest way
        String separator = "";  // separator here is your ","
        for (Method m : methods) {
            System.out.print(separator + m.getName());
            separator = ",";
        }



        System.out.println();
    }

}
