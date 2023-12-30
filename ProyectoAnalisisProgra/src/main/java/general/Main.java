package general;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Your code goes here
        Scanner scanner = new Scanner(System.in);

        int V = scanner.nextInt(); //cantidad de nodos

        /*
        la variable E indica el viaje de los nodos
        primer numero = nodo inicio
        segundo numero = nodo final
        tercer numero = costo de viaje

        1. pedir la cantidad de lineas
        2. almacenar los datos en un arraylist de arraylist, datos
        mantendran el esquema mencionado

        recordar que es un grafo no dirigido
         */

        int E = scanner.nextInt(); //cantidad de lineas

        ArrayList<ArrayList> viajes = new ArrayList<>(); //arraylist para mantener la informacion de viajes

        for(int i = 0; i<E; i++){
            ArrayList<Integer> datosViaje = new ArrayList<>();
            int nodoOrigen = scanner.nextInt();
            int nodoDestino = scanner.nextInt();
            int costoViaje = scanner.nextInt();

            datosViaje.add(nodoOrigen);
            datosViaje.add(nodoDestino);
            datosViaje.add(costoViaje);

            viajes.add(datosViaje);
        }

        System.out.println(viajes);




    }
}
