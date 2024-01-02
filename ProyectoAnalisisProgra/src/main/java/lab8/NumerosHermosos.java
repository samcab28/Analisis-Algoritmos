package lab8;

/*
estudiante: Samir Cabrera

complejidad: O(n)
 */
import java.util.ArrayList;
import java.util.Scanner;

public class NumerosHermosos {

    public static ArrayList<Integer> generarNumerosHermososEnRango(int inicio, int fin) {
        ArrayList<Integer> numerosHermosos = new ArrayList<>();

        for (int num = inicio; num <= fin; num++) {
            String numStr = Integer.toString(num);
            int longitud = numStr.length();

            // Asegúrate de que la longitud del número sea par
            if (longitud % 2 == 0) {
                int mitad = longitud / 2;
                String primeraParte = numStr.substring(0, mitad);
                String segundaParte = numStr.substring(mitad);

                // Verifica si la suma de los dígitos en ambas partes es igual
                if (sumarDigitos(primeraParte) == sumarDigitos(segundaParte)) {
                    numerosHermosos.add(num);
                }
            }
        }

        return numerosHermosos;
    }

    private static int sumarDigitos(String str) {
        int suma = 0;
        for (char c : str.toCharArray()) {
            suma += Character.getNumericValue(c);
        }
        return suma;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int repeticionesT = scanner.nextInt();

        for(int i = 0; i<repeticionesT; i++){
            int inicioRango = scanner.nextInt();
            int finRango = scanner.nextInt();

            ArrayList<Integer> resultados = generarNumerosHermososEnRango(inicioRango, finRango);

            System.out.println("Números hermosos en el rango [" + inicioRango + ", " + finRango + "]:");
            for (int numero : resultados) {
                System.out.print(numero + " ");
            }
        }

    }
}
