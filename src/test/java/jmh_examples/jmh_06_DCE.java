package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


public class jmh_06_DCE {


    @State(Scope.Thread)
    public static class MyState {
        public int a = 1;
        public int b = 2;
    }
    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void deadCodeElimination(MyState st) {
         sum();
        //bl.consume(sum());
    }

    private int sum(){
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
