package examen2;

/*
estudiante: Samir Cabrera
tecnica utilizada: Backtracking
O(n^2)
 */
import java.util.LinkedList;

//le puse grafo2 porque es usar la misma estructura de la pregunta 1
class Grafo2 {
    private int vertices;
    private LinkedList<Integer>[] grafo;

    Grafo2(int v) {
        vertices = v;
        grafo = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            grafo[i] = new LinkedList<>();
    }

    void agregarArista(int v, int w) {
        grafo[v].addLast(w);
    }

    boolean esSumidero(int v) {
        boolean[] visitados = new boolean[vertices];
        visitarNodo(v, visitados);
        for (int i = 0; i < vertices; i++) {
            if (i != v && !visitados[i]) {
                return false;
            }
        }
        return true;
    }

    private void visitarNodo(int v, boolean[] visitados) {
        visitados[v] = true;
        for (int i : grafo[v]) {
            if (!visitados[i]) {
                visitarNodo(i, visitados);
            }
        }
    }
}

public class PreguntaExtra {
    public static void main(String[] args) {
        Grafo2 grafo = new Grafo2(5);

        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);
        grafo.agregarArista(3, 4);

        for (int i = 0; i < 5; i++) {
            if (grafo.esSumidero(i)) {
                System.out.println("el nodo " + i + " es sumidero.");
                return;
            }
        }

        System.out.println("no se encontraro un sumidero");
    }
}
