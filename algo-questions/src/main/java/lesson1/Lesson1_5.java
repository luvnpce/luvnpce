package lesson1;

import java.util.HashMap;

/**
 * 数组arr,在每个数字前决定符号,但所有数字参与再给target,问最后算出target的方法数
 */
public class Lesson1_5 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 3;
        System.out.println(brute(arr, target));
        System.out.println(cache(arr, target));
        System.out.println("=====" + dp(arr, target));
    }

    /**
     * 动态规划：
     * 1. 假设数组里都是非负数（可以把负数转成整数）
     * 2. 求出数组里所有数之和，如果target > sum，那么则没有解
     * 3. 如果target和sum 奇偶不一致，也无解
     * 4.
     * 假设P = 都使用加号的数字，N = 都使用减号的数字，那个 target = P - N
     * 如果在两边都加上P + N，那么target + P + N = 2P
     * 我们知道P + N = sum，那么target + sum = 2P
     * 最终 P = (target + sum) / 2
     * 也就是说，数组arr里，有多少种组合相加可以等于(target + sum) / 2就是最终答案
     */
    public static int dp(int[] arr, int target) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        if (target > sum // 优化2
                || ((target & 1) ^ (sum & 1)) != 0) { // 优化3
            return 0;
        }
        return doDp2(arr, (target + sum) >> 1);
    }

    public static int doDp1(int[] arr, int k) {
        int[][] dp = new int[arr.length + 1][k + 1];
        for (int i = 0; i <= arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int index = arr.length - 1; index >= 0; index --) {
            for (int rest = 0; rest <= k; rest++) {
                int no = dp[index + 1][rest];
                int yes = 0;
                if (rest - arr[index] >= 0) {
                    yes = dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = no + yes;
            }
        }
        return dp[0][k];
    }

    public static int doDp2(int[] arr, int k) {
        int[] dp = new int[k + 1];
        dp[0] = 1;
        for (int num : arr) {
            for (int i = k; i >= num ; i--) {
                dp[i] += dp[i - num];
            }
        }
        return dp[k];
    }

    /**
     * 缓存优化, 针对每一个index，把target=k时的答案存在一个缓存里
     */
    public static int cache(int[] arr, int target) {
        return doCache(arr, 0, target, new HashMap<>());
    }

    public static int doCache(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> cache) {
        if (cache.containsKey(index) && cache.get(index).containsKey(rest)) {
            return cache.get(index).get(rest);
        }
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ans = doCache(arr, index + 1, rest - arr[index], cache)
                + doCache(arr, index + 1, rest + arr[index], cache);
        if (!cache.containsKey(index)) {
            cache.put(index, new HashMap<>());
        }
        cache.get(index).put(rest, ans);
        return ans;
    }


    /**
     * 暴力解法，针对每一个数，要不然就是+，要不然就是减
     */
    public static int brute(int[] arr, int target) {
        return doBrute(arr, 0, target);
    }

    public static int doBrute(int[] arr, int index, int rest) {
        // base case，当到数组最末端，如果rest == 0，则代表有结，否则无解
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        return doBrute(arr, index + 1, rest - arr[index])
                + doBrute(arr, index + 1, rest + arr[index]);
    }
}
