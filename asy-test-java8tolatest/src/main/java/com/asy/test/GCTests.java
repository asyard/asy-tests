package com.asy.test;

import com.asy.test.data.Person;

public class GCTests {

    //-XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
    //-verbose:gc activates the logging of garbage collection information in its simplest form
    public static void main(String[] args) {
        testEpsilonGC();
    }

    private static void testEpsilonGC() {
        for (int p = 0; p < 10000; p++) {
            Person person = new Person("name", "surname");
        }


    }
}

