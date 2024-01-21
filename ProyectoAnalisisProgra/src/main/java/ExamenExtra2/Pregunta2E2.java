package ExamenExtra2;

import java.util.*;

class OrdenadorPeliculas {
    private Map<Integer, List<Integer>> grafo;
    public OrdenadorPeliculas() {
        this.grafo = new HashMap<>();
    }
    public void agregarRelacion(int p, int q) {
        if (!grafo.containsKey(p)) {
            grafo.put(p, new ArrayList<>());
        }
        grafo.get(p).add(q);
    }
    public List<Integer> ordenamiento() {
        List<Integer> resultado = new ArrayList<>();
        Set<Integer> visitados = new HashSet<>();

        for (Integer nodo : grafo.keySet()) {
            if (!visitados.contains(nodo)) {
                busqueda(nodo, visitados, resultado);
            }
        }

        Collections.reverse(resultado);
        return resultado;
    }
    private void busqueda(Integer nodo, Set<Integer> visitados, List<Integer> resultado) {
        visitados.add(nodo);

        if (grafo.containsKey(nodo)) {
            for (Integer sucesor : grafo.get(nodo)) {
                if (!visitados.contains(sucesor)) {
                    busqueda(sucesor, visitados, resultado);
                }
            }
        }

        resultado.add(nodo);
    }
}

public class Pregunta2E2 {
    public static void main(String[] args) {
        OrdenadorPeliculas ordenador = new OrdenadorPeliculas();
        ordenador.agregarRelacion(1, 2);
        ordenador.agregarRelacion(2, 4);
        ordenador.agregarRelacion(4,3);
        List<Integer> secuenciaOrdenada = ordenador.ordenamiento();
        System.out.println("peliculas ordenadas");
        for (Integer numero : secuenciaOrdenada) {
            System.out.print(numero + " ");
        }
    }
}


