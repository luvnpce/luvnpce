package dp;

/**
 * 小扣出去秋游，途中收集了一些红叶和黄叶，他利用这些叶子初步整理了一份秋叶收藏集 leaves，
 * 字符串 leaves 仅包含小写字符 r 和 y， 其中字符 r 表示一片红叶，字符 y 表示一片黄叶。
 *
 * 出于美观整齐的考虑，小扣想要将收藏集中树叶的排列调整成「红、黄、红」三部分。每部分树叶数量可以不相等，但均需大于等于 1。
 * 每次调整操作，小扣可以将一片红叶替换成黄叶或者将一片黄叶替换成红叶。请问小扣最少需要多少次调整操作才能将秋叶收藏集调整完毕。
 *
 * leaves = "rrryyyrryyyrr"，output = 2
 * leaves = "ryr"，output = 0
 */
public class CollectLeaves {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String leaves = generateLeaves(10);
            if (brute(leaves) != dp(leaves)) {
                System.out.println("Oops");
                return;
            }
        }
    }

    public static int dp(String leaves) {
        if (null == leaves || leaves.length() < 1) {
            return 0;
        }
        char[] ch = leaves.toCharArray();
        /**
         * dp[i][0] : 把0...i替换成全红需要多少步
         * dp[i][1] : 把0...i替换成红黄需要多少步
         * dp[i][2] : 把0...i替换成红黄红需要多少步
         */
        int[][] dp = new int[leaves.length()][3];
        dp[0][0] = ch[0] == 'y' ? 1 : 0;
        // 这些都是无法完成的，因为叶子数量不够，所以默认MAX_VALUE
        dp[0][1] = dp[0][2] = dp[1][2] = Integer.MAX_VALUE;
        for (int i = 1; i < ch.length; i++) {
            int isYellow = leaves.charAt(i) == 'y' ? 1 : 0;
            int isRed = leaves.charAt(i) == 'r' ? 1 : 0;
            dp[i][0] = dp[i - 1][0] + isYellow;
            dp[i][1] = Math.min(dp[i - 1][1], dp[i - 1][0]) + isRed;
            if (i >= 2) {
                dp[i][2] = Math.min(dp[i-1][2], dp[i - 1][1]) + isYellow;
            }
        }
        return dp[ch.length - 1][2];
    }

    public static int brute(String leaves) {
        if (null == leaves || leaves.length() < 1) {
            return 0;
        }
        return doBrute(leaves.toCharArray(), 0, 0);
    }

    public static int doBrute(char[] arr, int index, int res) {
        if (index > arr.length - 1) {
            if (isValid(arr)) {
                return res;
            }
            return Integer.MAX_VALUE;
        }
        arr[index] = arr[index] == 'r' ? 'y' : 'r';
        int yes = doBrute(arr, index + 1, res + 1);
        arr[index] = arr[index] == 'r' ? 'y' : 'r';
        int no = doBrute(arr, index + 1, res);
        return Math.min(yes, no);
    }

    public static boolean isValid(char[] arr) {
        int index = 0;
        if (arr[index] == 'y') {
            return false;
        }
        int tmp = 0;
        while (index < arr.length && arr[index] == 'r') {
            index++;
        }
        while (index < arr.length && arr[index] == 'y') {
            index++;
            tmp = 1;
        }
        while (index < arr.length && arr[index] == 'r') {
            index++;
            tmp = 2;
        }
        return tmp == 2 && index == arr.length;
    }

    private static String generateLeaves(int maxSize) {
        String leaves = "";
        int size = (int) (Math.random() * (maxSize + 1)) + 3;
        for (int i = 0; i < size; i++) {
            if (Math.random() < 0.5) {
                leaves += 'r';
            } else {
                leaves += 'y';
            }
        }
        return leaves;
    }
}
