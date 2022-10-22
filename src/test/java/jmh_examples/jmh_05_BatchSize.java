package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class jmh_05_BatchSize {
    int a = 0;

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Threads(1)
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2, batchSize = 10)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void printCurrentIteration() {
        System.out.println("Current invocation is " + a++);
    }

    public static void main(String [] s) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_05_BatchSize.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}