package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class jmh_01_Annotations {

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 2,time = 1,timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 4,time = 1,timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Threads(2)
    public void benchmarkHelloWorld() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Hello World");
    }


    public static void main(String [] s) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_01_Annotations.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
