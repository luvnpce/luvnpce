package leetcode;

import utils.StringUtils;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * difficulty: medium
 *
 * Given a string s, find the length of the longest substring without repeating characters.
 */
public class LeetCode_3_LongestSubstringNoRepeat {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String s = StringUtils.generateRandomString(26, 10);
            int dp = dp(s);
            int brute = brute(s);
            if (dp != brute) {
                System.out.println(s);
                System.out.println("dp="+dp);
                System.out.println("brute="+brute);
                break;
            }
        }
    }

    /**
     * index从0到N遍历
     * dp[index]代表：字符串以index结尾，他的最长无重复子字符串有多长
     *
     * 当index每到一个新的位置时，需要考虑以下2点
     * 1、当前字符上次出现的位置
     * 2、在index-1位置，能往左推多远的距离没有重复字符
     */
    public static int dp(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int[] map = new int[256]; // 记录每个字符上一次出现的位置，默认-1
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        map[chars[0]] = 0; // 0位置的字符，出现在0位置

        int ans = 1;
        int prev = 1; // 上一个位置，向左推了多长（没有重复）
        for (int i = 1; i < chars.length; i++) {
            /**
             * 情况1
             * 找到当前字符，上一次出现的位置
             * 那么从当前字符开始往左推，最多只能推的距离 = 当前位置-上一次出现的位置
             */
            int p1 = i - map[chars[i]];
            /**
             * 情况2
             * 已知在i-1位置，能往左推多少距离
             * 那么在i位置能推的距离 = i-1位置的距离+1
             */
            int p2 = prev + 1;
            /**
             * 最终答案：
             * 情况1和情况2的最小值
             */
            int cur = Math.min(p1, p2);
            ans = Math.max(ans, cur);

            prev = cur;
            // 更新一下当前字符上一次出现的位置
            map[chars[i]] = i;
        }
        return ans;
    }

    /**
     * 滑动窗口暴力解法
     */
    public static int brute(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }

        int ans = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            boolean[] used = new boolean[256];
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    ans = Math.max(ans, j - i);
                    break;
                }
                if (used[chars[j]]) {
                    break;
                }
                used[chars[j]] = true;
                ans = Math.max(ans, j - i + 1);
            }
        }
        return ans;
    }

}
