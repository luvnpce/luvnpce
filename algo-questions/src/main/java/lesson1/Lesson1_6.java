package lesson1;

/**
 * 给定一个二维数组matrix,你可以从任何位置出发,走上下左右四个方向,返回能走出来的最长的递增链长度
 * leetcode 329
 * 从顶向下的动态规划
 */
public class Lesson1_6 {

    public static int cache(int[][] matrix) {
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, doCache(matrix, i, j, dp));
            }
        }
        return ans;
    }

    /**
     * 从matrix[i][j]开始走，返回能走出来的最长递增连
     */
    public static int doCache(int[][] m, int i, int j, int[][] dp) {
        // base case, dp[i][j] == 0，代表这个位置还没有计算过，否则直接返回
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        // go up
        int up = i > 0 && m[i][j] < m[i - 1][j] ? doCache(m, i - 1, j, dp) : 0;
        // go down
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? doCache(m, i + 1, j, dp) : 0;
        // go left
        int left = j > 0 && m[i][j] < m[i][j - 1] ? doCache(m, i, j - 1, dp) : 0;
        // go right
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? doCache(m, i, j + 1, dp) : 0;

        int ans = 1 + Math.max(Math.max(up, down), Math.max(left, right));
        dp[i][j] = ans;
        return ans;
    }


    public static int brute(int[][] matrix) {
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, doBrute(matrix, i, j));
            }
        }
        return ans;
    }

    /**
     * 从matrix[i][j]开始走，返回能走出来的最长递增连
     */
    public static int doBrute(int[][] m, int i, int j) {
        // base case 下面已经校验过了，所以不需要再校验
//        if (i < 0 || i == m.length || j < 0 || j == m[0].length) {
//            return 0;
//        }
        // go up
        int up = i > 0 && m[i][j] < m[i - 1][j] ? doBrute(m, i - 1, j) : 0;
        // go down
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? doBrute(m, i + 1, j) : 0;
        // go left
        int left = j > 0 && m[i][j] < m[i][j - 1] ? doBrute(m, i, j - 1) : 0;
        // go right
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? doBrute(m, i, j + 1) : 0;

        return 1 + Math.max(Math.max(up, down), Math.max(left, right));
    }
}
