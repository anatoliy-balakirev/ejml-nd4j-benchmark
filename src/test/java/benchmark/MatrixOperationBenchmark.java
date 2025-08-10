package benchmark;

import org.ejml.simple.SimpleMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
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
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                      155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    3x155;155x3  avgt    3  ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x2;2x1000  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x3;3x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                   1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                 16x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               1000x150;150x150  avgt    3   0.002 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               2000x200;200x200  avgt    3   0.006 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                500x200;200x200  avgt    3   0.002 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                300x200;200x200  avgt    3   0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                200x250;250x200  avgt    3   0.004 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               2000x500;500x500  avgt    3   0.033 ±  0.004   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               3000x600;600x600  avgt    3   0.074 ±  0.008   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               4000x800;800x800  avgt    3   0.169 ±  0.039   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            5000x1000;1000x1000  avgt    3   0.318 ±  0.044   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              3000x800;800x1000  avgt    3   0.158 ±  0.036   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               9441x9441;9441x1  avgt    3   0.199 ±  0.022   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               9441x155;155x155  avgt    3   0.019 ±  0.003   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            2239x2289;2289x2339  avgt    3   2.184 ±  1.178   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              9441x155;155x9441  avgt    3   0.847 ±  0.113   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml             155x9441;9441x9441  avgt    3   2.779 ±  1.314   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3000x3000;3000x3000  avgt    3   5.867 ± 16.654   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3300x3300;3300x3300  avgt    3   7.930 ±  3.886   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3500x3500;3500x3500  avgt    3  10.075 ±  5.028   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            4000x4000;4000x4000  avgt    3   12.521 ±   7.746   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            9441x9441;9441x9441  avgt    3  168.113 ± 125.444   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        1x1;1x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        1x5;5x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        2x2;2x2  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    1x155;155x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x18;18x18  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                      155x2;2x2  avgt    3   0.003 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    3x155;155x3  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                   1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    30x50;50x50  avgt    3   0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  28x155;155x28  avgt    3   0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  30x155;155x30  avgt    3   0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  7x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                 16x155;155x155  avgt    3   0.026 ±  0.023   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               1000x150;150x150  avgt    3   0.049 ±  0.034   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               2000x200;200x200  avgt    3   0.077 ±  0.031   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                500x200;200x200  avgt    3   0.040 ±  0.043   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                300x200;200x200  avgt    3   0.035 ±  0.012   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                200x250;250x200  avgt    3   0.032 ±  0.029   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               2000x500;500x500  avgt    3   0.089 ±  0.085   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               3000x600;600x600  avgt    3   0.133 ±  0.392   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               4000x800;800x800  avgt    3   0.180 ±  0.231   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            5000x1000;1000x1000  avgt    3   0.249 ±  0.190   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              3000x800;800x1000  avgt    3   0.164 ±  0.195   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               9441x9441;9441x1  avgt    3   0.543 ±  0.256   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               9441x155;155x155  avgt    3   0.279 ±  0.212   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            2239x2289;2289x2339  avgt    3   0.252 ±  0.193   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              9441x155;155x9441  avgt    3   1.048 ±  2.196   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J             155x9441;9441x9441  avgt    3   0.650 ±  0.270   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3000x3000;3000x3000  avgt    3   0.405 ±  0.301   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3300x3300;3300x3300  avgt    3   0.534 ±  0.419   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3500x3500;3500x3500  avgt    3   0.598 ±  0.433   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            4000x4000;4000x4000  avgt    3   0.783 ±   0.433  s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            9441x9441;9441x9441  avgt    3   6.792 ±   4.294  s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                    155x2;2x2  avgt    3  ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x2;2x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x3;3x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                 1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo               16x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             1000x150;150x150  avgt    3   0.018 ±  0.004   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             2000x200;200x200  avgt    3   0.065 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              500x200;200x200  avgt    3   0.016 ±  0.003   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              300x200;200x200  avgt    3   0.010 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              200x250;250x200  avgt    3   0.008 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             2000x500;500x500  avgt    3   0.442 ±  0.034   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             3000x600;600x600  avgt    3   1.120 ±  0.195   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             4000x800;800x800  avgt    3   2.950 ±  0.641   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          5000x1000;1000x1000  avgt    3   5.790 ±  2.982   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo            3000x800;800x1000  avgt    3   2.943 ±  2.197   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             9441x9441;9441x1  avgt    3   0.091 ±  0.013   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             9441x155;155x155  avgt    3   0.199 ±  0.031   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          2239x2289;2289x2339  avgt    3  13.843 ±  2.648   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo            9441x155;155x9441  avgt    3  21.660 ± 89.830   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo           155x9441;9441x9441  avgt    3  14.941 ±  0.226   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3000x3000;3000x3000  avgt    3  32.829 ± 14.060   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3300x3300;3300x3300  avgt    3  37.471 ±  2.889   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3500x3500;3500x3500  avgt    3  48.747 ± 67.275   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          4000x4000;4000x4000  avgt    3  67.452 ± 1.599    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          9441x9441;9441x9441  avgt    3  850.949 ± 80.546  s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel            155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel         1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel       16x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     1000x150;150x150  avgt    3   0.005 ±  0.002   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     2000x200;200x200  avgt    3   0.015 ±  0.006   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      500x200;200x200  avgt    3   0.005 ±  0.002   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      300x200;200x200  avgt    3   0.003 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      200x250;250x200  avgt    3   0.003 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     2000x500;500x500  avgt    3   0.053 ±  0.012   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     3000x600;600x600  avgt    3   0.097 ±  0.010   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     4000x800;800x800  avgt    3   0.464 ±  0.127   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  5000x1000;1000x1000  avgt    3   0.898 ±  0.527   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel    3000x800;800x1000  avgt    3   0.309 ±  0.086   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     9441x9441;9441x1  avgt    3   0.405 ±  0.104   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     9441x155;155x155  avgt    3   0.041 ±  0.002   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  2239x2289;2289x2339  avgt    3   2.100 ±  1.290   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel    9441x155;155x9441  avgt    3   0.997 ±  1.977   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel   155x9441;9441x9441  avgt    3   1.227 ±  0.217   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3000x3000;3000x3000  avgt    3   5.932 ±  9.180   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3300x3300;3300x3300  avgt    3   8.063 ±  6.989   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3500x3500;3500x3500  avgt    3   9.669 ±  1.651   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  4000x4000;4000x4000  avgt    3  14.067 ±  13.480  s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  9441x9441;9441x9441  avgt    3 157.278 ± 230.624  s/op
    * */
@State(Scope.Benchmark)
public class MatrixOperationBenchmark extends DoubleArrayInstantiationAbstract {

    @Benchmark
    public void testMatrixMultiplicationEjml(Blackhole blackhole) {
        var result = new SimpleMatrix(firstArray).mult(new SimpleMatrix(secondArray));
        blackhole.consume(result);
    }

    @Benchmark
    public void testMatrixMultiplicationNd4J(Blackhole blackhole) {
        try (INDArray indArray1 = Nd4j.create(firstArray); INDArray indArray2 = Nd4j.create(secondArray)) {
            var result = indArray1.mmul(indArray2).toDoubleMatrix();
            blackhole.consume(result);
        }
    }

    @Benchmark
    public void testMatrixMultiplicationOjAlgo(Blackhole blackhole) {
        var result = RawStore.wrap(firstArray).multiply(RawStore.wrap(secondArray));
        blackhole.consume(result);
    }

    @Benchmark
    public void testMatrixMultiplicationOjAlgoParallel(Blackhole blackhole) {
        var result = R064Store.FACTORY.copy(RawStore.wrap(firstArray)).multiply(R064Store.FACTORY.copy(RawStore.wrap(secondArray)));
        blackhole.consume(result);
    }
}
