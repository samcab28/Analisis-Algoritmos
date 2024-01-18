package lab9;

/*
estudiante: Samir Cabrera
Solucion de n-Reinas con algoritmos geneticos

 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
class NreinasGen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite la cantidad de reinas");
        int nReinas = scanner.nextInt(); // Cantidad de n reinas

        System.out.println("digite uno para visualizar y cero para no");
        int  visualizacion = scanner.nextInt();

        int poblacionInicial;
        if(nReinas > 7){
            poblacionInicial = 50;
        }
        else{
            poblacionInicial = nReinas * nReinas;
        }

        //cantidad de veces que puede repetir la elite
        int N2;
        if(nReinas > 5){
            N2 = 20;
        }
        else{
            N2 = nReinas * nReinas;
        }

        ArrayList<ArrayList<Integer>> eliteAnterior = new ArrayList<>();
        int generacionesSinCambio = 0;
        int generaciones = 100;

        System.out.println("\n\nGeneración: 1");
        ArrayList<ArrayList<Integer>> savePoblacionInicial = generarPoblacionInicial(poblacionInicial, nReinas);
        ArrayList<ArrayList<Integer>> mejoresIndividuos = new ArrayList<>();

        if(visualizacion == 1){
            Collections.sort(savePoblacionInicial, Collections.reverseOrder(new IndividuoComparator()));
            visualizacionDatos(savePoblacionInicial, getPromedioFitness(savePoblacionInicial), mejoresIndividuos(savePoblacionInicial));
        }

        for (int generacion = 2; generacion <= generaciones; generacion++) {
            System.out.println("\n\nGeneración: " + generacion);

            // Ordenar la población por fitness
            Collections.sort(savePoblacionInicial, Collections.reverseOrder(new IndividuoComparator()));

            // Array para los mejores individuos
            mejoresIndividuos = mejoresIndividuos(savePoblacionInicial);

            // Verificar si el élite ha cambiado
            if (!mejoresIndividuos.equals(eliteAnterior)) {
                eliteAnterior = new ArrayList<>(mejoresIndividuos); // Actualizar élite anterior
                generacionesSinCambio = 0; // Reiniciar contador
            } else {
                generacionesSinCambio++; // Incrementar contador si no hay cambios en el élite
            }

            // Verificar el criterio de parada
            if (generacionesSinCambio >= N2) {
                System.out.println("Criterio de parada alcanzado: El élite no ha cambiado en " + N2 + " generaciones.");
                break; // Salir del bucle si se cumple el criterio de parada
            }

            // Array para los mejores individuos
            mejoresIndividuos = mejoresIndividuos(savePoblacionInicial);
            savePoblacionInicial = siguienteGeneracion(savePoblacionInicial, mejoresIndividuos, poblacionInicial,visualizacion);

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
        System.out.println("\nImprimir los dos mejores resultados con promedio fitness diferente a 1.0:");
        for (ArrayList<Integer> mejorIndividuo : mejoresIndividuos) {
            System.out.println("Posición de las reinas: " + mejorIndividuo + "  Fitness: " + calcularFitness(mejorIndividuo));
        }

        //impresion de los resultados correctos
        ArrayList<ArrayList<Integer>> respuestas = new ArrayList<>();
        System.out.println("impresion de los resultados con Correctos");
        for (ArrayList<Integer> i : poblacionOrdenada) {
            double fitnessReina = calcularFitness(i);
            if (fitnessReina == 1.0 && !respuestas.contains(i)) {
                respuestas.add(i);
            }
        }
        if(respuestas.isEmpty()){
            System.out.println("No se encontraron resultados correctos");
        }
        else{
            for(ArrayList<Integer> i:respuestas){
                System.out.println("Posicion de reina: " + i + " fitness: " + calcularFitness(i));
            }
        }


    }

    //metodo para el calculo de la siguiente generacion
    public static ArrayList<ArrayList<Integer>> siguienteGeneracion(ArrayList<ArrayList<Integer>> poblacionInicial, ArrayList<ArrayList<Integer>> mejoresIndividuos,
                                                                    int poblacion, int visualizacion){
        // Generar nueva población mediante crossover y mutación
        ArrayList<ArrayList<Integer>> nuevaPoblacion = new ArrayList<>();

        //seleccion de padres para el crossover
        ArrayList<Integer> padre1 = mejoresIndividuos.get(0);
        ArrayList<Integer> padre2 = mejoresIndividuos.get(1);
        for (int i = 0; i < poblacion; i += 1) {
            // Realizar crossover
            ArrayList<Integer> descendencia = crossover(padre1, padre2);

            //generar mutacion
            nuevaPoblacion.add(aplicarMutacion(descendencia));

            // Agregar descendencia a la nueva población
            nuevaPoblacion.add(descendencia);
        }

        // Ordenar la población por fitness
        Collections.sort(nuevaPoblacion, Collections.reverseOrder(new IndividuoComparator()));

        double promFitnesPasado = getPromedioFitness(poblacionInicial);
        double promFitnesActual = getPromedioFitness(nuevaPoblacion);

        if(visualizacion == 1){
            visualizacionDatos(nuevaPoblacion, promFitnesActual, mejoresIndividuos(nuevaPoblacion));
        }
        if(promFitnesPasado > promFitnesActual){
            System.out.println("generacion pasada mayor, se mantiene");
            return poblacionInicial;
        }

        System.out.println("generacion actual mayor, se actualiza");
        return nuevaPoblacion;

    }

    //funcion para encontrar la media del fitness
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

    //funcion que filtra los mejores individuos
    public static ArrayList<ArrayList<Integer>> mejoresIndividuos(ArrayList<ArrayList<Integer>> poblacionBase) {
        ArrayList<ArrayList<Integer>> mejoresIndividuos = new ArrayList<>();

        int indice = poblacionBase.size() - 1;

        // Encontrar el primer individuo diferente con fitness diferente a 1.0
        while (indice >= 0 && calcularFitness(poblacionBase.get(indice)) == 1.0) {
            indice--;
        }

        // Verificar si no hay individuos con fitness diferente a 1.0
        if (indice < 0) {
            mejoresIndividuos.add(poblacionBase.get(poblacionBase.size()-1));
            mejoresIndividuos.add(poblacionBase.get(poblacionBase.size()-2));
        }

        // Guardar el primer individuo diferente con fitness diferente a 1.0
        mejoresIndividuos.add(poblacionBase.get(indice));

        indice--;

        // Encontrar el segundo individuo diferente con fitness diferente a 1.0
        while (indice >= 0 && calcularFitness(poblacionBase.get(indice)) == 1.0) {
            indice--;
        }

        // Verificar si no hay suficientes individuos diferentes con fitness diferente a 1.0
        if (indice < 0) {
            System.out.println("No hay suficientes individuos diferentes con fitness diferente a 1.0.");
            mejoresIndividuos.clear();
            mejoresIndividuos.add(poblacionBase.get(poblacionBase.size()-1));
            mejoresIndividuos.add(poblacionBase.get(poblacionBase.size()-2));
        }

        // Guardar el segundo individuo diferente con fitness diferente a 1.0
        mejoresIndividuos.add(poblacionBase.get(indice));

        return mejoresIndividuos;
    }

    // Método para aplicar mutación a un individuo
    public static ArrayList<Integer> aplicarMutacion(ArrayList<Integer> individuo) {
        int size = individuo.size();

        // Probabilidad de mutación
        double probabilidadMutacion = 0.10;

        for (int i = 0; i < size; i++) {
            if (Math.random() < probabilidadMutacion) {
                // Cambiar aleatoriamente la posición de la reina
                int nuevaPosicion = (int) (Math.random() * size);
                individuo.set(i, nuevaPosicion);
            }
        }

        return individuo;
    }

    // Método para realizar el crossover entre dos padres
    public static ArrayList<Integer> crossover(ArrayList<Integer> padre1, ArrayList<Integer> padre2) {
        // Asegúrate de que los arrays tengan la misma longitud
        if (padre1.size() != padre2.size()) {
            throw new IllegalArgumentException("Los arrays deben tener la misma longitud");
        }

        int size = padre1.size();

        // Seleccionar dos puntos de cruce aleatorios distintos
        int puntoCruce1 = (int) (Math.random() * (size - 1)) + 1;
        int puntoCruce2;
        do {
            puntoCruce2 = (int) (Math.random() * (size - 1)) + 1;
        } while (puntoCruce2 == puntoCruce1);

        // Asegúrate de que los valores de los puntos de cruce estén dentro de los límites adecuados
        if (puntoCruce1 < 1 || puntoCruce1 >= size || puntoCruce2 < 1 || puntoCruce2 >= size) {
            throw new IllegalArgumentException("Los puntos de cruce no están dentro de los límites adecuados");
        }

        // Ordenar los puntos de cruce
        int puntoInicio = Math.min(puntoCruce1, puntoCruce2);
        int puntoMedio = Math.max(puntoCruce1, puntoCruce2);
        int puntoFin = (int) (Math.random() * (size - puntoMedio)) + puntoMedio;

        // Crear la descendencia utilizando el crossover de tres puntos
        ArrayList<Integer> descendencia = new ArrayList<>(Collections.nCopies(size, -1));

        // Copiar las secciones de los padres al hijo
        for (int i = puntoInicio; i < puntoMedio; i++) {
            descendencia.set(i, padre1.get(i));
        }
        for (int i = puntoMedio; i < puntoFin; i++) {
            descendencia.set(i, padre2.get(i));
        }
        for (int i = puntoFin; i < size; i++) {
            descendencia.set(i, padre1.get(i));
        }

        // Completar cualquier valor faltante con números aleatorios
        for (int i = 0; i < size; i++) {
            if (descendencia.get(i) == -1) {
                int randomValue;
                do {
                    randomValue = (int) (Math.random() * size);
                } while (descendencia.contains(randomValue));
                descendencia.set(i, randomValue);
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