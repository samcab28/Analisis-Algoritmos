package examen2;

/*
estudiante: Samir Cabrera
 */


import java.util.*;

class Grafo {
    private int vertices;

    //uso de LinkedList para favorecer la manipulacion del grafo
    private LinkedList<Integer>[] grafo;

    Grafo(int v) {
        vertices = v;
        grafo = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            grafo[i] = new LinkedList<>();
    }

    void agregarArista(int v, int w) {
        grafo[v].add(w);
        grafo[w].add(v);
    }

    void colorearGrafo() {
        //variable de grados necesaria para tener en cuenta los vertices
        int[] grados = new int[vertices];
        for (int i = 0; i < vertices; i++)
            grados[i] = grafo[i].size();
        List<Integer> ordenGrados = new ArrayList<>();
        for (int i = 0; i < vertices; i++)
            ordenGrados.add(i);
        ordenGrados.sort(Comparator.comparingInt(i -> grados[i]));
        Collections.reverse(ordenGrados);
        int[] colores = new int[vertices];
        Arrays.fill(colores, -1);
        for (int ordenGrado : ordenGrados) {
            //uso de cuatro colores por la teoria de cuatro colores
            Set<Integer> coloresDisponibles = new HashSet<>(Arrays.asList(1, 2, 3, 4));
            for (int vecino : grafo[ordenGrado]) {
                if (colores[vecino] != -1) {
                    coloresDisponibles.remove(colores[vecino]);
                }
            }
            colores[ordenGrado] = coloresDisponibles.iterator().next();
        }
        for (int i =0; i < vertices; i++) {
            System.out.println("vertice: " + i + " coloreado con color: " + colores[i]);
        }
    }
}

public class Pregunta1 {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(5);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);
        grafo.agregarArista(3,4);


        grafo.colorearGrafo();
    }
}
