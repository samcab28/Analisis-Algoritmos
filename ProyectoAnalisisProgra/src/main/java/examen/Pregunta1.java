package examen;

import java.util.ArrayList;
import java.util.Scanner;

public class Pregunta1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> P = new ArrayList<>();
        P.add(4);
        P.add(4);
        P.add(9);
        P.add(1);

        int V = scanner.nextInt();

        System.out.println(subsequenceSum(P, V));
    }

    public static boolean subsequenceSum(ArrayList<Integer> lista, int numero) {
        int n = lista.size();

        // Create a boolean array to store subproblem solutions
        boolean[] dp = new boolean[numero + 1];

        // Base case: Sum 0 is always possible
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            // Iterate through the array and update dp array
            for (int j = numero; j >= lista.get(i); j--) {
                dp[j] = dp[j] || dp[j - lista.get(i)];
            }
        }

        // The final result is stored in dp[numero]
        return dp[numero];
    }
}
