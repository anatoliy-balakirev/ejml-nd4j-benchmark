package benchmark;

import org.ojalgo.matrix.store.RawStore;
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
