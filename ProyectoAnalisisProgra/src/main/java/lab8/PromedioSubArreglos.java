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

            scanner.nextLine();

            // Leer el arreglo binario como una cadena
            String numBinario = scanner.nextLine();

            // Convertir la cadena binaria en un ArrayList
            ArrayList<Integer> listaBinaria = convertirBinarioAArrayList(numBinario);

            // Calcular promedio de sublistas
            System.out.println(calcularPromedioSublistas(listaBinaria, k));
        }
    }

    // Función para convertir una cadena binaria en un ArrayList de enteros
    private static ArrayList<Integer> convertirBinarioAArrayList(String binario) {
        ArrayList<Integer> listaBinaria = new ArrayList<>();
        String[] elementos = binario.split(" ");
        for (String elemento : elementos) {
            int valor = Integer.parseInt(elemento);
            listaBinaria.add(valor);
        }

        return listaBinaria;
    }

    private static int calcularPromedioSublistas(ArrayList<Integer> lista, int k) {
        int n = lista.size();
        int cambiosRealizados = 0;
        for (int i = 0; i <= n - k; i++) {
            ArrayList<Integer> sublista = new ArrayList<>(lista.subList(i, i + k));
            int unos = 0;
            for (int valor : sublista) {
                unos += valor;
            }
            double promedio = (double) unos / k;
            if (promedio == 1) {
                sublista.set(k - 1, 0);
                cambiosRealizados++;
            }
        }
        return cambiosRealizados;
    }
}
