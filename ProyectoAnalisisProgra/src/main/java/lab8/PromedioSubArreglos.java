package lab8;

import java.util.ArrayList;
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

            promedioSub(numBinario, k);
        }
    }

    public static void promedioSub(String numBinario, int k) {
        int respuesta = 0;

        if (k > numBinario.length()) {
            System.out.println("Error: El valor de referencia es mayor que el tamaño del arreglo binario.");
            return;
        }

        System.out.println(numBinario);

        
    }
}
