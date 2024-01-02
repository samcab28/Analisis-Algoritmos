package lab8;

//backtracking

import java.util.Scanner;
import java.util.ArrayList;

public class Descifrado {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("letras separadas");
        String input = scanner.nextLine();

        ArrayList<String> palabrasArrayList = new ArrayList<>();

        // Dividir la entrada en palabras y agregarlas al ArrayList
        for (String palabra : input.split(" ")) {
            palabrasArrayList.add(palabra);
        }

        // Imprimir el ArrayList
        System.out.println("Palabras en el ArrayList:");
        for (String palabra : palabrasArrayList) {
            System.out.println(palabra);
        }

        System.out.println("frase:");
        String frase = scanner.nextLine();
        System.out.println(frase);
    }
}

