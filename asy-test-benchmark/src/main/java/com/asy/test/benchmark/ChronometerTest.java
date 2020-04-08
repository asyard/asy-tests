package com.asy.test.benchmark;

import java.util.concurrent.TimeUnit;

public class ChronometerTest {

    public static void main(String[] args) {
        long start = System.nanoTime();
        long result = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            result += i;
        }
        long end = System.nanoTime();


        System.out.println(TimeUnit.MILLISECONDS.convert(end-start, TimeUnit.NANOSECONDS));

    }
}
