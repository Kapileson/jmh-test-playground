package jmh_examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.AsyncProfiler;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jmh_07_Profilers {

    @State(Scope.Benchmark)
    public static class StateObj {
        public String emailAddress = "example@example.com";
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(1)
    @Fork(1)
    @Warmup(iterations = 1,time = 1)
    @Measurement(iterations = 2,time = 1)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public boolean isValidEmail(jmh_07_Profilers.StateObj state) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(state.emailAddress);
        return matcher.matches();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(jmh_07_Profilers.class.getSimpleName())
                .addProfiler(StackProfiler.class)
//                .addProfiler(GCProfiler.class)
//               .addProfiler(AsyncProfiler.class)
                .build();
        new Runner(opt).run();
    }
}