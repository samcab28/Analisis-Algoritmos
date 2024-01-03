package examen;

import java.util.ArrayDeque;
import java.util.Deque;

public class Pregunta4 {
    static class RectangleInfo {
        int length;
        int width;

        RectangleInfo(int length, int width) {
            this.length = length;
            this.width = width;
        }
    }

    public RectangleInfo maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return new RectangleInfo(0, 0);
        }

        int ans = 0;
        int[] hist = new int[matrix[0].length];
        int rectLength = 0;
        int rectWidth = 0;

        for (char[] row : matrix) {
            for (int i = 0; i < row.length; ++i) {
                hist[i] = row[i] == '*' ? 0 : hist[i] + 1; // Treat '*' as '0'
            }
            RectangleInfo rectangleInfo = largestRectangleArea(hist);
            int currentLength = rectangleInfo.length;
            int currentWidth = rectangleInfo.width;

            if (currentLength * currentWidth > ans) {
                ans = currentLength * currentWidth;
                rectLength = currentLength;
                rectWidth = currentWidth;
            }
        }

        return new RectangleInfo(rectLength, rectWidth);
    }

    private RectangleInfo largestRectangleArea(int[] heights) {
        int ans = 0;
        int length = 0;
        int width = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i <= heights.length; ++i) {
            while (!stack.isEmpty() && (i == heights.length || heights[stack.peek()] > heights[i])) {
                final int h = heights[stack.pop()];
                final int w = stack.isEmpty() ? i : i - stack.peek() - 1;

                if (h * w > ans) {
                    ans = h * w;
                    length = h;
                    width = w;
                }
            }
            stack.push(i);
        }

        return new RectangleInfo(length, width);
    }

    public static void main(String[] args) {
        char[][] matrix = {
                "*.....*".toCharArray(),
                ".......".toCharArray()
        };

        Pregunta4 solution = new Pregunta4();
        RectangleInfo result = solution.maximalRectangle(matrix);

        System.out.println("Length: " + result.length);
        System.out.println("Width: " + result.width);
    }
}

