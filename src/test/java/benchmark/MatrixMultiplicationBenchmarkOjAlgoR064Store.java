package benchmark;

import org.ojalgo.matrix.store.R064Store;
import org.ojalgo.matrix.store.RawStore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixMultiplicationBenchmarkOjAlgoR064Store"
        Benchmark                                               (matrixDimensions)  Mode  Cnt    Score    Error  Units
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply              1x1;1x1  avgt    3   ≈ 10⁻⁸            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply              1x5;5x1  avgt    3   ≈ 10⁻⁸            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply              2x2;2x2  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply          1x155;155x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply           1x18;18x18  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply            155x2;2x2  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply          3x155;155x3  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply           1x2;2x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply           1x3;3x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply        1x155;155x155  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply         1x28;28x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply        3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply          30x50;50x50  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply        28x155;155x28  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply        30x155;155x30  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply        7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply       16x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     1000x150;150x150  avgt    3    0.004 ±  0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     2000x200;200x200  avgt    3    0.011 ±  0.003   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply      500x200;200x200  avgt    3    0.003 ±  0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply      300x200;200x200  avgt    3    0.002 ±  0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply      200x250;250x200  avgt    3    0.002 ±  0.002   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     2000x500;500x500  avgt    3    0.045 ±  0.026   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     3000x600;600x600  avgt    3    0.085 ±  0.072   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     4000x800;800x800  avgt    3    0.383 ±  0.111   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  5000x1000;1000x1000  avgt    3    0.961 ±  0.548   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply    3000x800;800x1000  avgt    3    0.296 ±  0.051   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     9441x9441;9441x1  avgt    3    0.055 ±  0.015   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply     9441x155;155x155  avgt    3    0.028 ±  0.003   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  2239x2289;2289x2339  avgt    3    2.054 ±  1.080   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply    9441x155;155x9441  avgt    3    0.888 ±  0.175   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply   155x9441;9441x9441  avgt    3    0.813 ±  0.059   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  3000x3000;3000x3000  avgt    3    5.487 ±  0.581   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  3300x3300;3300x3300  avgt    3    6.686 ±  0.678   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  3500x3500;3500x3500  avgt    3    8.572 ±  3.942   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  4000x4000;4000x4000  avgt    3   13.265 ±  6.814   s/op
        MatrixMultiplicationBenchmarkOjAlgoR064Store.multiply  9441x9441;9441x9441  avgt    3  151.616 ±  9.378   s/op

    * */
@State(Scope.Benchmark)
public class MatrixMultiplicationBenchmarkOjAlgoR064Store extends MatrixMultiplicationBenchmarkAbstract<R064Store> {

    @Override
    public R064Store toNativeType(double[][] matrix) {
        return R064Store.FACTORY.copy(RawStore.wrap(matrix));
    }

    @Benchmark
    public void multiply(Blackhole blackhole) {
        var result = firstMatrix.multiply(secondMatrix);
        blackhole.consume(result);
    }
}
