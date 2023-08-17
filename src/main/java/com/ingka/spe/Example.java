package com.ingka.spe;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Fork(1) // Run entire benchmark class twice, 1 warmup + 1 measurement (warms up the JIT + caches)
@Warmup(iterations = 3, time= 2_000, timeUnit = TimeUnit.MILLISECONDS) // Run each individual benchmark 3x times before measuring (2 sec each)
@Measurement(iterations = 3, time= 2_000, timeUnit = TimeUnit.MILLISECONDS) //  Run each individual benchmark 3x time for measuring (2 sec each)
@State(Scope.Benchmark)
public class Example {

    // Run each benchmark once for each value
    @Param({"10000"})
    private int max;

    @Benchmark
    public void defaultCapacity() {

        // Default capacity list
        List<Integer> list = new ArrayList<>();
        for (int n = 0; n < max; n++) {
            list.add(n);
        }

    }

    @Benchmark
    public void predefinedCapacity() {

        // Explicit initial capacity
        List<Integer> list = new ArrayList<>(max);
        for (int n = 0; n < max; n++) {
            list.add(n);
        }

    }

    @Benchmark
    public void streams() {
        List<Integer> list = IntStream.range(0, max)
                .boxed()
                .collect(Collectors.toList());
    }
}

