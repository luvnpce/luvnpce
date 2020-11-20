package matrix;

/**
 * 给定一个矩阵，找出岛的总数量
 *  - 岛：被0包围的1，就属于一个岛
 */
public class CountIsland {

    public static void main(String[] args) {

    }

    /**
     * 感染解法：O(MN)，单核CPU的解法
     * 遍历所有位置，当发现一个位置==1时，然后将其感染（改为2）并把所有周围的1都一起感染
     *  - 一次就感染了整座岛
     * 然后我们一共发现了几次1，就代表有几座岛
     */
    public static int count(int[][] matrix) {
        if (null == matrix || null == matrix[0]) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    res++;
                    infect(matrix, i, j, matrix.length, matrix[0].length);
                }
            }
        }
        return res;
    }

    public static void infect(int[][] matrix, int i, int j, int N, int M) {
        if (i >= N || j >= M || i < 0 || j < 0 || matrix[i][j] != 1) {
            return;
        }
        matrix[i][j] = 2;
        infect(matrix, i + 1, j, N, M);
        infect(matrix, i - 1, j, N, M);
        infect(matrix, i, j + 1, N, M);
        infect(matrix, i, j - 1, N, M);
    }
}
