package com.asy.test.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
//@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class BencMarkTest {

    private List<MyObject> testData;


    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder()
                .include(BencMarkTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
        //org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() {
        testData = createData();
    }

    @Benchmark
    //@Fork(value = 1, warmups = 1)
    //@BenchmarkMode(Mode.Throughput)
    public void testLoopContains() {
        testData.contains(new MyObject("test898"));
        //System.out.println("test1 completed");
    }

    @Benchmark
    //@Fork(value = 1, warmups = 1)
    //@BenchmarkMode(Mode.Throughput)
    public void testStreamAnyMatch() {
        testData.stream().anyMatch(a -> a.getValue().equals("test898"));
    }


    private List<MyObject> createData() {
        System.out.println("creating test data");
        List<MyObject> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            data.add(new MyObject("test" + i));
        }
        System.out.println("test data creation OK. Total data count : " + data.size());
        return data;
    }

}

class MyObject {
    String value = "";

    public MyObject(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
