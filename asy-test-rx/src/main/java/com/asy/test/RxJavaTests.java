package com.asy.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxJavaTests {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> hello = Observable.just("Hello", "Hello2");
        hello.subscribe(System.out::println);

        List<String> words = Arrays.asList(
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        );

        Observable.fromIterable(words).subscribe(System.out::println);

        Observable.fromIterable(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count)->String.format("%2d. %s", count, string))
                .subscribe(System.out::println);

        Observable.range(1, 5).subscribe(System.out::println);

        Observable.fromIterable(words)
                .flatMap(word -> Observable.fromArray(word.split("")))
                //.sorted()
                //.distinct()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);

        // The debounce operator treats all events within a specified time delay as a single event, emitting only the last in each such series:

        int x = 5_2;
        System.out.println(x);

        Observable<Long> clock = Observable.interval(1, TimeUnit.SECONDS);
        clock.subscribe(tick-> System.out.println(tick + "> "+new Date().toString()));
        Thread.sleep(60_000);


    }

    private static long start = System.currentTimeMillis();
    public static Boolean isSlowTickTime() {
        return (System.currentTimeMillis() - start) % 30_000 >= 15_000;
    }
}
