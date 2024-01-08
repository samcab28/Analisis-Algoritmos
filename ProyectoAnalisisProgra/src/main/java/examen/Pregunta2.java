package examen;

/*
estudiante: Samir Cabrera
complejidad: O(n * |A|)
 */

import java.util.ArrayList;
import java.util.Collections;

public class Pregunta2 {

    public static void main(String[] args) {

        ArrayList<Integer> pesos = new ArrayList<>();
        pesos.add(4);
        pesos.add(8);
        pesos.add(12);

        ArrayList<Integer> costos = new ArrayList<>();
        costos.add(16);
        costos.add(30);
        costos.add(45);

        System.out.println(minCosto(15, pesos, costos));
        System.out.println(minCosto(9, pesos, costos));
    }

    public static int minCosto(int num, ArrayList<Integer> pesos, ArrayList<Integer> costos) {
        int n = pesos.size();
        ArrayList<ArrayList<Integer>> memo = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            memo.add(new ArrayList<>(Collections.nCopies(num * 2 + 1, Integer.MAX_VALUE)));
        }

        memo.get(0).set(0, 0);
        memo.get(1).set(0, 0);

        int costo = Integer.MIN_VALUE;
        int peso = Integer.MAX_VALUE;

        for (int i = 1; i <= num * 2; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= pesos.get(j) && memo.get(0).get(i - pesos.get(j)) != Integer.MAX_VALUE) {
                    memo.get(0).set(i, Math.min(memo.get(0).get(i), memo.get(0).get(i - pesos.get(j)) + costos.get(j)));
                    memo.get(1).set(i, memo.get(1).get(i - pesos.get(j)) + pesos.get(j));

                    if (costo >= 0) {
                        if (costo != memo.get(1).get(i)) {
                            return peso;
                        }
                        peso = Math.min(peso, memo.get(0).get(i));
                    }

                    if (memo.get(1).get(i) >= num && costo == Integer.MIN_VALUE) {
                        costo = memo.get(1).get(i);
                        peso = memo.get(0).get(i);
                    }
                }
            }
        }
        return memo.get(0).get(num * 2);
    }
}
