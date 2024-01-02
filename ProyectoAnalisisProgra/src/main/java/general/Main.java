package general;

import java.util.Scanner;

public class Main {

    static int[][] two_dim_cost_matrix = new int[100][100];
    static boolean[] visited_copy_array = new boolean[100];
    static int no_of_vertices, current_vertex, total_min_cost = 0;

    public static void shortestDistance(int c_vertex) {
        visited_copy_array[c_vertex - 1] = true;
        System.out.println(c_vertex + "--->");
        int nxt_visit = next_visit(c_vertex);
        if (nxt_visit == Integer.MAX_VALUE) {
            System.out.println(current_vertex);
            total_min_cost += two_dim_cost_matrix[c_vertex - 1][current_vertex - 1];
        }
        shortestDistance(nxt_visit);
    }

    public static int next_visit(int c_vertex) {
        int MIN = Integer.MAX_VALUE;
        int cost_spent = 0, next_vertex = Integer.MAX_VALUE;

        for (int i = 0; i < no_of_vertices; i++) {
            if (two_dim_cost_matrix[c_vertex - 1][i] != 0 && !visited_copy_array[i]) {
                if (two_dim_cost_matrix[c_vertex - 1][i] < MIN) {
                    MIN = two_dim_cost_matrix[c_vertex - 1][i];
                    cost_spent = two_dim_cost_matrix[c_vertex - 1][i];
                    next_vertex = i;
                }
            }
        }

        if (MIN != Integer.MAX_VALUE) {
            total_min_cost += cost_spent;
        }

        return next_vertex + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of vertices: ");
        no_of_vertices = scanner.nextInt();

        System.out.println("Enter the cost matrix: ");
        for (int i = 0; i < no_of_vertices; i++) {
            for (int j = 0; j < no_of_vertices; j++) {
                two_dim_cost_matrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the starting vertex: ");
        current_vertex = scanner.nextInt();

        shortestDistance(current_vertex);

        System.out.println("Total minimum cost: " + total_min_cost);

        scanner.close();
    }
}
