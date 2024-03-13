package benchmark;

import java.util.Random;
import java.util.regex.Pattern;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.ejml.simple.SimpleMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/*
        To run this, execute the following command:
        ./mvnw jmh:benchmark -Djmh.f=1 -Djmh.wi=1 -Djmh.i=3 -Djmh.bm=avgt
        Benchmark                                               (matrixDimensions)  Mode  Cnt    Score    Error  Units
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              1x1;1x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              1x5;5x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml              2x2;2x2  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml          1x155;155x1  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml           1x18;18x18  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml            155x2;2x2  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml          3x155;155x3  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml           1x2;2x1000  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml           1x3;3x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml        1x155;155x155  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml         1x28;28x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml        3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml          30x50;50x50  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml        28x155;155x28  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml        30x155;155x30  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml        7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml       16x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml     9441x9441;9441x1  avgt    3    0.182 ±  0.034   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml     9441x155;155x155  avgt    3    0.027 ±  0.016   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml    9441x100;100x9441  avgt    3    0.994 ± 2.099    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml    120x9441;9441x120  avgt    3    0.019 ± 0.009    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml    9441x120;120x9441  avgt    3    1.261 ± 0.115    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml    8001x180;180x8001  avgt    3    1.364 ± 0.044    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  2239x2289;2289x2339  avgt    3    1.702 ± 2.405    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml    9441x155;155x9441  avgt    3    1.741 ±  0.659   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml   155x9441;9441x9441  avgt    3    2.610 ±  2.113   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  3000x3000;3000x3000  avgt    3    4.095 ±  6.677   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  3300x3300;3300x3300  avgt    3    5.124 ±  0.117   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  3500x3500;3500x3500  avgt    3    6.191 ±  0.402   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  4000x4000;4000x4000  avgt    3   10.123 ±  0.639   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationEjml  9441x9441;9441x9441  avgt    3  135.833 ± 14.707   s/op

        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              1x1;1x1  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              1x5;5x1  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J              2x2;2x2  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J          1x155;155x1  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J           1x18;18x18  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J            155x2;2x2  avgt    3    0.003 ±  0.023   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J          3x155;155x3  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J           1x2;2x1000  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J           1x3;3x1000  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J        1x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J         1x28;28x1000  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J        3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J          30x50;50x50  avgt    3   ≈ 10⁻³            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J        28x155;155x28  avgt    3   ≈ 10⁻³            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J        30x155;155x30  avgt    3    0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J        7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J       16x155;155x155  avgt    3    0.001 ±  0.001   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J     9441x9441;9441x1  avgt    3    0.540 ±  0.070   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J     9441x155;155x155  avgt    3    0.218 ±  0.126   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J    9441x100;100x9441  avgt    3    1.171 ± 0.885    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J    120x9441;9441x120  avgt    3    0.022 ± 0.020    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J    9441x120;120x9441  avgt    3    1.384 ± 1.439    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J    8001x180;180x8001  avgt    3    0.910 ± 1.248    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  2239x2289;2289x2339  avgt    3    0.361 ± 0.073    s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J    9441x155;155x9441  avgt    3    1.440 ±  2.112   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J   155x9441;9441x9441  avgt    3    0.753 ±  0.166   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  3000x3000;3000x3000  avgt    3    0.669 ±  0.246   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  3300x3300;3300x3300  avgt    3    0.846 ±  0.307   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  3500x3500;3500x3500  avgt    3    0.942 ±  0.196   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  4000x4000;4000x4000  avgt    3    1.510 ±  1.066   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationNd4J  9441x9441;9441x9441  avgt    3   14.203 ± 12.498   s/op

        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                   1x1;1x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                   1x5;5x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                   2x2;2x2  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath               1x155;155x1  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                1x18;18x18  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                 155x2;2x2  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath               3x155;155x3  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                1x2;2x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath                1x3;3x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath             1x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath              1x28;28x1000  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath             3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath               30x50;50x50  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath             28x155;155x28  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath             30x155;155x30  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath             7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath            16x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath          9441x9441;9441x1  avgt    3    0.186 ±  0.043   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath          9441x155;155x155  avgt    3    0.125 ±  0.052   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       2239x2289;2289x2339  avgt    3    7.993 ±  2.103   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath         9441x155;155x9441  avgt    3   11.856 ±  3.543   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath        155x9441;9441x9441  avgt    3    9.184 ±  1.994   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       3000x3000;3000x3000  avgt    3   18.042 ±  6.372   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       3300x3300;3300x3300  avgt    3   24.793 ±  4.772   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       3500x3500;3500x3500  avgt    3   28.658 ±  6.257   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       4000x4000;4000x4000  avgt    3   43.166 ± 21.033   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMath       9441x9441;9441x9441  avgt    3  518.391 ± 29.871   s/op

        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock              1x1;1x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock              1x5;5x1  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock              2x2;2x2  avgt    3   ≈ 10⁻⁷            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock          1x155;155x1  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock           1x18;18x18  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock            155x2;2x2  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock          3x155;155x3  avgt    3   ≈ 10⁻⁶            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock           1x2;2x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock           1x3;3x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock        1x155;155x155  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock         1x28;28x1000  avgt    3   ≈ 10⁻⁵            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock        3x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock          30x50;50x50  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock        28x155;155x28  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock        30x155;155x30  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock        7x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock       16x155;155x155  avgt    3   ≈ 10⁻⁴            s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock     9441x9441;9441x1  avgt    3    0.128 ±  0.006   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock     9441x155;155x155  avgt    3    0.097 ±  0.102   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  2239x2289;2289x2339  avgt    3    4.966 ±  2.005   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock    9441x155;155x9441  avgt    3    5.579 ±  1.271   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock   155x9441;9441x9441  avgt    3    5.678 ±  2.780   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  3000x3000;3000x3000  avgt    3   11.262 ± 14.715   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  3300x3300;3300x3300  avgt    3   14.294 ±  4.802   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  3500x3500;3500x3500  avgt    3   16.994 ±  8.474   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  4000x4000;4000x4000  avgt    3   26.013 ±  0.711   s/op
        MatrixOperationBenchmark.testMatrixMultiplicationCommonsMathBlock  9441x9441;9441x9441  avgt    3  332.777 ± 40.412   s/op

    * */
@State(Scope.Benchmark)
public class MatrixOperationBenchmark {
    private final static Pattern X = Pattern.compile("x");
    private final static Pattern SEMICOLON = Pattern.compile(";");

    @Param({
          //          "1x1;1x1", // Matrix 1: 1 elements, Matrix 2: 1 elements, Compute Intensity: 1
          //          "1x5;5x1", // Matrix 1: 5 elements, Matrix 2: 5 elements, Compute Intensity: 5
          //          "2x2;2x2", // Matrix 1: 4 elements, Matrix 2: 4 elements, Compute Intensity: 8
          //          "1x155;155x1", // Matrix 1: 155 elements, Matrix 2: 155 elements, Compute Intensity: 155
          //          "1x18;18x18", // Matrix 1: 18 elements, Matrix 2: 324 elements, Compute Intensity: 324
          //          "155x2;2x2", // Matrix 1: 310 elements, Matrix 2: 4 elements, Compute Intensity: 620
          //          "3x155;155x3", // Matrix 1: 465 elements, Matrix 2: 465 elements, Compute Intensity: 1395
          //          "1x2;2x1000", // Matrix 1: 2 elements, Matrix 2: 2000 elements, Compute Intensity: 2000
          //          "1x3;3x1000", // Matrix 1: 3 elements, Matrix 2: 3000 elements, Compute Intensity: 3000
          //          "1x155;155x155", // Matrix 1: 155 elements, Matrix 2: 24025 elements, Compute Intensity: 24025
          //          "1x28;28x1000", // Matrix 1: 28 elements, Matrix 2: 28000 elements, Compute Intensity: 28000
          //          "3x155;155x155", // Matrix 1: 465 elements, Matrix 2: 24025 elements, Compute Intensity: 72075
          //          "30x50;50x50", // Matrix 1: 1500 elements, Matrix 2: 2500 elements, Compute Intensity: 75000
          //          "28x155;155x28", // Matrix 1: 4340 elements, Matrix 2: 4340 elements, Compute Intensity: 121520
          //          "30x155;155x30", // Matrix 1: 4650 elements, Matrix 2: 4650 elements, Compute Intensity: 139500
          //          "7x155;155x155", // Matrix 1: 1085 elements, Matrix 2: 24025 elements, Compute Intensity: 168175
          //          "16x155;155x155", // Matrix 1: 2480 elements, Matrix 2: 24025 elements, Compute Intensity: 384400
          //          "9441x9441;9441x1", // Matrix 1: 89132481 elements, Matrix 2: 9441 elements, Compute Intensity: 89132481
          //          "9441x155;155x155", // Matrix 1: 1463355 elements, Matrix 2: 24025 elements, Compute Intensity: 226820025
          //          "2239x2289;2289x2339", // Matrix 1: 5125071 elements, Matrix 2: 5353971 elements, Compute Intensity: 11987541069
          //          "9441x155;155x9441", // Matrix 1: 1463355 elements, Matrix 2: 1463355 elements, Compute Intensity: 13815534555
          "155x9441;9441x9441", // Matrix 1: 1463355 elements, Matrix 2: 89132481 elements, Compute Intensity: 13815534555
          "3000x3000;3000x3000", // Matrix 1: 9000000 elements, Matrix 2: 9000000 elements, Compute Intensity: 27000000000
          "3300x3300;3300x3300", // Matrix 1: 10890000 elements, Matrix 2: 10890000 elements, Compute Intensity: 35937000000
          "3500x3500;3500x3500", // Matrix 1: 10890000 elements, Matrix 2: 10890000 elements, Compute Intensity: 42875000000
          "4000x4000;4000x4000", // Matrix 1: 16000000 elements, Matrix 2: 16000000 elements, Compute Intensity: 64000000000
          "9441x9441;9441x9441", // Matrix 1: 89132481 elements, Matrix 2: 89132481 elements, Compute Intensity: 841499753121
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
    public void testMatrixMultiplicationCommonsMath() {
        new Array2DRowRealMatrix(firstMatrix).multiply(new Array2DRowRealMatrix(secondMatrix));
    }

    @Benchmark
    public void testMatrixMultiplicationCommonsMathBlock() {
        new BlockRealMatrix(firstMatrix).multiply(new BlockRealMatrix(secondMatrix));
    }
}
