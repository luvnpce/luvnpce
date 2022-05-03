package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * https://leetcode.com/problems/freedom-trail/
 * difficulty: hard
 *
 */
public class LeetCode_514_FreedomTrail {

    /**
     * 动态规划解法
     * 直接在暴力解法上加缓存
     */
    public static int dp(String ring, String key) {
        int N = ring.length();
        int M = key.length();
        int[][] dp = new int[N][M + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= M; j++) {
                // -1代表没有计算过
                dp[i][j] = -1;
            }
        }
        return doDp(0, 0, key.toCharArray(), fillMap(ring, key), N, dp);
    }

    public static int doDp(int buttonIndex, int keyIndex, char[] keyChars, HashMap<Character, List<Integer>> map, int size, int[][] dp) {
        if (dp[buttonIndex][keyIndex] != -1) {
            return dp[buttonIndex][keyIndex];
        }
        int ans = Integer.MAX_VALUE;
        if (keyIndex == keyChars.length) {
            ans = 0;
        } else {
            char cur = keyChars[keyIndex];
            List<Integer> nextPositions = map.get(cur);
            for (Integer next : nextPositions) {
                int cost = dial(buttonIndex, next, size) + 1 + doDp(next, keyIndex + 1, keyChars, map, size, dp);
                ans = Math.min(ans, cost);
            }
        }
        dp[buttonIndex][keyIndex] = ans;
        return ans;
    }

    /**
     * 暴力解法
     * 针对每一个key里的字符，去尝试所有拨号方式
     */
    public static int brute(String ring, String key) {
        return doBrute(0, 0, key.toCharArray(), fillMap(ring, key), ring.length());
    }

    /**
     * @param buttonIndex 电话圈里，当前指针的位置
     * @param keyIndex 目标字符串（key）里当前指向的字符位置
     * @param keyChars 目标字符串（key）的字符数组
     * @param map 记录每个字符，对应在目标字符串（key）里出现的位置
     * @param size 电话圈里一个多少个字符
     * @return
     */
    public static int doBrute(int buttonIndex, int keyIndex, char[] keyChars, HashMap<Character, List<Integer>> map, int size) {
        if (keyIndex == keyChars.length) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        char cur = keyChars[keyIndex];
        // 拿到这个目标字符在电话圈上的所有位置
        List<Integer> nextPositions = map.get(cur);
        // 尝试该字符的每一个位置
        for (Integer next : nextPositions) {
            int cost = dial(buttonIndex, next, size) + 1 + doBrute(next, keyIndex + 1, keyChars, map, size);
            ans = Math.min(ans, cost);
        }
        return ans;
    }

    private static HashMap<Character, List<Integer>> fillMap(String ring, String key) {
        HashMap<Character, List<Integer>> map = new HashMap<>();
        char[] chars = ring.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            map.computeIfAbsent(chars[i], (k) -> new ArrayList<>());
            map.get(chars[i]).add(i);
        }
        return map;
    }

    /**
     * 拨电话圈的最短路径
     */
    private static int dial(int i1, int i2, int size) {
        // 顺时针拨
        int p1 = Math.abs(i1 - i2);
        // 逆时针拨
        int p2 = Math.min(i1, i2) + size - Math.max(i1, i2);
        return Math.min(p1, p2);
    }
}
