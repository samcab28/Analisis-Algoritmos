package general;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;


public class Main {
    public static boolean[] visitados;
    public static ArrayList<Integer> rutaBT;
    public static int movContBT;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int V = scanner.nextInt(); // cantidad de nodos
        int E = scanner.nextInt(); // cantidad de lineas
        // Crear una matriz de adyacencia para representar el grafo
        int[][] grafo = construirGrafo(scanner, E, V);

        imprimirGrafo(grafo);
        dinamica(grafo);
        geneticos(grafo);
        backTracking(grafo);

    }



    public static void dinamica(int[][] grafo) {
        int nodoInicio = 0;
        int distanciaMatriz = grafo.length;

        int END_STATE = (1 << distanciaMatriz) - 1;
        Double[][] memo = new Double[distanciaMatriz][1 << distanciaMatriz];

        for (int end = 0; end < distanciaMatriz; end++) {
            if (end == nodoInicio) continue;
            memo[end][(1 << nodoInicio) | (1 << end)] = (double) grafo[nodoInicio][end];
        }

        for (int r = 3; r <= distanciaMatriz; r++) {
            for (int subset : combinations(r, distanciaMatriz)) {
                if (notIn(nodoInicio, subset)) continue;
                for (int next = 0; next < distanciaMatriz; next++) {
                    if (next == nodoInicio || notIn(next, subset)) continue;
                    int subsetWithoutNext = subset ^ (1 << next);
                    double minDist = Double.POSITIVE_INFINITY;
                    for (int end = 0; end < distanciaMatriz; end++) {
                        if (end == nodoInicio || end == next || notIn(end, subset)) continue;
                        double newDistance = memo[end][subsetWithoutNext] + grafo[end][next];
                        if (newDistance < minDist) {
                            minDist = newDistance;
                        }
                    }
                    memo[next][subset] = minDist;
                }
            }
        }

        double minTourCost = Double.POSITIVE_INFINITY;
        for (int i = 0; i < distanciaMatriz; i++) {
            if (i == nodoInicio) continue;
            double tourCost = memo[i][END_STATE] + grafo[i][nodoInicio];
            if (tourCost < minTourCost) {
                minTourCost = tourCost;
            }
        }

        System.out.println("Tour (Dynamic Programming) Costo: " + minTourCost);

        // dar la ruta desde la memo
        int lastIndex = nodoInicio;
        int state = END_STATE;

        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(nodoInicio + 1);  // Ajuste para iniciar desde 1

        for (int i = 1; i < distanciaMatriz; i++) {
            int bestIndex = -1;
            double bestDist = Double.POSITIVE_INFINITY;

            for (int j = 0; j < distanciaMatriz; j++) {
                if (j == nodoInicio || notIn(j, state)) continue;
                double newDist = memo[j][state] + grafo[j][lastIndex];
                if (newDist < bestDist) {
                    bestIndex = j;
                    bestDist = newDist;
                }
            }

            tour.add(bestIndex + 1);  // Ajuste para iniciar desde 1
            state = state ^ (1 << bestIndex);
            lastIndex = bestIndex;
        }

        tour.add(nodoInicio + 1);  // Ajuste para iniciar desde 1

        System.out.println("Tour (Dynamic Programming): " + tour);
    }




    //almacenamiento combinaciones
    private static List<Integer> combinations(int r, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, r, n, subsets);
        return subsets;
    }

    //posibles combinaciones restantes
    private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {
        int elementsLeftToPick = n - at;
        if (elementsLeftToPick < r) return;

        if (r == 0) {
            subsets.add(set);
        } else {
            for (int i = at; i < n; i++) {
                set ^= (1 << i);
                combinations(set, i + 1, r - 1, n, subsets);
                set ^= (1 << i);
            }
        }
    }

    //funcion de comprobacion
    private static boolean notIn(int elem, int subset) {
        return ((1 << elem) & subset) == 0;
    }





    public static void geneticos(int[][] grafo) {
        int poblacionSize = 50;
        int generations = 1000;
        ArrayList<ArrayList<Integer>> poblacion = generarPoblacionInicial(poblacionSize, grafo.length);
        ArrayList<Integer> mejorRuta = null;
        int mejorCosto = Integer.MAX_VALUE;

        for (int generacion = 0; generacion < generations; generacion++) {
            ArrayList<ArrayList<Integer>> nuevaPoblacion = new ArrayList<>();

            for (ArrayList<Integer> ruta : poblacion) {
                int crossoverPoint = grafo.length / 2;
                ArrayList<Integer> hijo = crossover(ruta, poblacion.get(new Random().nextInt(poblacionSize)), crossoverPoint);
                if (Math.random() < 0.2) {
                    mutacion(hijo);
                }
                nuevaPoblacion.add(hijo);
            }

            poblacion = nuevaPoblacion;

            for (ArrayList<Integer> ruta : poblacion) {
                int costo = calcularCostoRuta(grafo, ruta);
                if (costo < mejorCosto) {
                    mejorCosto = costo;
                    mejorRuta = new ArrayList<>(ruta);
                }
            }
        }

        System.out.println("Tour (Genetic Algorithm): " + mejorRuta);
        System.out.println("Tour (Genetic Algorithm) Costo: " + mejorCosto);
    }

    private static ArrayList<ArrayList<Integer>> generarPoblacionInicial(int size, int distanciaMatriz) {
        ArrayList<ArrayList<Integer>> poblacion = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> ruta = new ArrayList<>();
            for (int j = 0; j < distanciaMatriz; j++) {
                ruta.add(j);
            }
            Collections.shuffle(ruta);
            poblacion.add(ruta);
        }
        return poblacion;
    }

    private static ArrayList<Integer> crossover(ArrayList<Integer> padre1, ArrayList<Integer> padre2, int crossoverPoint) {
        ArrayList<Integer> hijo = new ArrayList<>(padre1.subList(0, crossoverPoint));
        for (int gen : padre2) {
            if (!hijo.contains(gen)) {
                hijo.add(gen);
            }
        }
        return hijo;
    }

    private static void mutacion(ArrayList<Integer> ruta) {
        int index1 = new Random().nextInt(ruta.size());
        int index2 = new Random().nextInt(ruta.size());
        Collections.swap(ruta, index1, index2);
    }

    private static int calcularCostoRuta(int[][] grafo, ArrayList<Integer> ruta) {
        int costo = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            costo += grafo[ruta.get(i)][ruta.get(i + 1)];
        }
        costo += grafo[ruta.get(ruta.size() - 1)][ruta.get(0)]; // Regreso al punto inicial
        return costo;
    }






















    private static void backTracking(int[][] grafo){
        visitados= new boolean[grafo.length]; //Inicializo los atributos de info
        rutaBT= new ArrayList<>();
        backTracking_aux(grafo,0,0,new ArrayList<>()); //Generacion de ruta
        System.out.print("Backtracking: ");
        for(int i:rutaBT){ //impresion
            System.out.print((i+1)+",");
        }
        System.out.println("1 Costo: "+movContBT);
    }
    private static void backTracking_aux(int[][] grafo, int vertice, int movCont, ArrayList<Integer>ruta){
        if(visitados[0]&&vertice==0){
            rutaBT= ruta;
            movContBT= movCont;
            return;
        }
        if(visitados[vertice]){
            return;
        }
        ruta.add(vertice);
        visitados[vertice]= true; //Marcar como visitado
        for(int i=0;i<grafo[vertice].length;i++){
            if(grafo[vertice][i]!=0){
                backTracking_aux(grafo,i,movCont+grafo[vertice][i],ruta); //Expansion en todos los vecinos
            }
        }
    }




    // Función para construir la matriz de adyacencia del grafo
    private static int[][] construirGrafo(Scanner scanner, int E, int V) {
        int[][] grafo = new int[V][V];

        for (int i = 0; i < E; i++) {
            int nodoOrigen = scanner.nextInt();
            int nodoDestino = scanner.nextInt();
            int costoViaje = scanner.nextInt();
            // Agregar la conexión al grafo
            grafo[nodoOrigen - 1][nodoDestino - 1] = costoViaje;
            grafo[nodoDestino - 1][nodoOrigen - 1] = costoViaje; // Considerando un grafo no dirigido
        }

        return grafo;
    }

    // Función para imprimir la representación del grafo
    private static void imprimirGrafo(int[][] grafo) {
        System.out.println("Representación del grafo:");

        for (int i = 0; i < grafo.length; i++) {
            System.out.print((i + 1) + ": ");
            for (int j = 0; j < grafo[i].length; j++) {
                System.out.print(grafo[i][j] + " ");
            }
            System.out.println();
        }
    }



}