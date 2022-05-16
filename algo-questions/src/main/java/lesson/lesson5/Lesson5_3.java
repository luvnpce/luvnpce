package lesson.lesson5;

import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个字符串s1和s2，请问s2删除多少个字符就可以成为s1的子串
 */
public class Lesson5_3 {

    /**
     * 解法1：KMP
     * 先找出s2的所有子序列，然后根据字符串长度排序从大到小排序
     * 遍历所有的子序列，找出是s1子串的子序列x，然后s2.length - x.length
     * ================================
     * 假设N = s1.length，M = s2.length
     * 时间复杂度是O(2^M * N)，如果s2长度很小是没有问题的
     */
    public static int solution1(String s1, String s2) {
        List<String> s2Subs = new ArrayList<>();
        //1、生成所有子序列
        solution1_getAllSubsequences(s2.toCharArray(), 0, "", s2Subs);
        //2、排序
        s2Subs.sort((a, b) -> b.length() - a.length());
        //3、遍历
        for (String str : s2Subs) {
            if (s1.contains(str)) {
                return s2.length() - str.length();
            }
        }
        //兜底（全删除）
        return s2.length();
    }

    private static void solution1_getAllSubsequences(char[] chars, int index, String path, List<String> s2Subs) {
        if (index == chars.length) {
            s2Subs.add(path);
            return;
        }
        solution1_getAllSubsequences(chars, index + 1, path, s2Subs);
        solution1_getAllSubsequences(chars, index + 1, path + chars[index], s2Subs);
    }

    /**
     * 解法2：动态规划
     * 用类似Lesson5_2里面的样本对应解法，只允许删除操作
     */
    public static int solution2(String s1, String s2) {
        int ans = Integer.MAX_VALUE;
        char[] chars2 = s2.toCharArray();
        //1、找出s1的所有子串
        for (int start = 0; start < s1.length(); start++) {
            for (int end = start + 1; end <= s1.length(); end++) {
                ans = Math.min(ans, solution2_minCost(s1.substring(start, end).toCharArray(), chars2));
            }
        }
        return ans == Integer.MAX_VALUE
                ? s2.length() // 兜底，只能全删除
                : ans;
    }

    /**
     * chars1：s1的子串
     * chars2：s2
     * 用动态规划来找出s2需要删除多少个字符来变成s1的子串
     * dp[i][j]
     * chars2[0...i]仅通过删除的行为变成chars[0...j]最小的代价
     */
    private static int solution2_minCost(char[] chars1, char[] chars2) {
        if (chars1.length > chars2.length) {
            return Integer.MAX_VALUE;
        }
        int M = chars1.length;
        int N = chars2.length;
        int[][] dp = new int[N + 1][M + 1];
        // 初始化，所以赋值MAX_VALUE
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        /**
         * base case
         * dp[i][0]，需要删除i个字符才能变成chars1的前0个字符
         */
        for (int i = 0; i <= N; i++) {
            dp[i][0] = i;
        }
        /**
         * dp[i][j]，有2种可能
         * 1、要删除第i个字符，然后看dp[i-1][j]是否有解
         * 2、不要删除第i个字符，那么必须chars2[i-1] == chars1[j-1]，那么就是dp[i-1][j-1]
         */
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                // 第一种可能
                if (dp[i - 1][j] != Integer.MAX_VALUE) {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
                // 第二种可能
                if (chars2[i - 1] == chars1[j - 1]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }
            }
        }
        return dp[N][M];
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String s1 = StringUtils.generateRandomString(5, 20);
            String s2 = StringUtils.generateRandomString(5, 10);
            if (solution1(s1, s2) != solution2(s1, s2)) {
                System.out.println("Ooops");
                System.out.println("s1 = " + s1);
                System.out.println("s2 = " + s2);
            }
        }
    }
}
