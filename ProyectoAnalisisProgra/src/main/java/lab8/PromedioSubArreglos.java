package lab8;

import java.util.Scanner;

public class PromedioSubArreglos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt(); // cantidad de casos

        for (int t = 0; t < T; t++) {
            int n = scanner.nextInt(); // tamaño de arreglo binario
            int k = scanner.nextInt(); // valor referencia

            scanner.nextLine(); // Consumir el salto de línea después de leer k

            // Leer el arreglo binario como una cadena
            String numBinario = scanner.nextLine();

            int operaciones = minOperacionesParaPromedioUno(numBinario, n, k);
            System.out.println(operaciones);
        }
    }

    private static int minOperacionesParaPromedioUno(String numBinario, int n, int k) {
        int operaciones = 0;

        // Iterar sobre el arreglo binario
        for (int i = 0; i <= n - k; i++) {
            int sum = 0;

            // Calcular la suma del subarreglo de tamaño k
            for (int j = i; j < i + k; j++) {
                sum += Character.getNumericValue(numBinario.charAt(j));
            }

            // Verificar si el promedio es diferente de 1
            if ((double) sum / k != 1.0) {
                // Realizar la operación de cambiar un bit
                operaciones++;
            }
        }

        return operaciones;
    }
}
