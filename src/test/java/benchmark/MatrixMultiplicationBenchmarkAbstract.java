package benchmark;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Group)
public abstract class MatrixMultiplicationBenchmarkAbstract<T> extends DoubleArrayInstantiationAbstract {
    T firstMatrix;
    T secondMatrix;

    @Setup
    public void setup() {
        this.firstMatrix = toNativeType(firstArray);
        this.secondMatrix = toNativeType(secondArray);
    }

    public abstract T toNativeType(double[][] matrix);
}
