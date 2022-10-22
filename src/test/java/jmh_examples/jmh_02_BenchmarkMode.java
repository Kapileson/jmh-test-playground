package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class jmh_02_BenchmarkMode {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(1)
    @Fork(1)
    @Warmup(iterations = 2,time = 1)
    @Measurement(iterations = 4,time = 1)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void microbenchmarkModes(){
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("JMH");
        stringBuilder.append("Microbenchmark");
        stringBuilder.append("tests");
    }

    public static void main(String [] s) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_02_BenchmarkMode.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}