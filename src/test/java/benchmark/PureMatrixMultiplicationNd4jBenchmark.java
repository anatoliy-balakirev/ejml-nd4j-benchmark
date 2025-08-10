package benchmark;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt
        Benchmark                                                         (matrixDimensions)  Mode  Cnt   Score    Error  Units
    * */
@State(Scope.Benchmark)
public class PureMatrixMultiplicationNd4jBenchmark extends PureMatrixMultiplicationBenchmarkAbstract<INDArray> {

    @Override
    public INDArray toNativeType(double[][] matrix) {
        return Nd4j.create(matrix);
    }

    @Benchmark
    public void testMatrixMultiplicationNd4J(Blackhole blackhole) {
        try {
            var result = firstMatrix.mmul(secondMatrix).toDoubleMatrix();
            blackhole.consume(result);
        } finally {
            firstMatrix.close();
            secondMatrix.close();
        }
    }
}
