package lesson.lesson2;

/**
 * 现在有司机N*2人，调度中心会将所有司机平分给A、B两个区域
 * 第i个司机去A可得收入income[i][0]
 * 第i个司机去B可得收入income[i][1]
 * 返回所有调度方案中能使所有司机的总收入最高的方案，是多少钱
 */
public class Lesson2_5 {

    public static int dp(int[][] income) {
        int N = income.length;
        int M = N >> 1;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= M; j++) {
                if (N - i == j) {
                    // 剩余司机只能去A区域
                    dp[i][j] = income[i][0] + dp[i + 1][j - 1];
                } else if (j == 0) {
                    // 剩余司机只能去B区域
                    dp[i][j] = income[i][1] + dp[i + 1][j];
                } else {
                    int p1 = income[i][0] + dp[i + 1][j - 1];
                    int p2 = income[i][1] + dp[i + 1][j];
                    dp[i][j] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][M];
    }

    public int brute(int[][] income) {
        // 司机数量，必须是偶数，否则不能平分
        if (null == income || income.length < 2 || (income.length & 1) != 0 ) {
            return 0;
        }
        int N = income.length;
        // 要去一个区域的人的数量
        int M = N >> 1;
        return doBrute(income, 0, M);
    }

    /**
     * 从第index位司机开始分配，返回从[index....end]整体的最大收入
     * @param aRest A区域剩余人数
     */
    public static int doBrute(int[][] income, int index, int aRest) {
        if (index == income.length) {
            return 0;
        }
        if (income.length - index == aRest) {
            // 剩余司机只能去A区域
            return income[index][0] + doBrute(income, index + 1, aRest - 1);
        }
        if (aRest == 0) {
            // 剩余司机只能去B区域
            return income[index][1] + doBrute(income, index + 1, aRest);
        }
        // 可以去A或B
        int goA = income[index][0] + doBrute(income, index + 1, aRest - 1);
        int goB = income[index][1] + doBrute(income, index + 1, aRest);
        return Math.max(goA, goB);

    }

}
