package dp;

/**
 * 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6
 * a b c d e f g h i j k l n m o p q r s t u v w x y z
 *
 * 给一个纯数字的字符串，把字符串按照上面的形式转换成字母，求能转换成多少种字符串
 */
public class ConvertLetterToStringV2 {

    public static void main(String[] args) {
        System.out.println(brute("11111"));
        System.out.println(dp("11111"));
    }

    public static int dp(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int N = chars.length;
        int[] dp = new int[N+1];

        dp[N] = 1;
        for (int i = N-1; i >= 0; i--) {
            if (chars[i] == '0') {
                dp[i] = 0;
            }
            if (chars[i] == '1' ) {
                dp[i] = dp[i+1] + ((i + 1) < N ? dp[i+2] : 0);
            } else if (chars[i] == '2') {
                dp[i] = dp[i+1] + ((((i + 1) < N && chars[i + 1] >= '0' && chars[i + 1] <= '6')) ? dp[i+2] : 0);
            } else {
                dp[i] = dp[i+1];
            }
        }
        return dp[0];
    }

    public static int brute(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return doBrute(str.toCharArray(), 0);
    }

    // str[0...i-1]已经转化完了，固定了
    // i之前的位置，如何转化已经做过决定了, 不用再关心
    // i... 有多少种转化的结果
    public static int doBrute(char[] str, int i) {
        if (i == str.length) { // base case
            return 1;
        }
        if (str[i] == '0') {
            return 0;
        }
        if (str[i] == '1') {
            int res = doBrute(str, i + 1);
            if (i + 1 < str.length) {
                res += doBrute(str, i + 2);
            }
            return res;
        }
        if (str[i] == '2') {
            int res = doBrute(str, i + 1);
            if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                res += doBrute(str, i + 2); // (i和i+1)作为单独的部分，后续有多少种方法
            }
            return res;
        }
        return doBrute(str, i + 1);
    }

}
