package ExamenExtra2;

import java.util.Random;

public class Pregunta6E2 {
    public static boolean inverso(double[][] A, double[][] B, double epsilon, int numSamples) {
        int n = A.length;
        double errorSum = 0;
        Random random = new Random();
        for (int i = 0; i < numSamples; i++) {
            double[] x = vectorRandom(n, random);
            double[] y = vectorRandom(n, random);
            double[] Bx = multiMatriz(B, x);
            double[] Ay = multiMatriz(A, y);
            double normError = calcularError(Bx, Ay);
            errorSum += normError;
        }
        double averageError = errorSum / numSamples;

        return averageError < epsilon;
    }
    private static double[] vectorRandom(int n, Random random) {
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = random.nextDouble();
        }
        return vector;
    }
    private static double[] multiMatriz(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }
    private static double calcularError(double[] vector1, double[] vector2) {
        int n = vector1.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(sum);
    }


    public static void main(String[] args) {
        double[][] A = {{3, 5}};
        double[][] B = {{5, 3}};
        double epsilon = 5;
        int numSamples = 100;
        boolean result = inverso(A, B, epsilon, numSamples);
        if (result) {
            System.out.println("es inversa");
        } else {
            System.out.println("NO es inversa");
        }
    }
}
