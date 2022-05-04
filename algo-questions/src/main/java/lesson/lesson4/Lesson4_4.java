package lesson.lesson4;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 返回一个数组中，选择的数字不能相邻的情况下，最大子序列累加和
 */
public class Lesson4_4 {

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            int[] arr = ArrayUtils.generate(20, 100);
            if (brute(arr) != dp(arr)) {
                System.out.println(Arrays.toString(arr));
            }
        }
    }

    public static int dp(int[] arr) {
        int[] dp = new int[arr.length + 2];
        for (int i = arr.length - 1; i >= 0; i--) {
            int p1 = arr[i] + dp[i + 2];
            int p2 = dp[i + 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp[0];
    }

    public static int brute(int[] arr) {
        return doBrute(arr, 0, 0);
    }

    public static int doBrute(int[] arr, int index, int sum) {
        if (index >= arr.length) {
            return sum;
        }
        int p1 = doBrute(arr, index + 2, sum + arr[index]);
        int p2 = doBrute(arr, index + 1, sum);

        return Math.max(p1, p2);
    }

}
