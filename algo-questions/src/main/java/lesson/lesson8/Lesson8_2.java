package lesson.lesson8;

import java.util.Arrays;

/**
 * 给定一个矩阵matrix，值有正、负、0
 * 蛇可以空降到最左列的任何一个位置上，初始增长值是0
 * 蛇每一步可以选择右上、右、右下三个方向的任何一个前进
 * 沿途的数字累加起来，作为增长值；但是蛇一旦增长值为负数，就会死去
 * 蛇有一种能力，可以使用一次，将某个格子里的数变成相反数
 * 蛇可以走到任何格子的时候停止
 * 返回蛇能获得的最大增长值
 */
public class Lesson8_2 {

    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = generateRandomArray(r, c, V);
            int ans1 = brute(matrix);
            int ans2 = dp(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }

    public static int dp(int[][] matrix) {
        if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        Integer res = Integer.MIN_VALUE;
        Info[][] dp = new Info[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int no = matrix[i][0];
            int yes = -matrix[i][0];
            dp[i][0] = new Info(no ,yes);
            res = Math.max(res, Math.max(dp[i][0].no, dp[i][0].yes));
        }
        for (int j = 1; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                int preNo = -1;
                int preYes = -1;
                // 不在最左列，先拿到左边位置的info
                Info pre = dp[i][j - 1];
                preNo = Math.max(preNo, pre.no);
                preYes = Math.max(preYes, pre.yes);
                if (i > 0) {
                    // 不在第一行，代表左上有位置
                    pre = dp[i - 1][j - 1];
                    preNo = Math.max(preNo, pre.no);
                    preYes = Math.max(preYes, pre.yes);
                }
                if (i < matrix.length - 1) {
                    // 不在最下一行，代表左下有位置
                    pre = dp[i + 1][j - 1];
                    preNo = Math.max(preNo, pre.no);
                    preYes = Math.max(preYes, pre.yes);
                }
                // 计算不用能力可获得的增长值，如果preNo == -1，就代表无法到达i,j位置
                int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);
                // 计算使用能力可获得的增长值，如果preNo == -1，就代表无法到达i,j位置
                int p1 = preYes == -1 ? -1 : preYes + matrix[i][j]; // 之前使用了能力
                int p2 = preNo == -1 ? -1 : preNo + (-matrix[i][j]); // 之前没有用过能力，当前位置使用
                int yes = Math.max(-1, Math.max(p1, p2));
                dp[i][j] = new Info(no, yes);
                res = Math.max(res, Math.max(no, yes));
            }
        }
        return res;
    }

    /**
     * 暴力解：
     * 求出每个位置(i,j)可以获得的最大增长值，f(i,j) = 如果蛇停在i,j位置上可以获得的最大增长值
     * 然后取最大
     */
    public static int brute(int[][] matrix) {
        if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                Info ans = doBrute(matrix, i, j);
                res = Math.max(res, Math.max(ans.no, ans.yes));
            }
        }
        return res;
    }

    /**
     * 求出在i,j位置上可以获得的最大增长值（蛇可以从最左列任意一个位置出发）
     * 方法返回一个int数组
     *  1. Info.no 代表如果不使用技能可以获得的最大增长值，如果蛇无法到达i,j位置，那么返回-1
     *  2. Info.yes 代表如果使用技能可以获得的最大增长值，如果蛇无法到达i,j位置，那么返回-1
     */
    private static Info doBrute(int[][] matrix, int i, int j) {
        if (j == 0) {
            // 最左列
            int no = Math.max(matrix[i][j], -1);
            int yes = Math.max(-matrix[i][j], -1);
            return new Info(no, yes);
        } else {
            int preNo = -1;
            int preYes = -1;
            // 不在最左列，先拿到左边位置的info
            Info pre = doBrute(matrix, i, j - 1);
            preNo = Math.max(preNo, pre.no);
            preYes = Math.max(preYes, pre.yes);
            if (i > 0) {
                // 不在第一行，代表左上有位置
                pre = doBrute(matrix, i - 1, j - 1);
                preNo = Math.max(preNo, pre.no);
                preYes = Math.max(preYes, pre.yes);
            }
            if (i < matrix.length - 1) {
                // 不在最下一行，代表左下有位置
                pre = doBrute(matrix, i + 1, j - 1);
                preNo = Math.max(preNo, pre.no);
                preYes = Math.max(preYes, pre.yes);
            }
            // 计算不用能力可获得的增长值，如果preNo == -1，就代表无法到达i,j位置
            int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);
            // 计算使用能力可获得的增长值，如果preNo == -1，就代表无法到达i,j位置
            int p1 = preYes == -1 ? -1 : preYes + matrix[i][j]; // 之前使用了能力
            int p2 = preNo == -1 ? -1 : preNo + (-matrix[i][j]); // 之前没有用过能力，当前位置使用
            int yes = Math.max(-1, Math.max(p1, p2));
            return new Info(no, yes);
        }
    }

    private static int[][] generateRandomArray(int row, int col, int value) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
    }

    private static class Info {
        int no;
        int yes;

        public Info(int x, int y) {
            this.no = x;
            this.yes = y;
        }
    }
}
