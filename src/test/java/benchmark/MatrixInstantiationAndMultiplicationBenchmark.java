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
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt -Djmh.benchmarks="benchmark.MatrixInstantiationAndMultiplicationBenchmark"
        Benchmark                                                         (matrixDimensions)  Mode  Cnt   Score    Error  Units
        MatrixInstantiationAndMultiplicationBenchmark.ejml                                1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                                1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                                2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                            1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                             1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                              155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                            3x155;155x3  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                             1x2;2x1000  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                             1x3;3x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                          1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                           1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                          3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                            30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                          28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                          30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                          7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                         16x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       1000x150;150x150  avgt    3   0.002 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       2000x200;200x200  avgt    3   0.006 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                        500x200;200x200  avgt    3   0.002 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                        300x200;200x200  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                        200x250;250x200  avgt    3   0.004 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       2000x500;500x500  avgt    3   0.033 ±  0.004   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       3000x600;600x600  avgt    3   0.074 ±  0.008   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       4000x800;800x800  avgt    3   0.169 ±  0.039   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    5000x1000;1000x1000  avgt    3   0.318 ±  0.044   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                      3000x800;800x1000  avgt    3   0.158 ±  0.036   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       9441x9441;9441x1  avgt    3   0.199 ±  0.022   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                       9441x155;155x155  avgt    3   0.019 ±  0.003   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    2239x2289;2289x2339  avgt    3   2.184 ±  1.178   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                      9441x155;155x9441  avgt    3   0.847 ±  0.113   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                     155x9441;9441x9441  avgt    3   2.779 ±  1.314   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    3000x3000;3000x3000  avgt    3   5.867 ± 16.654   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    3300x3300;3300x3300  avgt    3   7.930 ±  3.886   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    3500x3500;3500x3500  avgt    3  10.075 ±  5.028   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    4000x4000;4000x4000  avgt    3   12.521 ±   7.746   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ejml                    9441x9441;9441x9441  avgt    3  168.113 ± 125.444   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                      1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                      1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                      2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                  1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                   1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                    155x2;2x2  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                  3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                   1x2;2x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                   1x3;3x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                 1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                  30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore                7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore               16x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             1000x150;150x150  avgt    3   0.018 ±  0.004   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             2000x200;200x200  avgt    3   0.065 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore              500x200;200x200  avgt    3   0.016 ±  0.003   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore              300x200;200x200  avgt    3   0.010 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore              200x250;250x200  avgt    3   0.008 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             2000x500;500x500  avgt    3   0.442 ±  0.034   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             3000x600;600x600  avgt    3   1.120 ±  0.195   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             4000x800;800x800  avgt    3   2.950 ±  0.641   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          5000x1000;1000x1000  avgt    3   5.790 ±  2.982   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore            3000x800;800x1000  avgt    3   2.943 ±  2.197   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             9441x9441;9441x1  avgt    3   0.091 ±  0.013   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore             9441x155;155x155  avgt    3   0.199 ±  0.031   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          2239x2289;2289x2339  avgt    3  13.843 ±  2.648   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore            9441x155;155x9441  avgt    3  21.660 ± 89.830   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore           155x9441;9441x9441  avgt    3  14.941 ±  0.226   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          3000x3000;3000x3000  avgt    3  32.829 ± 14.060   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          3300x3300;3300x3300  avgt    3  37.471 ±  2.889   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          3500x3500;3500x3500  avgt    3  48.747 ± 67.275   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          4000x4000;4000x4000  avgt    3  67.452 ± 1.599    s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoRawStore          9441x9441;9441x9441  avgt    3  850.949 ± 80.546  s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                     1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                     1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                     2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                 1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                  1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                   155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                 3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                  1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                  1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store               1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store               3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store                 30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store               28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store               30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store               7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store              16x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            1000x150;150x150  avgt    3   0.005 ±  0.002   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            2000x200;200x200  avgt    3   0.015 ±  0.006   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store             500x200;200x200  avgt    3   0.005 ±  0.002   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store             300x200;200x200  avgt    3   0.003 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store             200x250;250x200  avgt    3   0.003 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            2000x500;500x500  avgt    3   0.053 ±  0.012   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            3000x600;600x600  avgt    3   0.097 ±  0.010   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            4000x800;800x800  avgt    3   0.464 ±  0.127   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         5000x1000;1000x1000  avgt    3   0.898 ±  0.527   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store           3000x800;800x1000  avgt    3   0.309 ±  0.086   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            9441x9441;9441x1  avgt    3   0.405 ±  0.104   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store            9441x155;155x155  avgt    3   0.041 ±  0.002   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         2239x2289;2289x2339  avgt    3   2.100 ±  1.290   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store           9441x155;155x9441  avgt    3   0.997 ±  1.977   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store          155x9441;9441x9441  avgt    3   1.227 ±  0.217   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         3000x3000;3000x3000  avgt    3   5.932 ±  9.180   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         3300x3300;3300x3300  avgt    3   8.063 ±  6.989   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         3500x3500;3500x3500  avgt    3   9.669 ±  1.651   s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         4000x4000;4000x4000  avgt    3  14.067 ±  13.480  s/op
        MatrixInstantiationAndMultiplicationBenchmark.ojAlgoR064Store         9441x9441;9441x9441  avgt    3 157.278 ± 230.624  s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                                1x1;1x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                                1x5;5x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                                2x2;2x2  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                            1x155;155x1  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                             1x18;18x18  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                              155x2;2x2  avgt    3   0.003 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                            3x155;155x3  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                             1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                             1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                          1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                           1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                          3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                            30x50;50x50  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                          28x155;155x28  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                          30x155;155x30  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                          7x155;155x155  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                         16x155;155x155  avgt    3   0.026 ±  0.023   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       1000x150;150x150  avgt    3   0.049 ±  0.034   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       2000x200;200x200  avgt    3   0.077 ±  0.031   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                        500x200;200x200  avgt    3   0.040 ±  0.043   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                        300x200;200x200  avgt    3   0.035 ±  0.012   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                        200x250;250x200  avgt    3   0.032 ±  0.029   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       2000x500;500x500  avgt    3   0.089 ±  0.085   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       3000x600;600x600  avgt    3   0.133 ±  0.392   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       4000x800;800x800  avgt    3   0.180 ±  0.231   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    5000x1000;1000x1000  avgt    3   0.249 ±  0.190   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                      3000x800;800x1000  avgt    3   0.164 ±  0.195   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       9441x9441;9441x1  avgt    3   0.543 ±  0.256   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                       9441x155;155x155  avgt    3   0.279 ±  0.212   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    2239x2289;2289x2339  avgt    3   0.252 ±  0.193   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                      9441x155;155x9441  avgt    3   1.048 ±  2.196   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                     155x9441;9441x9441  avgt    3   0.650 ±  0.270   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    3000x3000;3000x3000  avgt    3   0.405 ±  0.301   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    3300x3300;3300x3300  avgt    3   0.534 ±  0.419   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    3500x3500;3500x3500  avgt    3   0.598 ±  0.433   s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    4000x4000;4000x4000  avgt    3   0.783 ±   0.433  s/op
        MatrixInstantiationAndMultiplicationBenchmark.nd4j                    9441x9441;9441x9441  avgt    3   6.792 ±   4.294  s/op
    * */
@State(Scope.Benchmark)
public class MatrixInstantiationAndMultiplicationBenchmark extends DoubleArrayInstantiationAbstract {

    @Benchmark
    public void ejml(Blackhole blackhole) {
        var result = new SimpleMatrix(firstArray).mult(new SimpleMatrix(secondArray));
        blackhole.consume(result);
    }

    @Benchmark
    public void ojAlgoRawStore(Blackhole blackhole) {
        var result = RawStore.wrap(firstArray).multiply(RawStore.wrap(secondArray));
        blackhole.consume(result);
    }

    @Benchmark
    public void ojAlgoR064Store(Blackhole blackhole) {
        var result = R064Store.FACTORY.copy(RawStore.wrap(firstArray)).multiply(R064Store.FACTORY.copy(RawStore.wrap(secondArray)));
        blackhole.consume(result);
    }

    @Benchmark
    public void nd4j(Blackhole blackhole) {
        try (INDArray indArray1 = Nd4j.create(firstArray); INDArray indArray2 = Nd4j.create(secondArray)) {
            var result = indArray1.mmul(indArray2).toDoubleMatrix();
            blackhole.consume(result);
        }
    }
}
