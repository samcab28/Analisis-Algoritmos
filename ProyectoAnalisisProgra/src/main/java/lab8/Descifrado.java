package lab8;

/*
estudiante: Samir Cabrera

complejidad: O(2^n)
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Descifrado {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String letrasNoCambiadas = scanner.nextLine().toLowerCase();
        String textoCifrado = scanner.nextLine();
        char[] textoActual = new char[textoCifrado.length()];
        ArrayList<String> combinaciones = new ArrayList<>();
        descifrarTexto(textoCifrado.toCharArray(), letrasNoCambiadas.toCharArray(), 0, textoActual, combinaciones);
        filtrarMejoresSalidas(combinaciones, textoCifrado);
    }

    private static void descifrarTexto(char[] textoCifrado, char[] letrasNoCambiadas, int index, char[] textoActual, ArrayList<String> combinaciones) {
        if (index == textoCifrado.length) {
            combinaciones.add(new String(textoActual));
            return;
        }
        char letraCifrada = textoCifrado[index];
        if (letrasNoCambiadasContains(letrasNoCambiadas, letraCifrada)) {
            textoActual[index] = letraCifrada;
            descifrarTexto(textoCifrado, letrasNoCambiadas, index + 1, textoActual, combinaciones);
        } else {
            for (char letraPosible = 'a'; letraPosible <= 'z'; letraPosible++) {
                if (!isLetterUsed(textoActual, letraPosible)) {
                    textoActual[index] = letraPosible;
                    descifrarTexto(textoCifrado, letrasNoCambiadas, index + 1, textoActual, combinaciones);
                }
            }
        }
    }

    private static boolean letrasNoCambiadasContains(char[] letrasNoCambiadas, char letra) {
        for (char c : letrasNoCambiadas) {
            if (c == letra) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLetterUsed(char[] textoActual, char letra) {
        for (int i = 0; i < textoActual.length; i++) {
            if (textoActual[i] == letra) {
                return true;
            }
        }
        return false;
    }

    private static void filtrarMejoresSalidas(ArrayList<String> combinaciones, String textoCifrado) {
        int limiteSalidas = 5;
        int salidasImpresas = 0;
        for (String combinacion : combinaciones) {
            int coincidencias = contarCoincidencias(combinacion, textoCifrado);
            if (coincidencias > 0) {
                System.out.println(combinacion);
                salidasImpresas++;
            }
            if (salidasImpresas >= limiteSalidas) {
                break;
            }
        }
    }


    private static int contarCoincidencias(String texto1, String texto2) {
        int coincidencias = 0;
        for (int i = 0; i < texto1.length(); i++) {
            if (texto1.charAt(i) == texto2.charAt(i)) {
                coincidencias++;
            }
        }
        return coincidencias;
    }
}
