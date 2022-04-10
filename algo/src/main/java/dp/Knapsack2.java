package dp;

/**
 * 数组arr，指定target，请求数组里有多少种组合相加可以得到target
 */
public class Knapsack2 {

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 6, 7};
        int target = 14;
        System.out.println(brute(arr, target));
        System.out.println(dp(arr, target));
    }

    public static int dp(int[] arr, int target) {
        return doDp(arr, target);
    }

    public static int doDp(int[] arr, int target) {
        int[][] dp = new int[arr.length + 1][target + 1];
        for (int i = 0; i <= arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int index = arr.length - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= target; rest++) {
                int no = dp[index + 1][rest];
                int yes = 0;
                if (rest - arr[index] >= 0) {
                    yes = dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = yes + no;
            }
        }
        return dp[0][target];
    }

    public static int brute(int[] arr, int target) {
        return doBrute(arr, 0, target);
    }

    private static int doBrute(int[] arr, int index, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (rest < 0 || index == arr.length) {
            return 0;
        }
        return doBrute(arr, index + 1, rest)
                + doBrute(arr, index + 1, rest - arr[index]);
    }
}
