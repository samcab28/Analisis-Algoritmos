package lab9;

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

        //System.out.println("Digite el tamaño de la población inicial");
        int poblacionInicial = nReinas*10;

        //System.out.println("Digite la cantidad de generaciones");
        int generaciones = 100;

        System.out.println("\n\nGeneración: 1");
        ArrayList<ArrayList<Integer>> savePoblacionInicial = generarPoblacionInicial(poblacionInicial, nReinas);
        ArrayList<ArrayList<Integer>> mejoresIndividuos = new ArrayList<>();
        ArrayList<ArrayList<Integer>> filtrado = new ArrayList<>();
        ArrayList<ArrayList<Integer>> respuestas = new ArrayList<>();
        for (int generacion = 2; generacion <= generaciones; generacion++) {
            System.out.println("\n\nGeneración: " + generacion);

            // Ordenar la población por fitness
            Collections.sort(savePoblacionInicial, Collections.reverseOrder(new IndividuoComparator()));

            // Array para los mejores individuos
            mejoresIndividuos = mejoresIndividuos(savePoblacionInicial);


            savePoblacionInicial = siguienteGeneracion(savePoblacionInicial, mejoresIndividuos, poblacionInicial);


            respuestas = filtradoRespuestas(savePoblacionInicial, respuestas);

        }

        System.out.println("\n\n\nRESULTADO FINAL");
        visualizacionDatos(savePoblacionInicial,getPromedioFitness(savePoblacionInicial), mejoresIndividuos);

        System.out.println("\n\nListado de Respuestas");
        System.out.println(respuestas);
    }


    public static ArrayList<ArrayList<Integer>> filtradoRespuestas(ArrayList<ArrayList<Integer>> poblacion, ArrayList<ArrayList<Integer>> respuestasAnteriores) {
        ArrayList<ArrayList<Integer>> respuestas = new ArrayList<>();

        for (int i = 0; i < poblacion.size(); i++) {
            ArrayList<Integer> individuo = poblacion.get(i);
            double fitness = calcularFitness(individuo);

            // Verificar si el individuo es una respuesta válida
            if (fitness == 1.0 && !respuestas.contains(individuo) && !respuestasAnteriores.contains(individuo)) {
                respuestas.add(individuo);
            }
        }

        return respuestas;
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
        // Ordenar la población por fitness
        Collections.sort(nuevaPoblacion, Collections.reverseOrder(new IndividuoComparator()));

        // Array para los mejores individuos
        ArrayList<ArrayList<Integer>> mejoresIndividuos2 = mejoresIndividuos(nuevaPoblacion);

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
        // Asegúrate de que los arrays tengan la misma longitud
        if (padre1.size() != padre2.size()) {
            throw new IllegalArgumentException("Los arrays deben tener la misma longitud");
        }

        int size = padre1.size();

        // Seleccionar un punto de cruce aleatorio
        int puntoCruce = (int) (Math.random() * (size - 1)) + 1;

        // Asegúrate de que el valor de puntoCruce esté dentro de los límites adecuados
        if (puntoCruce < 1 || puntoCruce >= size) {
            throw new IllegalArgumentException("El punto de cruce no está dentro de los límites adecuados");
        }

        // Crear la descendencia utilizando el crossover de un punto
        ArrayList<Integer> descendencia = new ArrayList<>(Collections.nCopies(size, -1));

        // Copiar la sección del padre1 al hijo
        for (int i = 0; i < puntoCruce; i++) {
            descendencia.set(i, padre1.get(i));
        }

        // Copiar la sección del padre2 al hijo, completando con números aleatorios
        int currentIndex = puntoCruce;
        for (int i = 0; i < size; i++) {
            if (!descendencia.contains(padre2.get(i))) {
                while (descendencia.get(currentIndex) != -1) {
                    currentIndex = (currentIndex + 1) % size;
                }
                descendencia.set(currentIndex, padre2.get(i));
                currentIndex = (currentIndex + 1) % size;
            }
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