package benchmark;

import java.util.Random;
import java.util.regex.Pattern;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Group)
public abstract class DoubleArrayInstantiationAbstract {
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
          "4000x4000;4000x4000", // Matrix 1: 16000000 elements, Matrix 2: 16000000 elements, Compute Intensity: 64,000,000,000
          "9441x9441;9441x9441", // Matrix 1: 89132481 elements, Matrix 2: 89132481 elements, Compute Intensity: 841,499,753,121
    })
    private String matrixDimensions;
    double[][] firstArray;
    double[][] secondArray;

    @Setup
    public void setup() {
        String[] dimensions = SEMICOLON.split(matrixDimensions);
        String[] firstMatrixDimensions = X.split(dimensions[0]);
        String[] secondMatrixDimensions = X.split(dimensions[1]);
        double[][] firstMatrix = new double[Integer.parseInt(firstMatrixDimensions[0])][Integer.parseInt(firstMatrixDimensions[1])];
        double[][] secondMatrix = new double[Integer.parseInt(secondMatrixDimensions[0])][Integer.parseInt(secondMatrixDimensions[1])];
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
        this.firstArray = firstMatrix;
        this.secondArray = secondMatrix;
    }
}
