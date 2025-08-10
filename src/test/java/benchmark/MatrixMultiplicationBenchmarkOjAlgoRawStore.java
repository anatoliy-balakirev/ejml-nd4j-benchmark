package benchmark;

import org.ojalgo.matrix.store.RawStore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixMultiplicationBenchmarkOjAlgoRawStore"
        Benchmark                                              (matrixDimensions)  Mode  Cnt    Score     Error  Units
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply              1x1;1x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply              1x5;5x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply              2x2;2x2  avgt    3   ≈ 10⁻⁷             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply          1x155;155x1  avgt    3   ≈ 10⁻⁶             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply           1x18;18x18  avgt    3   ≈ 10⁻⁶             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply            155x2;2x2  avgt    3   ≈ 10⁻⁵             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply          3x155;155x3  avgt    3   ≈ 10⁻⁶             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply           1x2;2x1000  avgt    3   ≈ 10⁻⁵             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply           1x3;3x1000  avgt    3   ≈ 10⁻⁵             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply        1x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply         1x28;28x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply        3x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply          30x50;50x50  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply        28x155;155x28  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply        30x155;155x30  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply        7x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply       16x155;155x155  avgt    3   ≈ 10⁻³             s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     1000x150;150x150  avgt    3    0.018 ±   0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     2000x200;200x200  avgt    3    0.066 ±   0.004   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply      500x200;200x200  avgt    3    0.016 ±   0.003   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply      300x200;200x200  avgt    3    0.010 ±   0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply      200x250;250x200  avgt    3    0.008 ±   0.001   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     2000x500;500x500  avgt    3    0.444 ±   0.048   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     3000x600;600x600  avgt    3    1.290 ±   1.112   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     4000x800;800x800  avgt    3    3.184 ±   1.129   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  5000x1000;1000x1000  avgt    3    6.164 ±   1.984   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply    3000x800;800x1000  avgt    3    2.873 ±   0.935   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     9441x9441;9441x1  avgt    3    0.096 ±   0.022   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply     9441x155;155x155  avgt    3    0.230 ±   0.049   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  2239x2289;2289x2339  avgt    3   14.462 ±   3.825   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply    9441x155;155x9441  avgt    3   23.392 ± 115.635   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply   155x9441;9441x9441  avgt    3   14.938 ±   2.484   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  3000x3000;3000x3000  avgt    3   28.641 ±   0.624   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  3300x3300;3300x3300  avgt    3   40.726 ±  43.516   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  3500x3500;3500x3500  avgt    3   45.247 ±   1.928   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  4000x4000;4000x4000  avgt    3   66.928 ±   8.206   s/op
        MatrixMultiplicationBenchmarkOjAlgoRawStore.multiply  9441x9441;9441x9441  avgt    3  853.501 ±  30.950   s/op
    * */
@State(Scope.Benchmark)
public class MatrixMultiplicationBenchmarkOjAlgoRawStore extends MatrixMultiplicationBenchmarkAbstract<RawStore> {

    @Override
    public RawStore toNativeType(double[][] matrix) {
        return RawStore.wrap(matrix);
    }

    @Benchmark
    public void multiply(Blackhole blackhole) {
        var result = firstMatrix.multiply(secondMatrix);
        blackhole.consume(result);
    }
}
