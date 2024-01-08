package examen;

/*
estudiante: Samir Cabrera
complejidad: O(n) + O(log(n))
 */


import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Pregunta1 {

    public static boolean subsequenceSum(ArrayList<Integer> Lista, int num) {
        int n = Lista.size();
        TreeSet<Integer> set = new TreeSet<>();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += Lista.get(i);
            if (sum == num) {
                return true;
            }
            if (set.contains(sum - num)) {
                return true;
            }

            set.add(sum);
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> P = new ArrayList<>();
        P.add(1);
        P.add(2);
        P.add(1);
        P.add(3);
        P.add(2);
        P.add(4);
        P.add(8);

        System.out.print("Ingrese el valor objetivo V: ");
        int V = scanner.nextInt();

        System.out.println(subsequenceSum(P, V));
    }
}
