package ExamenExtra1;

import java.util.Arrays;

public class Pregunta3E1 {

    public static long tribonacci(int n) {
        if (n <= 2) {
            return n;
        }

        // Definir la matriz de transformación
        long[][] M = {{1, 1, 1}, {1, 0, 0}, {0, 1, 0}};

        // Inicializar el vector columna F2
        long[][] F2 = {{2}, {1}, {0}};

        // Elevar la matriz M a la potencia (n-2)
        long[][] matrizResultado = potenciaMatriz(M, n - 2);

        // Multiplicar la matriz resultante por el vector F2
        long[][] Fn = multiplicarMatrices(matrizResultado, F2);

        // El resultado está en la primera entrada de Fn
        return Fn[0][0];
    }

    // Función para elevar una matriz a una potencia
    private static long[][] potenciaMatriz(long[][] matriz, int exp) {
        if (exp == 0) {
            return matrizIdentidad(matriz.length);
        }

        if (exp % 2 == 0) {
            long[][] mitadPotencia = potenciaMatriz(matriz, exp / 2);
            return multiplicarMatrices(mitadPotencia, mitadPotencia);
        } else {
            return multiplicarMatrices(matriz, potenciaMatriz(matriz, exp - 1));
        }
    }

    // Función para multiplicar dos matrices
    private static long[][] multiplicarMatrices(long[][] A, long[][] B) {
        int m = A.length;
        int n = B[0].length;
        int p = B.length;
        long[][] resultado = new long[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < p; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return resultado;
    }

    // Función para obtener la matriz identidad
    private static long[][] matrizIdentidad(int size) {
        long[][] identidad = new long[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(identidad[i], 0);
            identidad[i][i] = 1;
        }
        return identidad;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            long resultado = tribonacci(i);
            System.out.println("tribonacci: "+ i +  " = " + resultado);
        }

    }
}
