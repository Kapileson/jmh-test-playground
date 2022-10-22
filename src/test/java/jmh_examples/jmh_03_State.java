package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
//@State(Scope.Group)
public class jmh_03_State {

   @Param({"1"})
    public int arg;

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Threads(2)
/*  @Group("Test")
    @GroupThreads(2)*/
    @Fork(1)
    @Warmup(iterations = 1,time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 1,time = 1, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void increment(jmh_03_State state) {
        state.arg++;
        System.out.println(arg);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_03_State.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}