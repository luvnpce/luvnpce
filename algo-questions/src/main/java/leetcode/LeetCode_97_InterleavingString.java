package leetcode;

/**
 * https://leetcode.com/problems/interleaving-string/
 * difficulty: medium
 *
 * Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.
 * An interleaving of two strings s and t is a configuration where they are divided into non-empty substrings such that:
 *
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
 * Note: a + b is the concatenation of strings a and b.
 */
public class LeetCode_97_InterleavingString {

    /**
     * 动态规划：样本对应
     * dp[i][j]
     * s1的前个i字符和s2的前j个字符，能否交错组成出s3的前i+j个字符
     */
    public static boolean solution(String s1, String s2, String s3) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        char[] chars3 = s3.toCharArray();
        // base case
        if (chars1.length + chars2.length != chars3.length) {
            return false;
        }

        boolean[][] dp = new boolean[chars1.length + 1][chars2.length + 1];
        dp[0][0] = true;
        /**
         * base case
         * 用s1前i个字符，s2前0个字符，是否能组成s3前i个字符
         * 意思就是对比s1和s3相同位置上的字符是否一样
         */
        for (int i = 1; i <= chars1.length; i++) {
            dp[i][0] = chars1[i - 1] == chars3[i - 1];
            if (!dp[i][0]) {
                break;
            }
        }
        /**
         * base case
         * 用s2前i个字符，s1前0个字符，是否能组成s3前i个字符
         * 意思就是对比s2和s3相同位置上的字符是否一样
         */
        for (int j = 1; j <= chars2.length; j++) {
            dp[0][j] = chars2[j - 1] == chars3[j - 1];
            if (!dp[0][j]) {
                break;
            }
        }

        /**
         * 用s1前i个字符，s2前j个字符，是否能交错组成s3前i+j个字符
         * 一共两种可能：
         * 1. s1的第i个字符 == s3的第i+j个字符，那么要判断dp[i-1][j]是否为true
         * 2. s2的第j个字符 == s3的第i+j个字符，那么要判断dp[i][j-1]是否为true
         *
         * dp[i][j]等于上面两种情况的||
         */
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                boolean p1 = chars1[i - 1] == chars3[i + j - 1] && dp[i - 1][j];
                boolean p2 = chars2[j - 1] == chars3[i + j - 1] && dp[i][j - 1];
                dp[i][j] = p1 || p2;
            }
        }
        return dp[chars1.length][chars2.length];
    }
}
