package dp;

/**
 * 假设由排成一行的N个位置，记为1~N，N一定大于或等于2
 * - 开始时有机器人在其中的M位置上（M一定是1~N中的一个）
 * - 如果机器人来到1位置，那么一下步智能往右来到2位置
 * - 如果机器人来到N位置，那么下一步只能往左来到N-1位置
 * - 如果机器人来到中间位置，那么下一步可以往左或者往右
 * - 规定机器人必须走K步，最终能来到P位置的方法有多少种
 * 给定四个参数N、M、K、P，返回方法数
 */
public class RobotWalk {

    public static int dp(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 采用缓存，缓存下来每个步骤的结果，防止重复计算
        int[][] dp = new int[N+1][K+1];
        for (int row = 0; row <= N; row++) {
            for (int col = 0; col <= K; col++) {
                // -1 = 还没算过这个步骤
                dp[row][col] = -1;
            }
        }
        return doDp(N, M, K, P, dp);
    }

    public static int doDp(int N, int cur, int rest, int P, int[][] dp) {
        if (dp[cur][rest] != -1) {
            // 这个步骤已经算过，直接返回
            return dp[cur][rest];
        }
        if (rest == 0) {
            dp[cur][rest] = cur == P ? 1 : 0;
            return dp[cur][rest];
        }
        if (cur == 1) {
            dp[cur][rest] = doDp(N, 2, rest - 1, P, dp);
            return dp[cur][rest];
        }
        if (cur == N) {
            dp[cur][rest] = doDp(N, N - 1, rest - 1, P, dp);
            return dp[cur][rest];
        }
        dp[cur][rest] = doDp(N, cur + 1, rest - 1, P, dp) + doDp(N, cur - 1, rest - 1, P, dp);
        return dp[cur][rest];
    }

    /**
     * 暴力
     */
    public static int brute(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return doBrute(N, M, K, P);
    }

    // N : 位置为1 ~ N，固定参数
    // cur : 当前在cur位置，可变参数
    // rest : 还剩res步没有走，可变参数
    // P : 最终目标位置是P，固定参数
    // 该函数的含义：只能在1~N这些位置上移动，当前在cur位置，走完rest步之后，停在P位置的方法数作为返回值返回
    public static int doBrute(int N, int cur, int rest, int P) {
        // 如果没有剩余步数了，当前的cur位置就是最后的位置
        // 如果最后的位置停在P上，那么之前做的移动是有效的
        // 如果最后的位置没在P上，那么之前做的移动是无效的
        if (rest == 0) {
            return cur == P ? 1 : 0;
        }
        // 如果还有rest步要走，而当前的cur位置在1位置上，那么当前这步只能从1走向2
        // 后续的过程就是，来到2位置上，还剩rest-1步要走
        if (cur == 1) {
            return doBrute(N, 2, rest - 1, P);
        }
        // 如果还有rest步要走，而当前的cur位置在N位置上，那么当前这步只能从N走向N-1
        // 后续的过程就是，来到N-1位置上，还剩rest-1步要走
        if (cur == N) {
            return doBrute(N, N - 1, rest - 1, P);
        }
        // 如果还有rest步要走，而当前的cur位置在中间位置上，那么当前这步可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到cur-1位置上，还剩rest-1步要走
        // 走向右之后，后续的过程就是，来到cur+1位置上，还剩rest-1步要走
        // 走向左、走向右是截然不同的方法，所以总方法数要都算上
        return doBrute(N, cur + 1, rest - 1, P) + doBrute(N, cur - 1, rest - 1, P);
    }
}
