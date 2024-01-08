package examen;

/*
estudiante: Samir Cabrera
complejidad: O(m * n)
 */

import java.util.ArrayDeque;
import java.util.Deque;

public class Pregunta4 {
    static class InformacionRectangulo {
        int longitud;
        int ancho;

        InformacionRectangulo(int longitud, int ancho) {
            this.longitud = longitud;
            this.ancho = ancho;
        }
    }

    public InformacionRectangulo rectanguloMaximo(char[][] matriz) {
        if (matriz.length == 0) {
            return new InformacionRectangulo(0, 0);
        }

        int ans = 0;
        int[] hist = new int[matriz[0].length];
        int longitudRectangulo = 0;
        int anchoRectangulo = 0;

        for (char[] fila : matriz) {
            for (int i = 0; i < fila.length; ++i) {
                hist[i] = fila[i] == '*' ? 0 : hist[i] + 1; // Tratar '*' como '0'
            }
            InformacionRectangulo informacionRectangulo = areaRectanguloMasGrande(hist);
            int longitudActual = informacionRectangulo.longitud;
            int anchoActual = informacionRectangulo.ancho;

            if (longitudActual * anchoActual > ans) {
                ans = longitudActual * anchoActual;
                longitudRectangulo = longitudActual;
                anchoRectangulo = anchoActual;
            }
        }

        return new InformacionRectangulo(longitudRectangulo, anchoRectangulo);
    }

    private InformacionRectangulo areaRectanguloMasGrande(int[] alturas) {
        int ans = 0;
        int longitud = 0;
        int ancho = 0;
        Deque<Integer> pila = new ArrayDeque<>();

        for (int i = 0; i <= alturas.length; ++i) {
            while (!pila.isEmpty() && (i == alturas.length || alturas[pila.peek()] > alturas[i])) {
                final int h = alturas[pila.pop()];
                final int w = pila.isEmpty() ? i : i - pila.peek() - 1;

                if (h * w > ans) {
                    ans = h * w;
                    longitud = h;
                    ancho = w;
                }
            }
            pila.push(i);
        }

        return new InformacionRectangulo(longitud, ancho);
    }

    public static void main(String[] args) {
        String[] entrada = {
                "*.....*",
                "......."
        };

        char[][] matriz = new char[entrada.length][];
        for (int i = 0; i < entrada.length; i++) {
            matriz[i] = entrada[i].toCharArray();
        }

        Pregunta4 solucion = new Pregunta4();
        InformacionRectangulo resultado = solucion.rectanguloMaximo(matriz);

        System.out.println("(" + resultado.ancho + "," + resultado.longitud + ")");
    }
}
