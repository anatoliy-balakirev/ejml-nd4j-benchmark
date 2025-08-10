package benchmark;

import org.ejml.simple.SimpleMatrix;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixMultiplicationBenchmarkEjml"
        Benchmark                                                         (matrixDimensions)  Mode  Cnt   Score    Error  Units
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
