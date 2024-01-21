package ExamenExtra2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ArbolSufijo {
    private class Nodo {
        Map<Character, Nodo> hijos = new HashMap<>();
    }

    private Nodo raiz = new Nodo();
    private List<String> sufijos = new ArrayList<>();

    public void insertar(String palabra) {
        for (int i = 0; i < palabra.length(); i++) {
            insertarSufijo(palabra.substring(i), raiz);
        }
    }

    private void insertarSufijo(String sufijo, Nodo nodo) {
        for (int i = 0; i < sufijo.length(); i++) {
            char c = sufijo.charAt(i);
            nodo = nodo.hijos.computeIfAbsent(c, k -> new Nodo());
        }
        sufijos.add(sufijo);
    }

    public List<String> obtenerSufijos() {
        return sufijos;
    }
}

public class Pregunta1E2 {
    public static void main(String[] args) {
        ArbolSufijo as = new ArbolSufijo();

        as.insertar("2022161229");
        as.insertar("2024123456");
        as.insertar("2019887496");

        List<String> listaFinal = as.obtenerSufijos();

        System.out.println("carnets en sufijos: ");
        for (String i : listaFinal) {
            System.out.println(i);
        }
    }
}

