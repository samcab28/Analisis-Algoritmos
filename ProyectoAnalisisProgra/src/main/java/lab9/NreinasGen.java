package lab9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
1. representacion de individuo
2. inicializacion de la poblacion
3. funcion de evaluacion o fitness
4. seleccion de individuos
5. operadores geneticos o crossover
6. reemplazo de poblacion
7. criterio de parada
 */

public class NreinasGen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("digite la cantidad de reinas");
        int nReinas = scanner.nextInt(); // cantidad de n reinas

        System.out.println("digite el tamano de la poblacion inicial");
        int poblacionInicial = scanner.nextInt();


        ArrayList<ArrayList<Integer>> savePoblacionInicial = generarPoblacionInicial(poblacionInicial,nReinas);

        System.out.println("poblacion inicial: " + savePoblacionInicial);

    }

    //funcion para crear el model de las primeras reinas
    public static ArrayList<Integer> inicializarPosicionRandom(int N) {
        ArrayList<Integer> posicionReina = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            posicionReina.add(i);
        }

        Collections.shuffle(posicionReina);

        return posicionReina;
    }

    //funcion para crear la poblacion inicial
    public static ArrayList<ArrayList<Integer>> generarPoblacionInicial(int poblacionInicial, int nReinas){
        ArrayList<ArrayList<Integer>> savePoblacionInicial = new ArrayList<>();
        for(int i = 0; i < poblacionInicial; i++){
            ArrayList<Integer> posicionReina = inicializarPosicionRandom(nReinas);
            savePoblacionInicial.add(posicionReina);
            System.out.println("Posición de las reinas: " + posicionReina);
        }

        return savePoblacionInicial;
    }
}
