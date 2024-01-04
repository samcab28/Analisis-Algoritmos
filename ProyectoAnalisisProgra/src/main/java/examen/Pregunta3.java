package examen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Pregunta3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = 3;
        ArrayList<Integer> precios = new ArrayList<>();
        precios.add(10);
        precios.add(20);
        precios.add(30);

        int C = 4;
        ArrayList<Integer> combos = new ArrayList<>();
        combos.add(2);
        combos.add(3);
        combos.add(5);
        combos.add(6);

        int G = 70;

        // Calcular todas las combinaciones de precios por productos
        ArrayList<Integer> combinaciones = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < C; j++) {
                combinaciones.add(precios.get(i) * combos.get(j));
            }
        }

        // Ordenar las combinaciones en orden ascendente
        Collections.sort(combinaciones);

        // Busqueda binaria para encontrar la combinación más pequeña mayor o igual a G
        int resultado = binarySearch(combinaciones, G);

        if (resultado != -1) {
            System.out.println("La menor combinación es: " + resultado);
        } else {
            System.out.println("No hay combinación que sea mayor o igual a " + G);
        }
    }

    private static int binarySearch(ArrayList<Integer> combinaciones, int target) {
        int low = 0;
        int high = combinaciones.size() - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (combinaciones.get(mid) >= target) {
                result = combinaciones.get(mid);
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }
}
