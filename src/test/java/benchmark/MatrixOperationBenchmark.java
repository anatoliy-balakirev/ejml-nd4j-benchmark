package benchmark;

import java.util.Random;
import java.util.regex.Pattern;
import org.ejml.simple.SimpleMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.ojalgo.matrix.store.R064Store;
import org.ojalgo.matrix.store.RawStore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt
        Benchmark                                                         (matrixDimensions)  Mode  Cnt    Score     Error  Units
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        1x1;1x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        1x5;5x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                        2x2;2x2  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    1x155;155x1  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x18;18x18  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                      155x2;2x2  avgt    3   ≈ 10⁻⁵             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    3x155;155x3  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x2;2x1000  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                     1x3;3x1000  avgt    3   ≈ 10⁻⁵             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  1x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                   1x28;28x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  3x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                    30x50;50x50  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  28x155;155x28  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  30x155;155x30  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                  7x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                 16x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               1000x150;150x150  avgt    3    0.002 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               2000x200;200x200  avgt    3    0.006 ±   0.009   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                500x200;200x200  avgt    3    0.002 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                300x200;200x200  avgt    3    0.001 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml                200x250;250x200  avgt    3    0.004 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               2000x500;500x500  avgt    3    0.033 ±   0.004   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               3000x600;600x600  avgt    3    0.074 ±   0.037   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               4000x800;800x800  avgt    3    0.171 ±   0.028   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            5000x1000;1000x1000  avgt    3    0.318 ±   0.055   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              3000x800;800x1000  avgt    3    0.159 ±   0.024   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               9441x9441;9441x1  avgt    3    0.202 ±   0.035   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml               9441x155;155x155  avgt    3    0.019 ±   0.004   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            2239x2289;2289x2339  avgt    3    2.319 ±   3.476   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              9441x155;155x9441  avgt    3    0.863 ±   0.066   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml             155x9441;9441x9441  avgt    3    2.681 ±   0.582   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3000x3000;3000x3000  avgt    3    5.572 ±   9.568   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3300x3300;3300x3300  avgt    3    7.885 ±   4.665   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            3500x3500;3500x3500  avgt    3    8.530 ±   8.931   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            4000x4000;4000x4000  avgt    3   14.159 ±  30.938   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            9441x9441;9441x9441  avgt    3  154.883 ± 201.422   s/op

        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        1x1;1x1  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        1x5;5x1  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                        2x2;2x2  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    1x155;155x1  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x18;18x18  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                      155x2;2x2  avgt    3    0.003 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    3x155;155x3  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x2;2x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                     1x3;3x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  1x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                   1x28;28x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  3x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                    30x50;50x50  avgt    3    0.001 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  28x155;155x28  avgt    3    0.001 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  30x155;155x30  avgt    3    0.001 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                  7x155;155x155  avgt    3   ≈ 10⁻³             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                 16x155;155x155  avgt    3    0.025 ±   0.003   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               1000x150;150x150  avgt    3    0.048 ±   0.057   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               2000x200;200x200  avgt    3    0.075 ±   0.053   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                500x200;200x200  avgt    3    0.038 ±   0.012   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                300x200;200x200  avgt    3    0.033 ±   0.033   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J                200x250;250x200  avgt    3    0.031 ±   0.007   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               2000x500;500x500  avgt    3    0.087 ±   0.057   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               3000x600;600x600  avgt    3    0.122 ±   0.170   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               4000x800;800x800  avgt    3    0.202 ±   0.176   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            5000x1000;1000x1000  avgt    3    0.255 ±   0.084   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              3000x800;800x1000  avgt    3    0.165 ±   0.445   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               9441x9441;9441x1  avgt    3    0.724 ±   3.000   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J               9441x155;155x155  avgt    3    0.288 ±   0.200   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            2239x2289;2289x2339  avgt    3    0.267 ±   0.243   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              9441x155;155x9441  avgt    3    1.561 ±   4.792   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J             155x9441;9441x9441  avgt    3    0.666 ±   0.115   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3000x3000;3000x3000  avgt    3    0.417 ±   0.106   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3300x3300;3300x3300  avgt    3    0.558 ±   0.413   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            3500x3500;3500x3500  avgt    3    0.582 ±   0.464   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            4000x4000;4000x4000  avgt    3    0.801 ±   0.446   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            9441x9441;9441x9441  avgt    3    6.967 ±   4.973   s/op
        Benchmark                                                         (matrixDimensions)  Mode  Cnt    Score     Error  Units
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      1x1;1x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      1x5;5x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                      2x2;2x2  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  1x155;155x1  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x18;18x18  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                    155x2;2x2  avgt    3   ≈ 10⁻⁵             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  3x155;155x3  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x2;2x1000  avgt    3   ≈ 10⁻⁵             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                   1x3;3x1000  avgt    3   ≈ 10⁻⁵             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                1x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                 1x28;28x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                3x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                  30x50;50x50  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                28x155;155x28  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                30x155;155x30  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo                7x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo               16x155;155x155  avgt    3   ≈ 10⁻³             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             1000x150;150x150  avgt    3   0.018 ±    0.002   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             2000x200;200x200  avgt    3   0.066 ±    0.014   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              500x200;200x200  avgt    3   0.016 ±    0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              300x200;200x200  avgt    3   0.010 ±    0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo              200x250;250x200  avgt    3   0.009 ±    0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             2000x500;500x500  avgt    3   0.448 ±    0.050   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             3000x600;600x600  avgt    3   1.136 ±    0.722   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             4000x800;800x800  avgt    3   3.016 ±    1.690   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          5000x1000;1000x1000  avgt    3   5.951 ±    0.853   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo            3000x800;800x1000  avgt    3   2.858 ±    1.357   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             9441x9441;9441x1  avgt    3    0.093 ±   0.020   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo             9441x155;155x155  avgt    3    0.250 ±   0.092   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          2239x2289;2289x2339  avgt    3   14.383 ±   5.406   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo            9441x155;155x9441  avgt    3   25.509 ± 117.568   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo           155x9441;9441x9441  avgt    3   14.683 ±   1.317   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3000x3000;3000x3000  avgt    3   28.569 ±   1.156   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3300x3300;3300x3300  avgt    3   42.619 ±  85.974   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          3500x3500;3500x3500  avgt    3   48.554 ±  63.907   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          4000x4000;4000x4000  avgt    3   72.155 ±  94.036   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgo          9441x9441;9441x9441  avgt    3  852.608 ±  22.270   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              1x1;1x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              1x5;5x1  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel              2x2;2x2  avgt    3   ≈ 10⁻⁷             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          1x155;155x1  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x18;18x18  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel            155x2;2x2  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          3x155;155x3  avgt    3   ≈ 10⁻⁶             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x2;2x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel           1x3;3x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        1x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel         1x28;28x1000  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        3x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel          30x50;50x50  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        28x155;155x28  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        30x155;155x30  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel        7x155;155x155  avgt    3   ≈ 10⁻⁴             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel       16x155;155x155  avgt    3   ≈ 10⁻³             s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     1000x150;150x150  avgt    3    0.005 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     2000x200;200x200  avgt    3    0.015 ±   0.007   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      500x200;200x200  avgt    3    0.005 ±   0.002   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      300x200;200x200  avgt    3    0.003 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel      200x250;250x200  avgt    3    0.003 ±   0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     2000x500;500x500  avgt    3    0.052 ±   0.005   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     3000x600;600x600  avgt    3    0.096 ±   0.035   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     4000x800;800x800  avgt    3    0.479 ±   0.202   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  5000x1000;1000x1000  avgt    3    1.021 ±   1.142   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel    3000x800;800x1000  avgt    3    0.316 ±   0.179   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     9441x9441;9441x1  avgt    3    0.417 ±   0.006   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel     9441x155;155x155  avgt    3    0.041 ±   0.009   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  2239x2289;2289x2339  avgt    3    2.341 ±   1.519   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel    9441x155;155x9441  avgt    3    0.927 ±   0.151   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel   155x9441;9441x9441  avgt    3    1.192 ±   0.146   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3000x3000;3000x3000  avgt    3    5.505 ±   6.960   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3300x3300;3300x3300  avgt    3    6.931 ±   1.234   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  3500x3500;3500x3500  avgt    3    8.574 ±   3.094   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  4000x4000;4000x4000  avgt    3   12.008 ±  15.572   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationOjAlgoParallel  9441x9441;9441x9441  avgt    3  166.299 ±  38.479   s/op
    * */
@State(Scope.Benchmark)
public class MatrixOperationBenchmark {
    private final static Pattern X = Pattern.compile("x");
    private final static Pattern SEMICOLON = Pattern.compile(";");

    @Param({
          "1x1;1x1", // Matrix 1: 1 elements, Matrix 2: 1 elements, Compute Intensity: 1
          "1x5;5x1", // Matrix 1: 5 elements, Matrix 2: 5 elements, Compute Intensity: 5
          "2x2;2x2", // Matrix 1: 4 elements, Matrix 2: 4 elements, Compute Intensity: 8
          "1x155;155x1", // Matrix 1: 155 elements, Matrix 2: 155 elements, Compute Intensity: 155
          "1x18;18x18", // Matrix 1: 18 elements, Matrix 2: 324 elements, Compute Intensity: 324
          "155x2;2x2", // Matrix 1: 310 elements, Matrix 2: 4 elements, Compute Intensity: 620
          "3x155;155x3", // Matrix 1: 465 elements, Matrix 2: 465 elements, Compute Intensity: 1,395
          "1x2;2x1000", // Matrix 1: 2 elements, Matrix 2: 2000 elements, Compute Intensity: 2,000
          "1x3;3x1000", // Matrix 1: 3 elements, Matrix 2: 3000 elements, Compute Intensity: 3,000
          "1x155;155x155", // Matrix 1: 155 elements, Matrix 2: 24025 elements, Compute Intensity: 24,025
          "1x28;28x1000", // Matrix 1: 28 elements, Matrix 2: 28000 elements, Compute Intensity: 28,000
          "3x155;155x155", // Matrix 1: 465 elements, Matrix 2: 24025 elements, Compute Intensity: 72,075
          "30x50;50x50", // Matrix 1: 1500 elements, Matrix 2: 2500 elements, Compute Intensity: 75,000
          "28x155;155x28", // Matrix 1: 4340 elements, Matrix 2: 4340 elements, Compute Intensity: 121,520
          "30x155;155x30", // Matrix 1: 4650 elements, Matrix 2: 4650 elements, Compute Intensity: 139,500
          "7x155;155x155", // Matrix 1: 1085 elements, Matrix 2: 24025 elements, Compute Intensity: 168,175
          "16x155;155x155", // Matrix 1: 2480 elements, Matrix 2: 24025 elements, Compute Intensity: 384,400
          "1000x150;150x150", // 22.5M complexity
          "2000x200;200x200", // 80M complexity
          "500x200;200x200",  // 20M complexity
          "300x200;200x200",  // 12M complexity
          "200x250;250x200",  // 10M complexity
          "2000x500;500x500", // 500M complexity
          "3000x600;600x600", // 1.08B complexity
          "4000x800;800x800", // 2.56B complexity
          "5000x1000;1000x1000", // 5B complexity
          "3000x800;800x1000",  // 1.92B complexity
          "9441x9441;9441x1", // Matrix 1: 89132481 elements, Matrix 2: 9441 elements, Compute Intensity: 89,132,481
          "9441x155;155x155", // Matrix 1: 1463355 elements, Matrix 2: 24025 elements, Compute Intensity: 226,820,025
          "2239x2289;2289x2339", // Matrix 1: 5125071 elements, Matrix 2: 5353971 elements, Compute Intensity: 11,987,541,069
          "9441x155;155x9441", // Matrix 1: 1463355 elements, Matrix 2: 1463355 elements, Compute Intensity: 13,815,534,555
          "155x9441;9441x9441", // Matrix 1: 1463355 elements, Matrix 2: 89132481 elements, Compute Intensity: 13,815,534,555
          "3000x3000;3000x3000", // Matrix 1: 9000000 elements, Matrix 2: 9000000 elements, Compute Intensity: 27,000,000,000
          "3300x3300;3300x3300", // Matrix 1: 10,890,000 elements, Matrix 2: 10,890,000 elements, Compute Intensity: 35,937,000,000
          "3500x3500;3500x3500", // Matrix 1: 10,890,000 elements, Matrix 2: 10,890,000 elements, Compute Intensity: 42,875,000,000
          // "4000x4000;4000x4000", // Matrix 1: 16000000 elements, Matrix 2: 16000000 elements, Compute Intensity: 64,000,000,000
          // "9441x9441;9441x9441", // Matrix 1: 89132481 elements, Matrix 2: 89132481 elements, Compute Intensity: 841,499,753,121
    })
    private String matrixDimensions;
    double[][] firstMatrix;
    double[][] secondMatrix;

    @Setup
    public void setup() {
        String[] dimensions = SEMICOLON.split(matrixDimensions);
        String[] firstMatrixDimensions = X.split(dimensions[0]);
        String[] secondMatrixDimensions = X.split(dimensions[1]);
        firstMatrix = new double[Integer.parseInt(firstMatrixDimensions[0])][Integer.parseInt(firstMatrixDimensions[1])];
        secondMatrix = new double[Integer.parseInt(secondMatrixDimensions[0])][Integer.parseInt(secondMatrixDimensions[1])];
        Random random = new Random();

        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                firstMatrix[i][j] = random.nextDouble();
            }
        }

        for (int i = 0; i < secondMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[i].length; j++) {
                secondMatrix[i][j] = random.nextDouble();
            }
        }
    }

    @Benchmark
    public void testMatrixMultiplicationEjml() {
        new SimpleMatrix(firstMatrix).mult(new SimpleMatrix(secondMatrix));
    }

    @Benchmark
    public void testMatrixMultiplicationNd4J() {
        try (INDArray indArray1 = Nd4j.create(firstMatrix); INDArray indArray2 = Nd4j.create(secondMatrix)) {
            indArray1.mmul(indArray2).toDoubleMatrix();
        }
    }

    @Benchmark
    public void testMatrixMultiplicationOjAlgo() {
        RawStore.wrap(firstMatrix).multiply(RawStore.wrap(secondMatrix));
    }

    @Benchmark
    public void testMatrixMultiplicationOjAlgoParallel() {
        R064Store.FACTORY.copy(RawStore.wrap(firstMatrix)).multiply(R064Store.FACTORY.copy(RawStore.wrap(secondMatrix)));
    }
}
