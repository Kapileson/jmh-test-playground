package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class jmh_04_Fixtures {

    @Setup(Level.Trial)
    public void setup() {
        System.out.println("Do Setup...");
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        System.out.println("Do TearDown...");
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Threads(1)
    @Fork(1)
    @Warmup(iterations = 1,time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2,time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void print() throws InterruptedException {
        System.out.println("test");
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_04_Fixtures.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}