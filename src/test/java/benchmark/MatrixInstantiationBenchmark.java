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
        Benchmark                                                        (matrixDimensions)  Mode  Cnt   Score    Error  Units
        MatrixInstantiationBenchmark.ejml                                1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ejml                                1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ejml                                2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ejml                            1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                             1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                              155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                            3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                             1x2;2x1000  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                             1x3;3x1000  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ejml                          1x155;155x155  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                           1x28;28x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                          3x155;155x155  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                            30x50;50x50  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                          28x155;155x28  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                          30x155;155x30  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                          7x155;155x155  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                         16x155;155x155  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ejml                       1000x150;150x150  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ejml                       2000x200;200x200  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationBenchmark.ejml                        500x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ejml                        300x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ejml                        200x250;250x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ejml                       2000x500;500x500  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationBenchmark.ejml                       3000x600;600x600  avgt    3   0.003 ±  0.001   s/op
        MatrixInstantiationBenchmark.ejml                       4000x800;800x800  avgt    3   0.005 ±  0.002   s/op
        MatrixInstantiationBenchmark.ejml                    5000x1000;1000x1000  avgt    3   0.009 ±  0.003   s/op
        MatrixInstantiationBenchmark.ejml                      3000x800;800x1000  avgt    3   0.004 ±  0.001   s/op
        MatrixInstantiationBenchmark.ejml                       9441x9441;9441x1  avgt    3   0.128 ±  0.034   s/op
        MatrixInstantiationBenchmark.ejml                       9441x155;155x155  avgt    3   0.002 ±  0.001   s/op
        MatrixInstantiationBenchmark.ejml                    2239x2289;2289x2339  avgt    3   0.015 ±  0.002   s/op
        MatrixInstantiationBenchmark.ejml                      9441x155;155x9441  avgt    3   0.004 ±  0.001   s/op
        MatrixInstantiationBenchmark.ejml                     155x9441;9441x9441  avgt    3   0.130 ±  0.016   s/op
        MatrixInstantiationBenchmark.ejml                    3000x3000;3000x3000  avgt    3   0.026 ±  0.010   s/op
        MatrixInstantiationBenchmark.ejml                    3300x3300;3300x3300  avgt    3   0.032 ±  0.008   s/op
        MatrixInstantiationBenchmark.ejml                    3500x3500;3500x3500  avgt    3   0.035 ±  0.006   s/op
        MatrixInstantiationBenchmark.ejml                    4000x4000;4000x4000  avgt    3   0.047 ±  0.010   s/op
        MatrixInstantiationBenchmark.ejml                    9441x9441;9441x9441  avgt    3   0.252 ±  0.141   s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                      1x1;1x1  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                      1x5;5x1  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                      2x2;2x2  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                  1x155;155x1  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                   1x18;18x18  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                    155x2;2x2  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                  3x155;155x3  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                   1x2;2x1000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                   1x3;3x1000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                1x155;155x155  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                 1x28;28x1000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                3x155;155x155  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                  30x50;50x50  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                28x155;155x28  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                30x155;155x30  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore                7x155;155x155  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore               16x155;155x155  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             1000x150;150x150  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             2000x200;200x200  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore              500x200;200x200  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore              300x200;200x200  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore              200x250;250x200  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             2000x500;500x500  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             3000x600;600x600  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             4000x800;800x800  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          5000x1000;1000x1000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore            3000x800;800x1000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             9441x9441;9441x1  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore             9441x155;155x155  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          2239x2289;2289x2339  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore            9441x155;155x9441  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore           155x9441;9441x9441  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          3000x3000;3000x3000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          3300x3300;3300x3300  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          3500x3500;3500x3500  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          4000x4000;4000x4000  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoRawStore          9441x9441;9441x9441  avgt    3  ≈ 10⁻⁸            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                     1x1;1x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                     1x5;5x1  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                     2x2;2x2  avgt    3  ≈ 10⁻⁷            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                 1x155;155x1  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                  1x18;18x18  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                   155x2;2x2  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                 3x155;155x3  avgt    3  ≈ 10⁻⁶            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                  1x2;2x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                  1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store               1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store               3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store                 30x50;50x50  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store               28x155;155x28  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store               30x155;155x30  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store               7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store              16x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            1000x150;150x150  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            2000x200;200x200  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store             500x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store             300x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store             200x250;250x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            2000x500;500x500  avgt    3   0.004 ±  0.002   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            3000x600;600x600  avgt    3   0.006 ±  0.001   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            4000x800;800x800  avgt    3   0.010 ±  0.001   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         5000x1000;1000x1000  avgt    3   0.015 ±  0.003   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store           3000x800;800x1000  avgt    3   0.008 ±  0.001   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            9441x9441;9441x1  avgt    3   0.315 ±  0.103   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store            9441x155;155x155  avgt    3   0.012 ±  0.002   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         2239x2289;2289x2339  avgt    3   0.023 ±  0.003   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store           9441x155;155x9441  avgt    3   0.016 ±  0.005   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store          155x9441;9441x9441  avgt    3   0.350 ±  0.113   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         3000x3000;3000x3000  avgt    3   0.035 ±  0.005   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         3300x3300;3300x3300  avgt    3   0.041 ±  0.003   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         3500x3500;3500x3500  avgt    3   0.045 ±  0.005   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         4000x4000;4000x4000  avgt    3   0.058 ±  0.003   s/op
        MatrixInstantiationBenchmark.ojAlgoR064Store         9441x9441;9441x9441  avgt    3   0.660 ±  0.285   s/op
        MatrixInstantiationBenchmark.nd4j                                1x1;1x1  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                                1x5;5x1  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                                2x2;2x2  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                            1x155;155x1  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                             1x18;18x18  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                              155x2;2x2  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                            3x155;155x3  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                             1x2;2x1000  avgt    3  ≈ 10⁻⁵            s/op
        MatrixInstantiationBenchmark.nd4j                             1x3;3x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                          1x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                           1x28;28x1000  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                          3x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                            30x50;50x50  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                          28x155;155x28  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                          30x155;155x30  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                          7x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                         16x155;155x155  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                       1000x150;150x150  avgt    3  ≈ 10⁻³            s/op
        MatrixInstantiationBenchmark.nd4j                       2000x200;200x200  avgt    3   0.001 ±  0.001   s/op
        MatrixInstantiationBenchmark.nd4j                        500x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                        300x200;200x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                        200x250;250x200  avgt    3  ≈ 10⁻⁴            s/op
        MatrixInstantiationBenchmark.nd4j                       2000x500;500x500  avgt    3   0.003 ±  0.001   s/op
        MatrixInstantiationBenchmark.nd4j                       3000x600;600x600  avgt    3   0.006 ±  0.001   s/op
        MatrixInstantiationBenchmark.nd4j                       4000x800;800x800  avgt    3   0.011 ±  0.001   s/op
        MatrixInstantiationBenchmark.nd4j                    5000x1000;1000x1000  avgt    3   0.021 ±  0.003   s/op
        MatrixInstantiationBenchmark.nd4j                      3000x800;800x1000  avgt    3   0.009 ±  0.003   s/op
        MatrixInstantiationBenchmark.nd4j                       9441x9441;9441x1  avgt    3   0.316 ±  0.103   s/op
        MatrixInstantiationBenchmark.nd4j                       9441x155;155x155  avgt    3   0.004 ±  0.001   s/op
        MatrixInstantiationBenchmark.nd4j                    2239x2289;2289x2339  avgt    3   0.041 ±  0.011   s/op
        MatrixInstantiationBenchmark.nd4j                      9441x155;155x9441  avgt    3   0.008 ±  0.004   s/op
        MatrixInstantiationBenchmark.nd4j                     155x9441;9441x9441  avgt    3   0.319 ±  0.043   s/op
        MatrixInstantiationBenchmark.nd4j                    3000x3000;3000x3000  avgt    3   0.068 ±  0.029   s/op
        MatrixInstantiationBenchmark.nd4j                    3300x3300;3300x3300  avgt    3   0.081 ±  0.037   s/op
        MatrixInstantiationBenchmark.nd4j                    3500x3500;3500x3500  avgt    3   0.090 ±  0.026   s/op
        MatrixInstantiationBenchmark.nd4j                    4000x4000;4000x4000  avgt    3   0.116 ±  0.025   s/op
        MatrixInstantiationBenchmark.nd4j                    9441x9441;9441x9441  avgt    3   0.626 ±  0.170   s/op
* */
@State(Scope.Benchmark)
public class MatrixInstantiationBenchmark extends DoubleArrayInstantiationAbstract {

    @Benchmark
    public void ejml(Blackhole blackhole) {
        blackhole.consume(new SimpleMatrix(firstArray));
        blackhole.consume(new SimpleMatrix(secondArray));
    }

    @Benchmark
    public void ojAlgoRawStore(Blackhole blackhole) {
        blackhole.consume(RawStore.wrap(firstArray));
        blackhole.consume(RawStore.wrap(secondArray));
    }

    @Benchmark
    public void ojAlgoR064Store(Blackhole blackhole) {
        blackhole.consume(R064Store.FACTORY.copy(RawStore.wrap(firstArray)));
        blackhole.consume(R064Store.FACTORY.copy(RawStore.wrap(secondArray)));
    }

    @Benchmark
    public void nd4j(Blackhole blackhole) {
        try (INDArray indArray1 = Nd4j.create(firstArray); INDArray indArray2 = Nd4j.create(secondArray)) {
            blackhole.consume(indArray1);
            blackhole.consume(indArray2);
        }
    }
}
