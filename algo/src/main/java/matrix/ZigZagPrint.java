package matrix;

/**
 * 给你一个包含 m x n 个元素的矩阵 (m 行, n 列), 求该矩阵的之字型遍历。
 * [
 *   [1, 2,  3,  4],
 *   [5, 6,  7,  8],
 *   [9,10, 11, 12]
 * ]
 * 返回 [1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8, 12]
 */
public class ZigZagPrint {

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        print(matrix);
    }

    public static void print(int[][] matrix) {
        int aRow = 0;
        int aCol = 0;
        int bRow = 0;
        int bCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        boolean fromUp = false; // 是不是从右上往坐下打印
        while (aRow != endRow + 1) {
            // 告诉你斜线的两端，根据fromUp的方向去打印
            print(matrix, aRow, aCol, bRow, bCol, fromUp);
            aRow = aCol == endCol ? aRow + 1 : aRow;
            aCol = aCol == endCol ? aCol : aCol + 1;
            bCol = bRow == endRow ? bCol + 1 : bCol;
            bRow = bRow == endRow ? bRow : bRow + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    private static void print(int[][] matrix, int aRow, int aCol, int bRow, int bCol, boolean fromUp) {
        if (fromUp) {
            while (aRow != bRow + 1) {
                System.out.println(matrix[aRow++][aCol--] + " ");
            }
        } else {
            while (bRow != aRow - 1) {
                System.out.println(matrix[bRow--][bCol++] + " ");
            }
        }
    }

}
