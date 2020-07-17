package com.asy.test.lambda;


import com.asy.test.data.Person;

import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaTests {


    public static void main(String[] args) {
        scopeTests();
        //generalTests();

        //methodRefTests();

    }

    static int somestaticval = 13;
    private static void scopeTests() {
        int someval1 = 11;
        final int[] someval2 = {12};
        //Function<Integer, Integer> someFnc = (someval1) -> { //Variable 'someval1' is already defined in the scope
        Function<Integer, Integer> someFnc = (p) -> {
            //someval1 = 41; //Variable used in lambda expression should be final or effectively final
            //System.out.println(someval1);
            someval2[0] = 42;
            somestaticval = 43;
            return p * 2;
        };
        someval1 = 11;

        System.out.println(someFnc.apply(4));


    }

    private static void methodRefTests() {
        //static method reference
        Predicate<Person> predicate1 = p -> p.isVip();
        Predicate<Person> predicate1_2 = Person::isVip;

        Function<Double, Double> sinFunction = a -> Math.sin(a);
        System.out.println(sinFunction.apply(90d));

        Function<Double, Double> sinFunction2 = Math::sin; // wouldnt work if sth like Math.sin(a*2); There should not be any logic inside.
        System.out.println(sinFunction2.apply(90d));

        Function<Double, Double> sinOfTwofoldFunc = LambdaTests::sinOfTwofold;
        System.out.println(sinOfTwofoldFunc.apply(45d));

        //instance method reference
        LambdaTests lambdaTests = new LambdaTests();
        Function<Double, Double> sinOfTwofoldFunc2 = lambdaTests::nonStaticSinOfTwofold;
        System.out.println(sinOfTwofoldFunc2.apply(45d));

        //constr
        //String str = String::new; String is not a functional interface
        FunctnlInterface1 fi = String::new;
        DummyPersonFunctionalInterface pfi = Person::new; //we added 2 param constr. to Person class to achieve this
        Person dummyPerson = pfi.createNewDummy("Test", "Pers");
        System.out.println(dummyPerson);

    }

    private static Double sinOfTwofold(Double val) {
        return Math.sin(val * 2);
    }
    private Double nonStaticSinOfTwofold(Double val) {
        return Math.sin(val * 2);
    }

    private static void generalTests() {
        LambdaTests lambdaTests = new LambdaTests();

        lambdaTests.testLambda1(() -> System.out.println("testing1.1"));

        lambdaTests.testLambda1(new FunctnlInterface1() {
            @Override
            public void test() {
                System.out.println("testing1.2");
            }
        });


        lambdaTests.testLambda2("testing2.1", 2, (param1, param2) -> "param1 : " + param1 + ", param2 : " + param2);

        lambdaTests.testLambda2("testing2.2", 2, (param1, param2) -> {return "param1 : " + param1 + ", param2 : " + param2;});

        lambdaTests.testLambda2("testing2.3", 2, new FunctnlInterface2() {
            @Override
            public String test2(String param1, int param2) {
                return "param1 : " + param1 + ", param2 : " + param2;
            }
        });

        FunctnlInterface2 fi2 = (s, p) -> "param1 : " + s + ", param2 : " + p;
        System.out.println(fi2.test2("testing2.4", 2));

        FunctnlInterface2.staticMethod1();
    }

    public void testLambda1(FunctnlInterface1 iTest) {
        iTest.testDefault1();
    }

    public void testLambda2(String p1, int p2, FunctnlInterface2 iTest2) {
        String s = iTest2.test2(p1, p2);
        System.out.println(s);
    }

}
