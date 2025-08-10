package benchmark;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixMultiplicationBenchmarkNd4J"
        Benchmark                                    (matrixDimensions)  Mode  Cnt   Score    Error  Units
        MatrixMultiplicationBenchmarkNd4J.multiply              1x1;1x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply              1x5;5x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply              2x2;2x2  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply          1x155;155x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply           1x18;18x18  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply            155x2;2x2  avgt    3   0.003 ±  0.001   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply          3x155;155x3  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply           1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply           1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply        1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply         1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply        3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply          30x50;50x50  avgt    3   0.001 ±  0.001   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply        28x155;155x28  avgt    3   0.001 ±  0.001   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply        30x155;155x30  avgt    3   0.001 ±  0.001   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply        7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixMultiplicationBenchmarkNd4J.multiply       16x155;155x155  avgt    3   0.026 ±  0.029   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     1000x150;150x150  avgt    3   0.048 ±  0.020   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     2000x200;200x200  avgt    3   0.075 ±  0.075   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply      500x200;200x200  avgt    3   0.040 ±  0.021   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply      300x200;200x200  avgt    3   0.034 ±  0.011   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply      200x250;250x200  avgt    3   0.032 ±  0.019   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     2000x500;500x500  avgt    3   0.083 ±  0.060   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     3000x600;600x600  avgt    3   0.119 ±  0.149   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     4000x800;800x800  avgt    3   0.175 ±  0.077   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  5000x1000;1000x1000  avgt    3   0.242 ±  0.174   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply    3000x800;800x1000  avgt    3   0.146 ±  0.175   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     9441x9441;9441x1  avgt    3   0.259 ±  0.176   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply     9441x155;155x155  avgt    3   0.283 ±  0.197   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  2239x2289;2289x2339  avgt    3   0.217 ±  0.249   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply    9441x155;155x9441  avgt    3   1.088 ±  0.629   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply   155x9441;9441x9441  avgt    3   0.335 ±  0.215   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  3000x3000;3000x3000  avgt    3   0.364 ±  0.266   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  3300x3300;3300x3300  avgt    3   0.458 ±  0.232   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  3500x3500;3500x3500  avgt    3   0.497 ±  0.116   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  4000x4000;4000x4000  avgt    3   0.655 ±  0.429   s/op
        MatrixMultiplicationBenchmarkNd4J.multiply  9441x9441;9441x9441  avgt    3   6.157 ±  1.819   s/op
    * */
@State(Scope.Benchmark)
public class MatrixMultiplicationBenchmarkNd4J extends MatrixMultiplicationBenchmarkAbstract<INDArray> {

    @Override
    public INDArray toNativeType(double[][] matrix) {
        return Nd4j.create(matrix);
    }

    @Benchmark
    public void multiply(Blackhole blackhole) {
        var result = firstMatrix.mmul(secondMatrix).toDoubleMatrix();
        blackhole.consume(result);
    }

    @TearDown
    public void tearDown() {
        if (firstMatrix != null) {
            firstMatrix.close();
        }
        if (secondMatrix != null) {
            secondMatrix.close();
        }
    }
}
