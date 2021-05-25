package com.asy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class VarKeywordTests {
    public static void main(String[] args) {
        testVar();
    }

    private static void testVar() {
        var local = 5;
        System.out.println(local);
        //local = "324"; gives error
        var localStr = "teststr";
        System.out.println(localStr);

        //int var = 10; // valid. this means var is not a keyword. but using var is not recommended
        //System.out.println(var);
        // its ok : var var = 10;

        //int else = 5; gives error

        for (var i = 0; i < 5; i++) {

        }

        //var val; gives error if no value is assigned
        //var val = null; gives error
        var val = new Object();
        val = null;

        //var v1, v2 = "test"; Cannot infer type: 'var' on variable without initializer
        //var v1 = "test1", v2 = "test2"; 'var' is not allowed in a compound declaration
        //var arr = {3, 4, 5}; Array initializer is not allowed here
        int[] arr = {3, 4, 5};
        var arr2 = new int[]{3, 4, 5};

        var div1 = 23/2;
        System.out.println(div1); //prints 11

        var div2 = 23.0 / 2;
        System.out.println(div2); //prints 11.5
        System.out.println();

        var param = "hello";
        System.out.println(innerMethod(param));



        var ccls = new C();
        var bcls = new B();

        System.out.println(ccls instanceof B);

        var longvar = Long.valueOf(5);
        Integer.valueOf(4);

        var list = new ArrayList<>();
        list.add("test");
        list.get(0);//object, not String.

        var strList = List.of("str1", "str2", "test3", "str4", "test5");
        var strFiltered = strList.stream().filter((var p) -> p.contains("str")).collect(Collectors.toList());
        //var strFilteredErr = strList.stream().filter(var p -> p.contains("str")).collect(Collectors.toList());

        strFiltered.forEach(p-> System.out.println(p.getClass()));

        BiFunction<Integer, Double, Double> powBiFunctionOriginal = (a, b) -> Math.pow(a, b);
        BiFunction<Integer, Double, Double> powBiFunction = (Integer a, Double b) -> Math.pow(a, b);
        //BiFunction<Integer, Double, Double> powBiFunctionVarCompileErr = (var a, Double b) -> Math.pow(a, b); Cannot mix 'var' and explicitly typed parameters in lambda expression
        BiFunction<Integer, Double, Double> powBiFunction2Var = (var a, var b) -> Math.pow(a, b);
        //BiFunction<Integer, Double, Double> powBiFunction2CompileErr = (a, Double b) -> Math.pow(a, b);
        //BiFunction<Integer, Double, Double> powBiFunction2VarCompileErr = (var a, b) -> Math.pow(a, b); Package not found: b

        Consumer<String> consumer5 = (final var s) -> System.out.println(s);
        Consumer<String> consumer6 = (var s) -> System.out.println(s);
        //Consumer<String> consumer6Err = String s -> System.out.println(s);
        //Consumer<String> consumer6ErrVar = var s -> System.out.println(s);

    }

    private static String innerMethod(String s) {
        return s.toUpperCase();
    }
}

/*var Clazz {
    error
 }*/

class B {
    //public var b1 = 5; cannot be used in fields of a class
}

class C extends B {
    /*void doSth(var v) {
        error
    }*/

    /*var retSth() {
        return "5";
        error
    }*/
}