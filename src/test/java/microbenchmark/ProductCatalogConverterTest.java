package microbenchmark;

import com.codoid.products.exception.FilloException;
import jmh.demo.ProductCatalogConverter;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.AsyncProfiler;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ProductCatalogConverterTest {

    @Param("src/test/resources/product_catalog.xlsx")
    String filePath;

    @Benchmark
    public void benchmarkProductCatalogConverter(Blackhole bh) throws FilloException {
        bh.consume(ProductCatalogConverter.run(filePath));
    }

    //Verify the average response time is less than 2 seconds
    // when 100 users access the method concurrently for 1 second
    @Test
    public void loadTest() throws RunnerException {
        double EXPECTED_AVG_TIME = 0.02;
        Options opt = new OptionsBuilder()
                .include(ProductCatalogConverterTest.class.getSimpleName() + ".benchmarkProductCatalogConverter")
                .mode(Mode.AverageTime)
                .threads(100)
                .forks(1)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(1))
                .timeUnit(TimeUnit.SECONDS)
                .addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                .addProfiler(AsyncProfiler.class)
                .build();
        Collection<RunResult> results = new Runner(opt).run();
        Assert.assertTrue("\nActual ["+getScore(results)+"]\nExpected ["+EXPECTED_AVG_TIME+"]",
                getScore(results) <= EXPECTED_AVG_TIME);
    }

    //Verify the load baseline as 3000 RPS when 50 concurrent users access the method for 1 second
    @Test
    public void baselineTest() throws RunnerException {
        double EXPECTED_TPT = 3000;
        Options opt = new OptionsBuilder()
                .include(ProductCatalogConverterTest.class.getSimpleName() + ".benchmarkProductCatalogConverter")
                .mode(Mode.Throughput)
                .threads(50)
                .forks(1)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(1))
                .timeUnit(TimeUnit.SECONDS)
                .build();
        Collection<RunResult> results = new Runner(opt).run();
        Assert.assertTrue(getScore(results) >= EXPECTED_TPT);
    }

    //Verify the response time is less than 2 seconds for a single request with high volume
    @Test
    public void volumeTest() throws RunnerException {
        double EXPECTED_AVG_TIME = 0.02;
        Options opt = new OptionsBuilder()
                .include(ProductCatalogConverterTest.class.getSimpleName() + ".benchmarkProductCatalogConverter")
                .mode(Mode.SingleShotTime)
                .param("filePath", "src/test/resources/product_catalog_50.xlsx")
                .forks(1)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(1))
                .timeUnit(TimeUnit.SECONDS)
                .addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                .addProfiler(AsyncProfiler.class)
                .build();
        Collection<RunResult> results = new Runner(opt).run();
        Assert.assertTrue(getScore(results) <= EXPECTED_AVG_TIME );
    }

    private double getScore(Collection<RunResult> results) {
        for (RunResult r : results) {
            for (BenchmarkResult rr : r.getBenchmarkResults()) {
                return rr.getPrimaryResult().getScore();
            }
        }
        return 0;
    }
}