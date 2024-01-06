package examen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Pregunta2 {
    public static void main(String[] args) {
        int[] A = {4, 8, 12};
        int[] P = {16, 30, 45};

        System.out.println(minCosto(15, A, P));  // Salida esperada: 60
        System.out.println(minCosto(9, A, P));    // Salida esperada: 45
    }

    public static int minCosto ( int n, int [] A, int [] P) {
        int [][] memo = new int [2][n *2+1];
        Arrays.fill (memo[0] , Integer . MAX_VALUE );
        Arrays.fill (memo[1] , Integer . MAX_VALUE );
        memo [0][0] = 0;
        memo [1][0] = 0;
        int costo= Integer.MIN_VALUE;
        int peso=Integer.MAX_VALUE;
        for (int i = 1; i <= n*2; i++) {
            for (int j = 0; j < A. length ; j++) {
                if (i >= A[j] && memo [0][i - A[j]] != Integer . MAX_VALUE ) {
                    memo [0][i] = Math . min( memo [0][i] , memo [0][i - A[j]] + P[j]);
                    memo [1][i] = memo[1][i - A[j]] + A[j];
                    if(costo>=0){
                        if(costo!=memo[1][i]){return peso;}
                        peso= Math.min(peso, memo [0][i]);
                    }
                    if(memo[1][i]>=n&&costo==Integer.MIN_VALUE){
                        costo=memo[1][i];
                        peso=memo [0][i];
                    }
                }
            }
        }
        return memo [0][n*2];
    }
}
