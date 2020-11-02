package dp;

/**
 * 给你一个坐标x，y，规定只能走k步。
 * 请问马从0，0的位置有多少种方法能走到x，y
 */
public class ChineseChess {

    public static void main(String[] args) {
        int x = 7;
        int y = 8;
        int k = 5;
        System.out.println(brute(x, y, k));
        System.out.println(dp(x, y, k));
    }

    public static int brute(int x, int y, int k) {
        if (k == 0) {
            return (x == 0 && y == 0) ? 1 : 0;
        }
        // 判断有没有出棋盘
        if (x < 0 || x > 9 || y < 0 || y >8) {
            return 0;
        }

        return brute(x+2, y-1, k-1)
                + brute(x+2, y+1, k-1)
                + brute(x+1, y+2, k-1)
                + brute(x-1, y+2, k-1)
                + brute(x-2, y-1, k-1)
                + brute(x-2, y+1, k-1)
                + brute(x+1, y-2, k-1)
                + brute(x-1, y-2, k-1);
    }


    public static int dp(int x, int y, int k) {
        int[][][] dp = new int[10][9][k+1];
        dp[0][0][0] = 1;

        for (int level = 1; level <= k; level++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    dp[i][j][level] = getValue(dp,i+2, j-1, level-1)
                            + getValue(dp,i+2, j+1, level-1)
                            + getValue(dp,i+1, j+2, level-1)
                            + getValue(dp,i-1, j+2, level-1)
                            + getValue(dp,i-2, j-1, level-1)
                            + getValue(dp,i-2, j+1, level-1)
                            + getValue(dp,i+1, j-2, level-1)
                            + getValue(dp,i-1, j-2, level-1);
                }
            }
        }

        return dp[x][y][k];
    }

    private static int getValue(int[][][] dp, int x, int y, int k) {
        // 判断有没有出棋盘
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][k];
    }
}
