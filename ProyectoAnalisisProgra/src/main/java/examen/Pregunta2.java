package examen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Pregunta2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> A = new ArrayList<>(); //conjunto de paquetes
        A.add(4);
        A.add(8);
        A.add(12);

        ArrayList<Integer> P = new ArrayList<>(); //precios
        P.add(16);
        P.add(30);
        P.add(45);

        int n = scanner.nextInt();

        int resultado = minCosto(n, A, P);
        System.out.println(resultado);
    }

    public static int minCosto(int n, ArrayList<Integer> paquetes, ArrayList<Integer> precios) {
        int m = paquetes.size();
        int[] dp = new int[n + 1];
        int[] paquetesUtilizados = new int[n + 1];

        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;

        for (int i = 0; i < m; i++) {
            for (int j = paquetes.get(i); j <= n; j++) {
                if (dp[j - paquetes.get(i)] + 1 < dp[j]) {
                    dp[j] = dp[j - paquetes.get(i)] + 1;
                    paquetesUtilizados[j] = i;
                }
            }
        }

        // Encontrar la configuración más cercana a la cantidad deseada
        int cantidad = n;
        while (dp[cantidad] == Integer.MAX_VALUE - 1) {
            cantidad--;
        }

        // Reconstruir la solución para obtener la configuración más cercana
        ArrayList<Integer> paquetesSeleccionados = new ArrayList<>();
        while (cantidad > 0) {
            int indicePaquete = paquetesUtilizados[cantidad];
            paquetesSeleccionados.add(paquetes.get(indicePaquete));
            cantidad -= paquetes.get(indicePaquete);
        }

        System.out.println("Paquetes seleccionados: " + paquetesSeleccionados);
        return dp[n];
    }
}
