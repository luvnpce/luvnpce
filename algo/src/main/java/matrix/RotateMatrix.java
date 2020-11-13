package matrix;

public class RotateMatrix {

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },{13, 14, 15, 16} };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=============================");
        printMatrix(matrix);
    }

    /**
     * 从最外层的圈开始，一层一层的旋转
     */
    public static void rotate(int[][] matrix) {
        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        while (startRow < endRow) {
            rotate(matrix, startRow++, startCol++, endRow--, endCol--);
        }
    }

    /**
     * 这个旋转其实就是4个元素互相换位置
     * 假设我们正方形矩阵的边长为6，那么就有5个4元素的小组
     */
    private static void rotate(int[][] matrix, int aX, int aY, int bX, int bY) {
        int tmp = 0;
        for (int i = 0; i < (bX - aX); i++) { // 假设ax = 0, bx = 5, 矩阵长度是6，那么i = [0..4]
            /**
             * matrix[aX][aY+i] 第i组的第一个点
             * matrix[aX+i][bY] 第i组的第二个点
             * matrix[bx][bY-i] 第i组的第三个点
             * matrix[bx-i][aY] 第i组的第四个点
             */
            // 记录第一个点
            tmp = matrix[aX][aY+i];
            // 移动第四个点到第一个点
            matrix[aX][aY+i] = matrix[bX-i][aY];
            // 移动第三个点到第四个点
            matrix[bX-i][aY] = matrix[bX][bY-i];
            // 移动第二个点到第三个点
            matrix[bX][bY-i] = matrix[aX+i][bY];
            // 移动第一个点到第二个点
            matrix[aX+i][bY] = tmp;
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
