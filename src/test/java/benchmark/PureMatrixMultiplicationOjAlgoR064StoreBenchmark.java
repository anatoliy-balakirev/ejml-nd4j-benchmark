package benchmark;

import org.ojalgo.matrix.store.R064Store;
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
public class PureMatrixMultiplicationOjAlgoR064StoreBenchmark extends PureMatrixMultiplicationBenchmarkAbstract<R064Store> {

    @Override
    public R064Store toNativeType(double[][] matrix) {
        return R064Store.FACTORY.copy(RawStore.wrap(matrix));
    }

    @Benchmark
    public void testMatrixMultiplicationOjAlgoParallel(Blackhole blackhole) {
        var result = firstMatrix.multiply(secondMatrix);
        blackhole.consume(result);
    }
}
