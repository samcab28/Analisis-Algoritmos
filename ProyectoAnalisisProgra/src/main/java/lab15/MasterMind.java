package lab15;

import java.util.ArrayList;
import java.util.Scanner;
/*
estudiante: Samir Cabrera
 */
class MasterMind {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Obtener la cantidad de colores y espacios
        System.out.print("digite la cantidad de colores: ");
        int cantidadColores = scanner.nextInt();
        System.out.print("digite la cantidad de espacios: ");
        int cantidadEspacios = scanner.nextInt();

        // Lista de posibles combinaciones
        ArrayList<ArrayList<Integer>> posiblesCombinaciones = generarPosiblesCombinaciones(cantidadColores, cantidadEspacios);

        // Generar una combinación aleatoria
        ArrayList<Integer> combinacionSecreta = posiblesCombinaciones.get((int) (Math.random() * posiblesCombinaciones.size()));


        while (!posiblesCombinaciones.isEmpty()) {
            // Generar una combinación propuesta aleatoria
            ArrayList<Integer> combinacionPropuesta = posiblesCombinaciones.get((int) (Math.random() * posiblesCombinaciones.size()));
            System.out.println("combinacion propuesta: " + combinacionPropuesta);

            // Obtener la respuesta del usuario
            System.out.print("ingresar fichas rojas y blancas: ");
            int fichasRojas = scanner.nextInt();
            int fichasBlancas = scanner.nextInt();

            // Verificar si la combinación propuesta es igual a la combinación secreta
            if (combinacionPropuesta.equals(combinacionSecreta)) {
                System.out.println("combinacion adivinada");
                break;
            }

            // Filtrar las posibles combinaciones según la respuesta del usuario
            posiblesCombinaciones = filtrarCombinaciones(posiblesCombinaciones, combinacionPropuesta, fichasRojas, fichasBlancas);

            /*
            // Mostrar todas las combinaciones con las fichas proporcionadas
            System.out.println("Combinaciones con " + fichasRojas + " fichas rojas y " + fichasBlancas + " fichas blancas:");
            for (ArrayList<Integer> posibleCombinacion : posiblesCombinaciones) {
                System.out.println(posibleCombinacion);
            }*/
        }


        // Si el bucle termina y posiblesCombinaciones está vacío, mostrar el mensaje de combinación secreta
        if (posiblesCombinaciones.isEmpty()) {
            System.out.println("no hay mas combinaciones posibles, la combinación secreta era: " + combinacionSecreta);
        }

        scanner.close();
    }

    private static ArrayList<ArrayList<Integer>> generarPosiblesCombinaciones(int cantidadColores, int cantidadEspacios) {
        ArrayList<ArrayList<Integer>> posiblesCombinaciones = new ArrayList<>();
        generarPosiblesCombinacionesRec(cantidadColores, cantidadEspacios, new ArrayList<>(), posiblesCombinaciones);
        return posiblesCombinaciones;
    }

    // generar todas las combinaciones
    private static void generarPosiblesCombinacionesRec(int cantidadColores, int cantidadEspacios,
                                                        ArrayList<Integer> combinacionParcial,
                                                        ArrayList<ArrayList<Integer>> posiblesCombinaciones) {
        if (combinacionParcial.size() == cantidadEspacios) {
            posiblesCombinaciones.add(new ArrayList<>(combinacionParcial));
            return;
        }

        for (int i = 1; i <= cantidadColores; i++) {
            combinacionParcial.add(i);
            generarPosiblesCombinacionesRec(cantidadColores, cantidadEspacios, combinacionParcial, posiblesCombinaciones);
            combinacionParcial.remove(combinacionParcial.size() - 1);
        }
    }

    // filtracion de combinaciones
    private static ArrayList<ArrayList<Integer>> filtrarCombinaciones(
            ArrayList<ArrayList<Integer>> posiblesCombinaciones,
            ArrayList<Integer> combinacionActual,
            int fichasRojas, int fichasBlancas) {

        ArrayList<ArrayList<Integer>> nuevasPosiblesCombinaciones = new ArrayList<>();

        for (ArrayList<Integer> combinacion : posiblesCombinaciones) {
            if (combinacion.size() == combinacionActual.size()) {
                int rojas = contarFichasRojas(combinacionActual, combinacion);
                int blancas = contarFichasBlancas(combinacionActual, combinacion) - rojas;

                if (rojas == fichasRojas && blancas == fichasBlancas) {
                    nuevasPosiblesCombinaciones.add(combinacion);
                }
            }
        }

        return nuevasPosiblesCombinaciones;
    }

    // contar la cantidad de fichas rojas
    private static int contarFichasRojas(ArrayList<Integer> combinacion1, ArrayList<Integer> combinacion2) {
        int rojas = 0;
        for (int i = 0; i < combinacion1.size(); i++) {
            if (combinacion1.get(i).intValue() == combinacion2.get(i).intValue()) {
                rojas++;
            }
        }
        return rojas;
    }

    // contar la cantidad de fichas blancas
    private static int contarFichasBlancas(ArrayList<Integer> combinacion1, ArrayList<Integer> combinacion2) {
        int blancas = 0;
        for (int i = 0; i < combinacion1.size(); i++) {
            if (combinacion1.contains(combinacion2.get(i).intValue()) && combinacion1.get(i).intValue() != combinacion2.get(i).intValue()) {
                blancas++;
            }
        }
        return blancas;
    }

}
