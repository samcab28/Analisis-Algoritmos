package lab8;

//backtracking
//No se que hice, pero me salio sin backtraking
import java.util.Scanner;
import java.util.ArrayList;

public class Descifrado {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String p1 = scanner.nextLine();
        String p2 = scanner.nextLine();
        decifrar(p1, p2);
        scanner.close();
    }
    public static void decifrar(String guia,String texto){
        guia.replaceAll(" ", "");
        List<Character> aux=guia.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> abced = IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c).collect(Collectors.toList());
        abced.removeAll(aux);
        for(Character letra:abced){
            for(Character letra2:abced){
                if(letra!=letra2){
                    System.out.println(texto.replace(letra,letra2));
                }
            }
        }
    }
}

