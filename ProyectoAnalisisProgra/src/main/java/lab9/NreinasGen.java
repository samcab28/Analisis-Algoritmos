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

        System.out.println("Población inicial y su fitness:");
        for (ArrayList<Integer> posicionReina : savePoblacionInicial) {
            System.out.println("Posición de las reinas: " + posicionReina);
            System.out.println("Fitness: " + calcularFitness(posicionReina));
        }

        System.out.println("\n\n\nimprimir las reinas de en orden de fitness");

        // Ordenar la población por fitness
        Collections.sort(savePoblacionInicial, Collections.reverseOrder(new IndividuoComparator()));

        System.out.println("Población ordenada por fitness:");
        for (ArrayList<Integer> posicionReina : savePoblacionInicial) {
            System.out.println("Posición de las reinas: " + posicionReina);
            System.out.println("Fitness: " + calcularFitness(posicionReina));
        }

    }

    static class IndividuoComparator implements java.util.Comparator<ArrayList<Integer>> {
        @Override
        public int compare(ArrayList<Integer> individuo1, ArrayList<Integer> individuo2) {
            return Double.compare(calcularFitness(individuo2), calcularFitness(individuo1));
        }
    }


    //funcion de calculo del fitness
    public static double calcularFitness(ArrayList<Integer> posicionReina) {
        int conflicts = contarConflictos(posicionReina);
        return 1.0 / (1 + conflicts);
    }

    // Función para contar los conflictos en una posición de reinas
    private static int contarConflictos(ArrayList<Integer> posicionReina) {
        int conflicts = 0;
        int size = posicionReina.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // Misma fila, misma columna o misma diagonal
                if (posicionReina.get(i).equals(posicionReina.get(j)) || Math.abs(posicionReina.get(i) - posicionReina.get(j)) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
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
        }

        return savePoblacionInicial;
    }
}
