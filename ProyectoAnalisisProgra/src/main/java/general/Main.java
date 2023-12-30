package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int V = scanner.nextInt(); // cantidad de nodos
        int E = scanner.nextInt(); // cantidad de lineas

        // Crear un mapa para representar el grafo
        Map<Integer, ArrayList<Integer[]>> grafo = new HashMap<>();

        for (int i = 0; i < E; i++) {
            int nodoOrigen = scanner.nextInt();
            int nodoDestino = scanner.nextInt();
            int costoViaje = scanner.nextInt();

            // Agregar el nodo de destino y el costo al nodo de origen
            grafo.computeIfAbsent(nodoOrigen, k -> new ArrayList<>())
                    .add(new Integer[]{nodoDestino, costoViaje});

            // Agregar el nodo de origen y el costo al nodo de destino
            grafo.computeIfAbsent(nodoDestino, k -> new ArrayList<>())
                    .add(new Integer[]{nodoOrigen, costoViaje});
        }

        // Imprimir el grafo
        System.out.println("Representación del grafo:");
        for (Map.Entry<Integer, ArrayList<Integer[]>> entry : grafo.entrySet()) {
            int nodo = entry.getKey();
            ArrayList<Integer[]> conexiones = entry.getValue();

            System.out.print(nodo + ": ");
            for (Integer[] conexion : conexiones) {
                System.out.print("(" + conexion[0] + ", " + conexion[1] + ") ");
            }
            System.out.println();
        }
    }
}
