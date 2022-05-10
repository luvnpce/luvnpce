package lesson.lesson5;

/**
 * 给定一个字符串x，和目标字符串y
 * 1. 增加
 * 2. 删除
 * 3. 替换（某个字符改成另外一个字符）
 * 以上三种方式来让x变成y
 * 每种操作都有不同的行为代价
 * 求最少需要付出多少代价
 */
public class Lesson5_2 {

    /**
     * 动态规划：样本对应
     * dp[i][j]
     * 让s1的前i个字符 变成 s2的前j个字符的最小代价是多少
     */
    public static int solution(String s1, String s2, int add, int delete, int edit) {
        if (null == s1 || null == s2) {
            return 0;
        }
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];

        /**
         * base case
         * dp[0][0]，s1的前0个字符变成s2的前0个字符，代价等于0
         * dp[i][0]，s1的前i个字符变成s2的前0个字符，s1的每个字符都要删除，代价等于i * delete
         * dp[0][j]，s1的前0个字符变成s2的前j个字符，s2的每个字符都需要s1去新增，代价等于j * add
         */
        for (int i = 0; i <= chars1.length; i++) {
            dp[i][0] = i * delete;
        }
        for (int j = 0; j <= chars2.length ; j++) {
            dp[0][j] = j * add;
        }

        /**
         * dp[i][j]，这种就有4种可能
         * 1. s1前i-1个字符变成s2前j个字符，外加一个删除s1第i个字符的代价，dp[i-1][j] + delete
         * 2. s1前i个字符变成s2前j-1个字符，外加一个新增s2第j个字符的代价，dp[i][j-1] + add
         * 3. s1的第i个字符 == s2的第j个字符，那么dp[i][j]等于dp[i-1][j-1]
         * 4. s1的第i个字符 != s2的第j个字符,那么dp[i][j]等于dp[i-1][j-1]外加一个edit操作（默认edit的代价< add + delete）
         * ============================
         * 这4种取代价最小的
         */
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                // 第3和第4种可能
                dp[i][j] = dp[i - 1][j - 1] + (chars1[i -1 ] == chars2[j - 1] ? 0 : edit);
                // 第1
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + delete);
                // 第2
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + add);
            }
        }
        return dp[chars1.length][chars2.length];
    }

}
