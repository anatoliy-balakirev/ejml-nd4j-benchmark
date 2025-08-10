package benchmark;

import org.ejml.simple.SimpleMatrix;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixMultiplicationBenchmarkEjml"
        Benchmark                                    (matrixDimensions)  Mode  Cnt    Score    Error  Units
        MatrixMultiplicationBenchmarkEjml.multiply              1x1;1x1  avgt    3   ≈ 10⁻⁸            s/op
        MatrixMultiplicationBenchmarkEjml.multiply              1x5;5x1  avgt    3   ≈ 10⁻⁸            s/op
        MatrixMultiplicationBenchmarkEjml.multiply              2x2;2x2  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkEjml.multiply          1x155;155x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkEjml.multiply           1x18;18x18  avgt    3   ≈ 10⁻⁷            s/op
        MatrixMultiplicationBenchmarkEjml.multiply            155x2;2x2  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkEjml.multiply          3x155;155x3  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkEjml.multiply           1x2;2x1000  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkEjml.multiply           1x3;3x1000  avgt    3   ≈ 10⁻⁶            s/op
        MatrixMultiplicationBenchmarkEjml.multiply        1x155;155x155  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkEjml.multiply         1x28;28x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixMultiplicationBenchmarkEjml.multiply        3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply          30x50;50x50  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply        28x155;155x28  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply        30x155;155x30  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply        7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply       16x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkEjml.multiply     1000x150;150x150  avgt    3    0.002 ±  0.001   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     2000x200;200x200  avgt    3    0.005 ±  0.001   s/op
        MatrixMultiplicationBenchmarkEjml.multiply      500x200;200x200  avgt    3    0.002 ±  0.001   s/op
        MatrixMultiplicationBenchmarkEjml.multiply      300x200;200x200  avgt    3    0.001 ±  0.001   s/op
        MatrixMultiplicationBenchmarkEjml.multiply      200x250;250x200  avgt    3    0.004 ±  0.001   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     2000x500;500x500  avgt    3    0.031 ±  0.003   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     3000x600;600x600  avgt    3    0.067 ±  0.008   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     4000x800;800x800  avgt    3    0.156 ±  0.047   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  5000x1000;1000x1000  avgt    3    0.300 ±  0.064   s/op
        MatrixMultiplicationBenchmarkEjml.multiply    3000x800;800x1000  avgt    3    0.147 ±  0.031   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     9441x9441;9441x1  avgt    3    0.057 ±  0.027   s/op
        MatrixMultiplicationBenchmarkEjml.multiply     9441x155;155x155  avgt    3    0.014 ±  0.003   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  2239x2289;2289x2339  avgt    3    1.919 ±  1.623   s/op
        MatrixMultiplicationBenchmarkEjml.multiply    9441x155;155x9441  avgt    3    0.839 ±  0.104   s/op
        MatrixMultiplicationBenchmarkEjml.multiply   155x9441;9441x9441  avgt    3    2.607 ±  0.619   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  3000x3000;3000x3000  avgt    3    5.753 ±  0.949   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  3300x3300;3300x3300  avgt    3    7.900 ±  3.414   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  3500x3500;3500x3500  avgt    3    8.494 ±  3.831   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  4000x4000;4000x4000  avgt    3   12.114 ±  7.547   s/op
        MatrixMultiplicationBenchmarkEjml.multiply  9441x9441;9441x9441  avgt    3  170.100 ± 14.861   s/op
    * */
@State(Scope.Benchmark)
public class MatrixMultiplicationBenchmarkEjml extends MatrixMultiplicationBenchmarkAbstract<SimpleMatrix> {

    @Override
    public SimpleMatrix toNativeType(double[][] matrix) {
        return new SimpleMatrix(matrix);
    }

    @Benchmark
    public void multiply(Blackhole blackhole) {
        var result = firstMatrix.mult(secondMatrix);
        blackhole.consume(result);
    }
}
