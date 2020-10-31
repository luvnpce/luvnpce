package dp;

/**
 * 字符串1：a123bc
 * 字符串2：12de3fz
 *
 *
 */
public class LongestCommonSubSequence {


    public static void main(String[] args) {
        String s1 = "";
        String s2 = "eatt";
        System.out.println(dp(s1, s2));
        System.out.println(brute(s1, s2, s1.length(), s2.length()));
    }

    public static int brute(String s1, String s2, int m, int n) {
        if (m == 0 || n == 0)
            return 0;
        if (s1.charAt(m - 1) == s2.charAt(n - 1))
            return 1 + brute(s1, s2, m - 1, n - 1);
        else
            return Math.max(brute(s1, s2, m, n - 1), brute(s1, s2, m - 1, n));
    }

    public static int dp(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int[][] dp = new int[chars1.length][chars2.length];

        // 初始化
        dp[0][0] = chars1[0] == chars2[0] ? 1 : 0;
        for (int i = 1; i < chars1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], chars1[i] == chars2[0] ? 1 : 0);
        }
        for (int j = 1; j < chars2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], chars1[0] == chars2[j] ? 1 : 0);
        }
        // 计算
        for (int i = 1; i < chars1.length; i++) {
            for (int j = 1; j < chars2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (chars1[i] == chars2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[chars1.length - 1][chars2.length - 1];
    }


}
