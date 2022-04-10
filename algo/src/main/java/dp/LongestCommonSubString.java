package dp;

/**
 * 字符串1：a123bc
 * 字符串2：12de3fz
 * 求最长公共子串："12"
 *
 */
public class LongestCommonSubString {


    public static void main(String[] args) {
        String s1 = "a123bc";
        String s2 = "123de3fz";
        System.out.println(dp(s1, s2));
    }

    /**
     * dp[i][j]的含义：如果公共子串必须以s1的i字符结尾，并且同时以s2的j字符结尾
     * 最长公共子串的长度是多少
     * ex:
     * s1：a12b123c
     * s2：a1b123bc
     *         (a) (1) (b) (1) (2) (3) (b) (c)
     *          0   1   2   3   4   5   6   7
     * (a) 0    1   0   0   0   0   0   0   0   从右往左开始，在每个点时往右下方移动计算
     * (1) 1    0
     * (2) 2    0
     * (b) 3    0
     * (1) 4    0
     * (2) 5    0
     * (3) 6    0
     * (c) 7    0
     *
     * dp[i][j] = s1[i] == s2[j] = dp[i-1][j-1]+1
     */
    public static String dp(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return "";
        }
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int row = 0; // 出发点的行号
        int col = s2.length() - 1; // 出发点的列号
        int maxLength = 0;
        int endPosition = 0;
        while (row < s1.length()) {
            int i = row;
            int j = col;
            int length = 0;
            while (i < s1.length() && j < s2.length()) {
                if (chars1[i] != chars2[j]) {
                    length = 0;
                } else {
                    length++;
                }
                if (length > maxLength) {
                    endPosition = i;
                    maxLength = length;
                }
                // 2. 从当前点往右下方移动
                i++;
                j++;
            }
            // 1. 从二维表的第一行的最右列往左移动，当到第一列后再往下移动
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return s1.substring(endPosition - maxLength + 1, endPosition + 1);
    }


}
