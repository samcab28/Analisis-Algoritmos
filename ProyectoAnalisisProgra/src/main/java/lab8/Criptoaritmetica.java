package lab8;

/*
estudiante: Samir Cabrera

complejidad: O(10^n)
 */

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Criptoaritmetica {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String palabra1 = scanner.nextLine();
        String palabra2 = scanner.nextLine();
        String palabra3 = scanner.nextLine();
        criptoaritmetica(palabra1, palabra2, palabra3);
        scanner.close();
    }

    public static void criptoaritmetica(String palabra1, String palabra2, String palabra3) {
        palabra1 = palabra1.toUpperCase();
        palabra2 = palabra2.toUpperCase();
        palabra3 = palabra3.toUpperCase();
        List<Character> letrasUnicas = obtenerLetrasUnicas(palabra1, palabra2, palabra3);
        int[] valores = new int[letrasUnicas.size()];
        boolean[] digitosUsados = new boolean[10];
        generarTodasLasSoluciones(palabra1, palabra2, palabra3, letrasUnicas,
                valores, digitosUsados, 0);
    }

    private static List<Character> obtenerLetrasUnicas(String... palabras) {
        return Arrays.stream(palabras)
                .flatMapToInt(String::chars)
                .distinct()
                .mapToObj(c -> (char) c)
                .toList();
    }

    private static void generarTodasLasSoluciones(String palabra1, String palabra2,
                                                  String palabra3,
                                                  List<Character> letrasUnicas,
                                                  int[] valores,
                                                  boolean[] digitosUsados, int indice) {
        if (indice == letrasUnicas.size()) {
            if (esCriptoaritmeticaValida(palabra1, palabra2, palabra3, letrasUnicas, valores)) {
                imprimirSolucion(palabra1, palabra2, palabra3, letrasUnicas, valores);
            }
            return;
        }
        char letraActual = letrasUnicas.get(indice);
        for (int digito = 0; digito <= 9; digito++) {
            if (!digitosUsados[digito]) {
                digitosUsados[digito] = true;
                valores[indice] = digito;
                generarTodasLasSoluciones(palabra1, palabra2, palabra3, letrasUnicas,
                        valores, digitosUsados, indice + 1);
                digitosUsados[digito] = false; // Retroceder
            }
        }
    }

    private static boolean esCriptoaritmeticaValida(String palabra1, String palabra2,
                                                    String palabra3,
                                                    List<Character> letrasUnicas,
                                                    int[] valores) {
        int suma = 0;
        int acarreo = 0;
        for (int i = palabra1.length() - 1; i >= 0; i--) {
            int val1 = valores[letrasUnicas.indexOf(palabra1.charAt(i))];
            int val2 = valores[letrasUnicas.indexOf(palabra2.charAt(i))];
            int val3 = valores[letrasUnicas.indexOf(palabra3.charAt(i))];
            int sumaColumna = val1 + val2 + acarreo;
            acarreo = sumaColumna / 10;
            if (val3 != sumaColumna % 10) {
                return false;
            }
            suma = suma * 10 + sumaColumna % 10;
        }
        return acarreo == 0 && suma != 0;
    }

    private static void imprimirSolucion(String palabra1, String palabra2,
                                         String palabra3,
                                         List<Character> letrasUnicas,
                                         int[] valores) {
        for (int i = 0; i < palabra1.length(); i++) {
            System.out.print(valores[letrasUnicas.indexOf(palabra1.charAt(i))]);
        }
        System.out.println();
        for (int i = 0; i < palabra2.length(); i++) {
            System.out.print(valores[letrasUnicas.indexOf(palabra2.charAt(i))]);
        }
        System.out.println();
        for (int i = 0; i < palabra3.length(); i++) {
            System.out.print(valores[letrasUnicas.indexOf(palabra3.charAt(i))]);
        }
        System.out.println();
        System.out.println();
    }
}
