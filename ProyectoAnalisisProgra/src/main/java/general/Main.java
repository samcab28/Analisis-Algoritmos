package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.stream.Collectors;


public class Main {
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

        // Inicializar memo con valores "infinitos"
        for (int i = 0; i < distanciaMatriz; i++) {
            Arrays.fill(memo[i], Double.POSITIVE_INFINITY);
        }

        for (int end = 0; end < distanciaMatriz; end++) {
            if (end == nodoInicio || grafo[nodoInicio][end] == 0) continue;
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

                        // Ajustar para manejar conexiones faltantes y distancias cero
                        if (grafo[end][next] > 0) {
                            double newDistance = memo[end][subsetWithoutNext] + grafo[end][next];
                            if (newDistance < minDist) {
                                minDist = newDistance;
                            }
                        }
                    }
                    memo[next][subset] = minDist;
                }
            }
        }

        double minTourCost = Double.POSITIVE_INFINITY;
        int bestSubset = 0;

        // Encontrar la mejor solución entre todas las combinaciones
        for (int i = 0; i < distanciaMatriz; i++) {
            if (i == nodoInicio || grafo[i][nodoInicio] == 0) continue;
            double tourCost = memo[i][END_STATE] + grafo[i][nodoInicio];
            if (tourCost < minTourCost) {
                minTourCost = tourCost;
                bestSubset = END_STATE;
            }
        }

        if (minTourCost == Double.POSITIVE_INFINITY) {
            System.out.println("No hay recorrido válido en el grafo incompleto.");
            return;
        }


        // Dar la ruta desde la memo
        int lastIndex = nodoInicio;
        int state = bestSubset;

        List<Integer> tour = new ArrayList<>();
        tour.add(nodoInicio + 1);  // Ajuste para iniciar desde 1

        for (int i = 1; i < distanciaMatriz; i++) {
            int bestIndex = -1;
            double bestDist = Double.POSITIVE_INFINITY;

            for (int j = 0; j < distanciaMatriz; j++) {
                if (j == nodoInicio || notIn(j, state)) continue;

                // Ajustar para manejar distancias cero
                if (grafo[j][lastIndex] > 0) {
                    double newDist = memo[j][state] + grafo[j][lastIndex];
                    if (newDist < bestDist) {
                        bestIndex = j;
                        bestDist = newDist;
                    }
                }
            }

            if (bestIndex == -1) {
                // No se encontró una conexión válida, el grafo es incompleto
                System.out.println("El grafo es incompleto, no se puede completar un recorrido válido.");
                return;
            }

            tour.add(bestIndex + 1);  // Ajuste para iniciar desde 1
            state = state ^ (1 << bestIndex);
            lastIndex = bestIndex;
        }

        tour.add(nodoInicio + 1);  // Ajuste para iniciar desde 1

        System.out.println("Dinamica: " + tour + " Costo: " + minTourCost);
    }

    private static List<Integer> combinations(int r, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, r, n, subsets);
        return subsets;
    }

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

    private static boolean notIn(int elem, int subset) {
        return ((1 << elem) & subset) == 0;
    }





    public static void geneticos(int[][] grafo) {
        int poblacionSize = 50,generations = 1000, mejorFit = Integer.MAX_VALUE, costo=0;
        ArrayList<ArrayList<Integer>> poblacion = generarPoblacionInicial(poblacionSize, grafo.length);
        ArrayList<Integer> mejorRuta = null;

        for (int generacion = 0; generacion < generations; generacion++) {
            ArrayList<ArrayList<Integer>> nuevaPoblacion = new ArrayList<>();

            for (ArrayList<Integer> ruta : poblacion) {
                int crossoverPoint = new Random().nextInt(grafo.length-1);
                ArrayList<Integer> hijo = crossover(ruta, poblacion.get(new Random().nextInt(poblacionSize)), crossoverPoint);
                if (Math.random() < 0.2) {
                    mutacion(hijo);
                }
                nuevaPoblacion.add(hijo);
            }

            poblacion = nuevaPoblacion;

            for (ArrayList<Integer> ruta : poblacion) {
                int fit = fitness(grafo, ruta), ncosto = calcularCostoRuta(grafo, ruta);
                if (fit < mejorFit||(fit == 0&&ncosto<=costo)) {
                    mejorFit=fit;
                    costo = calcularCostoRuta(grafo, ruta);
                    mejorRuta = new ArrayList<>(ruta);
                }
            }
        }

        System.out.print("Genetico: ");
        if(mejorFit==0&&mejorRuta!=null){
            for(int i:mejorRuta){ //impresion
                System.out.print((i+1)+",");
            }
            System.out.println("1 Costo: "+costo);
        }else{System.out.println("--- Costo: "+costo);}
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

    private static int fitness(int[][] grafo, ArrayList<Integer> ruta) {
        int fallas=0;
        if(ruta.get(0)!=0){fallas++;}
        long repetidos= ruta.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).values().stream().filter(count -> count > 1).mapToLong(count -> count - 1).sum();
        if(repetidos>0){fallas++;}
        for (int i = 0; i < ruta.size() - 1; i++) {
            if(grafo[ruta.get(i)][ruta.get(i + 1)]==0){
                fallas++;
            }
        }
        return fallas;
    }
    private static int calcularCostoRuta(int[][] grafo, ArrayList<Integer> ruta) {
        int costo = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            costo += grafo[ruta.get(i)][ruta.get(i + 1)];
        }
        costo += grafo[ruta.get(ruta.size() - 1)][ruta.get(0)]; // Regreso al punto inicial
        return costo;
    }






















    private static void backTracking(int[][] grafo) {
        ArrayList<Boolean> visitados = new ArrayList<>();
        for (int i = 0; i < grafo.length; i++) {
            visitados.add(false);
        }

        rutaBT = new ArrayList<>();
        movContBT = Integer.MAX_VALUE;
        backTracking_aux(grafo, 0, 0, new ArrayList<>(), visitados);
        
        System.out.print("Backtracking: ");
        if (movContBT != Integer.MAX_VALUE && !rutaBT.isEmpty()) {
            for (int i : rutaBT) {
                System.out.print((i + 1) + ",");
            }
            System.out.println("1 Costo: " + movContBT);
        } else {
            System.out.println("--- Costo: " + movContBT);
        }
    }

    private static void backTracking_aux(int[][] grafo, int vertice, int movCont, ArrayList<Integer> ruta, ArrayList<Boolean> visitados) {
        if (visitados.get(0) && vertice == 0) {
            boolean todosTrue = true;
            for (boolean valor : visitados) {
                if (!valor) {
                    todosTrue = false;
                    break;
                }
            }
            if (todosTrue) {
                if (movCont < movContBT) {
                    rutaBT = new ArrayList<>(ruta);
                    movContBT = movCont;
                    return;
                }
            }
        }
        if (visitados.get(vertice)) {return;}
        ruta.add(vertice);
        visitados.set(vertice, true);
        for (int i = 0; i < grafo.length; i++) {
            if (grafo[vertice][i] != 0) {
                backTracking_aux(grafo, i, movCont + grafo[vertice][i], new ArrayList<>(ruta), new ArrayList<>(visitados));
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

        // Verificar si el grafo es completo
        if (esGrafoCompleto(grafo)) {
            System.out.println("El grafo es completo.");
            imprimirGrafo(grafo);
        } else {
            imprimirGrafo(grafo);
            /*
            System.out.println("El grafo no es completo.");
            System.out.println("grafo original");
            imprimirGrafo(grafo);

            System.out.println("\n\ngrafo modificado");
            grafo = completarGrafoIncompleto(grafo);
            imprimirGrafo(grafo);*/

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

    // Función para verificar si el grafo es completo
    private static boolean esGrafoCompleto(int[][] grafo) {
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                if (i != j && grafo[i][j] == 0) {
                    return false; // Si hay al menos una conexión faltante, el grafo no es completo
                }
            }
        }
        return true;
    }

    // Función para completar el grafo si no es completo
    private static int[][] completarGrafoIncompleto(int[][] grafo) {
        int V = grafo.length;

        // Verificar si el grafo es completo
        if (!esGrafoCompleto(grafo)) {
            // Completar las conexiones faltantes con las distancias disponibles
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i != j && grafo[i][j] == 0) {
                        // Utilizar la distancia ya almacenada entre i y j en alguna dirección
                        if (grafo[j][i] != 0) {
                            grafo[i][j] = grafo[j][i];
                        } else {
                            // Asignar un valor predeterminado o utilizar la distancia óptima si es posible
                            grafo[i][j] = encontrarDistanciaMinima(grafo, i, j);
                        }
                    }
                }
            }
        }

        return grafo;
    }



    // Función para encontrar la distancia mínima entre dos nodos en el grafo
    private static int encontrarDistanciaMinima(int[][] grafo, int nodoOrigen, int nodoDestino) {
        int minDistancia = Integer.MAX_VALUE;

        for (int k = 0; k < grafo.length; k++) {
            if (grafo[nodoOrigen][k] != 0 && grafo[k][nodoDestino] != 0) {
                int distanciaTotal = grafo[nodoOrigen][k] + grafo[k][nodoDestino];
                if (distanciaTotal < minDistancia) {
                    minDistancia = distanciaTotal;
                }
            }
        }

        return minDistancia;
    }



}
