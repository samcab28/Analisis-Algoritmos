package general;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int V = scanner.nextInt(); // cantidad de nodos
        int E = scanner.nextInt(); // cantidad de lineas

        // Crear una matriz de adyacencia para representar el grafo
        int[][] grafo = construirGrafo(scanner, E, V);

        // Imprimir la representación del grafo
        imprimirGrafo(grafo);

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
