package examen;

public class Pregunta4 {
    public static int[] newHouse(String[] grid) {
        if (grid == null || grid.length == 0 || grid[0].length() == 0) {
            return new int[]{0, 0};
        }

        int rows = grid.length;
        int cols = grid[0].length();
        int[][] dp = new int[rows][cols];

        // Inicializar la primera fila y columna del arreglo dp
        for (int i = 0; i < rows; i++) {
            dp[i][0] = (grid[i].charAt(0) == '.') ? 1 : 0;
        }

        for (int j = 0; j < cols; j++) {
            dp[0][j] = (grid[0].charAt(j) == '.') ? 1 : 0;
        }

        // Calcular el arreglo dp
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (grid[i].charAt(j) == '.') {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        // Encontrar el máximo en el arreglo dp
        int maxArea = 0;
        int maxRow = 0;
        int maxCol = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dp[i][j] > 0) {
                    int area = dp[i][j];
                    if (area > maxArea) {
                        maxArea = area;
                        maxRow = dp[i][j];
                        maxCol = j + 1 - dp[i][j] + 1;
                    }
                }
            }
        }

        return new int[]{maxRow, maxCol};
    }

    public static void main(String[] args) {
        String[] grid = {"*.....*", "......."};
        int[] result = newHouse(grid);
        System.out.println("(" + result[0] + "," + result[1] + ")");
    }
}
