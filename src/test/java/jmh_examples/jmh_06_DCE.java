package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class jmh_06_DCE {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void deadCodeElimination(Blackhole bh) {
          addTwoNumbers();
//         bh.consume(addTwoNumbers());
    }

    private int addTwoNumbers(){
        int a = 1;
        int b = 2;
        return a + b;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_06_DCE.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}