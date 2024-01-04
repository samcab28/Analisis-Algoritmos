package examen;

/*
estudiante: Samir Cabrera

complejidad:  O(n log n)
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Pregunta3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> precios = new ArrayList<>();
        precios.add(10);
        precios.add(20);
        precios.add(30);

        ArrayList<Integer> combos = new ArrayList<>();
        combos.add(2);
        combos.add(3);
        combos.add(5);
        combos.add(6);

        int G = 50;

        sumaInteres(precios.size(), precios, combos.size(), combos, G);
    }


    private static void sumaInteres(int N, ArrayList<Integer> precios, int C, ArrayList<Integer> combos, int G) {
        ArrayList<Integer> combinaciones = generarCombinaciones(precios, combos);

        Collections.sort(combinaciones);

        int resultado = binarySearch(combinaciones, G);

        if (resultado != -1) {
            int result = calculoSuma(combinaciones, resultado);
            System.out.println("resultado: " + result);
        } else {
            System.out.println("No hay combinación que sea mayor o igual a " + G);
        }
    }

    private static ArrayList<Integer> generarCombinaciones(ArrayList<Integer> precios, ArrayList<Integer> combos) {
        ArrayList<Integer> combinaciones = new ArrayList<>();

        for (int precio : precios) {
            for (int combo : combos) {
                combinaciones.add(precio * combo);
            }
        }

        return combinaciones;
    }

    private static int calculoSuma(ArrayList<Integer> combinaciones, int target) {
        int sumaAcumulada = 0;
        for (int combinacion : combinaciones) {
            if (combinacion >= target) {
                sumaAcumulada += combinacion;
            }
        }
        return sumaAcumulada;
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
