package lab9;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
1. representacion de individuo
2. inicializacion de la poblacion
3. funcion de evaluacion o fitness
4. seleccion de individuos
5. operadores geneticos o crossover
6. reemplazo de poblacion
7. criterio de parada
 */

public class NreinasGen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite la cantidad de reinas");
        int nReinas = scanner.nextInt(); // Cantidad de n reinas

        System.out.println("Digite el tamaño de la población inicial");
        int poblacionInicial = scanner.nextInt();

        System.out.println("Digite la cantidad de generaciones");
        int generaciones = scanner.nextInt();

        System.out.println("\n\nGeneración: 1");
        //array de almacenamiento de la poblacion inicial
        //se genera la primera poblacion inicial del array de manera aleatoria
        ArrayList<ArrayList<Integer>> savePoblacionInicial = generarPoblacionInicial(poblacionInicial, nReinas);
        ArrayList<ArrayList<Integer>> mejoresIndividuos = new ArrayList<>();
        for (int generacion = 2; generacion <= generaciones; generacion++) {
            System.out.println("\n\nGeneración: " + generacion);

            // Ordenar la población por fitness
            Collections.sort(savePoblacionInicial, Collections.reverseOrder(new IndividuoComparator()));

            // Array para los mejores individuos
             mejoresIndividuos = mejoresIndividuos(savePoblacionInicial);


            savePoblacionInicial = siguienteGeneracion(savePoblacionInicial, mejoresIndividuos, poblacionInicial);
        }

        System.out.println("\n\n\nRESULTADO FINAL");
        visualizacionDatos(savePoblacionInicial,getPromedioFitness(savePoblacionInicial), mejoresIndividuos);
    }


    //metodo para la visualizacion de datos
    public static void visualizacionDatos(ArrayList<ArrayList<Integer>> poblacionOrdenada, double fitness,  ArrayList<ArrayList<Integer>> mejoresIndividuos){
        // Imprimir la población ordenada
        System.out.println("\nImprimir las reinas en orden de fitness");
        for (ArrayList<Integer> posicionReina : poblacionOrdenada) {
            double fitnessReina = calcularFitness(posicionReina);
            System.out.println("Posición de las reinas: " + posicionReina + " fitness de la reina: " + fitnessReina);
        }

        // Calcular el promedio del fitness de la generación
        double promedioFitnessGeneracion = fitness;
        System.out.println("\nPromedio del fitness de la generación: " + promedioFitnessGeneracion);

        // Impresión de los dos mejores resultados de la generación
        System.out.println("\nImprimir los dos mejores resultados:");
        for (ArrayList<Integer> mejorIndividuo : mejoresIndividuos) {
            System.out.println("Posición de las reinas: " + mejorIndividuo + "  Fitness: " + calcularFitness(mejorIndividuo));
        }
    }


    //metodo para el calculo de la siguiente generacion
    public static ArrayList<ArrayList<Integer>> siguienteGeneracion(ArrayList<ArrayList<Integer>> poblacionInicial, ArrayList<ArrayList<Integer>> mejoresIndividuos, int poblacion){
        // Generar nueva población mediante crossover y mutación
        ArrayList<ArrayList<Integer>> nuevaPoblacion = new ArrayList<>();

        for (int i = 0; i < poblacion; i += 1) {
            // Seleccionar dos padres para el crossover
            ArrayList<Integer> padre1 = mejoresIndividuos.get(i % 2);
            ArrayList<Integer> padre2 = mejoresIndividuos.get((i + 1) % 2);

            // Realizar crossover
            ArrayList<Integer> descendencia = crossover(padre1, padre2);

            // Aplicar mutación (opcional)
            aplicarMutacion(descendencia);

            // Agregar descendencia a la nueva población
            nuevaPoblacion.add(descendencia);
        }

        System.out.println("\n\n\nsegunda generacion");
        System.out.println(nuevaPoblacion);

        // Ordenar la población por fitness
        Collections.sort(nuevaPoblacion, Collections.reverseOrder(new IndividuoComparator()));

        // Array para los mejores individuos
        ArrayList<ArrayList<Integer>> mejoresIndividuos2 = mejoresIndividuos(nuevaPoblacion);


        visualizacionDatos(nuevaPoblacion,getPromedioFitness(nuevaPoblacion), mejoresIndividuos2);

        double promFitnesPasado = getPromedioFitness(poblacionInicial);
        double promFitnesActual = getPromedioFitness(nuevaPoblacion);

        if(promFitnesPasado > promFitnesActual){
            System.out.println("generacion pasada mayor, se mantiene");
            return poblacionInicial;
        }

        System.out.println("generacion actual mayor, se actualiza");
        return nuevaPoblacion;

    }

    public static double getPromedioFitness(ArrayList<ArrayList<Integer>> poblacionGeneral){
        //conteo del fitnnes de la generacion
        double fitnessTotal = 0.0;

        // recopilatorio del fitness
        for (ArrayList<Integer> posicionReina : poblacionGeneral) {
            double fitness = calcularFitness(posicionReina);
            fitnessTotal += fitness;
        }
        double promedioFitnessGeneracion = fitnessTotal / poblacionGeneral.size();

        return promedioFitnessGeneracion;
    }


    // Método para obtener los mejores individuos de una población
    public static ArrayList<ArrayList<Integer>> mejoresIndividuos(ArrayList<ArrayList<Integer>> poblacionBase) {
        ArrayList<ArrayList<Integer>> mejoresIndividuos = new ArrayList<>();

        // Obtener los últimos dos individuos de la lista (en orden ascendente)
        ArrayList<Integer> ultimoIndividuo = poblacionBase.get(poblacionBase.size() - 1);

        // Encontrar el primer individuo diferente del último
        int indice = poblacionBase.size() - 2;
        while (indice >= 0 && poblacionBase.get(indice).equals(ultimoIndividuo)) {
            indice--;
        }

        // Verificar si todos son iguales
        if (indice < 0) {
            System.out.println("Todos los mejores individuos son iguales.");
            return poblacionBase;  // Devolver la lista completa
        }

        // Guardar los dos mejores resultados
        mejoresIndividuos.add(ultimoIndividuo);
        mejoresIndividuos.add(poblacionBase.get(indice));

        return mejoresIndividuos;
    }





    // Método para aplicar mutación a un individuo
    public static void aplicarMutacion(ArrayList<Integer> individuo) {
        int size = individuo.size();

        // Probabilidad de mutación
        double probabilidadMutacion = 0.25;

        for (int i = 0; i < size; i++) {
            if (Math.random() < probabilidadMutacion) {
                // Cambiar aleatoriamente la posición de la reina
                int nuevaPosicion = (int) (Math.random() * size);
                individuo.set(i, nuevaPosicion);
            }
        }
    }


    // Método para realizar el crossover entre dos padres
    public static ArrayList<Integer> crossover(ArrayList<Integer> padre1, ArrayList<Integer> padre2) {
        int size = padre1.size();

        // Seleccionar un punto de cruce aleatorio
        int puntoCruce = (int) (Math.random() * (size - 1)) + 1;

        // Crear la descendencia utilizando el crossover de un punto
        ArrayList<Integer> descendencia = new ArrayList<>(padre1.subList(0, puntoCruce));

        for (int i = 0; i < size; i++) {
            if (!descendencia.contains(padre2.get(i))) {
                descendencia.add(padre2.get(i));
            }
        }

        return descendencia;
    }




    //funcion para el filtrado
    static class IndividuoComparator implements java.util.Comparator<ArrayList<Integer>> {
        @Override
        public int compare(ArrayList<Integer> individuo1, ArrayList<Integer> individuo2) {
            return Double.compare(calcularFitness(individuo2), calcularFitness(individuo1));
        }
    }


    //funcion de calculo del fitness
    public static double calcularFitness(ArrayList<Integer> posicionReina) {
        int conflicts = contarConflictos(posicionReina);
        return 1.0 / (1 + conflicts);
    }

    // Función para contar los conflictos en una posición de reinas
    private static int contarConflictos(ArrayList<Integer> posicionReina) {
        int conflicts = 0;
        int size = posicionReina.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // Misma fila, misma columna o misma diagonal
                if (posicionReina.get(i).equals(posicionReina.get(j)) || Math.abs(posicionReina.get(i) - posicionReina.get(j)) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }



    //funcion para crear el model de las primeras reinas
    public static ArrayList<Integer> inicializarPosicionRandom(int N) {
        ArrayList<Integer> posicionReina = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            posicionReina.add(i);
        }

        Collections.shuffle(posicionReina);

        return posicionReina;
    }

    //funcion para crear la poblacion inicial
    public static ArrayList<ArrayList<Integer>> generarPoblacionInicial(int poblacionInicial, int nReinas){
        ArrayList<ArrayList<Integer>> savePoblacionInicial = new ArrayList<>();
        for(int i = 0; i < poblacionInicial; i++){
            ArrayList<Integer> posicionReina = inicializarPosicionRandom(nReinas);
            savePoblacionInicial.add(posicionReina);
        }

        return savePoblacionInicial;
    }
}