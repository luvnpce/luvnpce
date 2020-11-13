package matrix;

public class SpiralPrint {

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        print(matrix);
    }

    /**
     * 一圈一圈，从最外圈开始打印
     * 每打印完一圈后，缩小一格再打印下一个圈
     */
    public static void print(int[][] matrix) {
        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        while (startRow <= endRow && startCol <= endCol) {
            print(matrix, startRow++, startCol++, endRow--, endCol--);
        }

    }

    /**
     * 打印外围一圈
     * 只要知道a（左上角的点）和b（右下角的点）就可以打印
     */
    private static void print(int[][] matrix, int aX, int aY, int bX, int bY) {
        if (aX == bX) {
            // 两个点都在一条横线上，那么直接从左到右打印
            for (int i = aY; i <= bY; i++) {
                System.out.println(matrix[aX][i]);
            }
        } else if (aY == bY) {
            // 两个点都在一条竖线上，那么直接从上到下打印
            for (int i = aX; i <= bX; i++) {
                System.out.println(matrix[i][aY]);
            }
        } else {
            int curX = aX;
            int curY = aY;
            while (curY != bY) {
                // 从左往右
                System.out.println(matrix[curX][curY]);
                curY++;
            }
            while (curX != bX) {
                // 从上到下
                System.out.println(matrix[curX][curY]);
                curX++;
            }
            while (curY != aY) {
                // 从右到左
                System.out.println(matrix[curX][curY]);
                curY--;
            }
            while (curX != aX) {
                // 从上到下
                System.out.println(matrix[curX][curY]);
                curX--;
            }
        }
    }
}
