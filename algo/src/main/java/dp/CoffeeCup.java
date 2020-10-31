package dp;

import java.util.Arrays;

/**
 * 给定一个数组，代表每个人喝完咖啡准备刷杯子的时间
 * 只有一台洗碗机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一个杯子
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 返回让所有咖啡杯遍干净的最早完成时间
 * 三个参数：int[] arr、int a、int b
 */
public class CoffeeCup {

    public static void main(String[] args) {
        int len = 5;
        int max = 9;
        int testTime = 50000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            Arrays.sort(arr);
            int a = (int) (Math.random() * 5) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = brute2(arr, a, b, 0, 0);
            int ans2 = dp(arr, a, b);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2);
                System.out.println("===============");
                break;
            }
        }
    }

    /**
     * @param arr 准备刷杯子的时间（不变）
     * @param a 洗碗机耗时（不变）
     * @param b 挥发耗时（不变）
     * @param index 遍历的位置（每次+1）
     * @param machine 洗碗机空闲的时间点
     * @param time 最后完成时间
     */
    public static int brute(int[] arr, int a, int b, int index, int machine, int time) {
        if (null == arr || index == arr.length) {
            return time;
        }
        int cup = arr[index];
        /**
         * 用洗碗机洗，有几种情况
         * 1. 洗碗机空闲时间（machine） > 当前时间（cup），洗碗机下一次空闲时间 = machine + a
         * 2. 洗碗机空闲时间（machine） < 当前时间（cup），洗碗机下一次空闲时间 = cup + a
         * 最后完成时间 = 已知最后完成时间 vs 洗碗机洗完这个杯子的时间（下一次空闲的时间） 较大的那个
         */
        int res1 = brute(arr, a, b, index + 1, Math.max(machine, cup) + a, Math.max(Math.max(machine, cup) + a, time));
        /**
         * 不用洗碗机洗
         * machine不变
         * 最后完成时间 = 已知最后完成时间 vs 等这个杯子挥发完的时间 较大的那个
         */
        int res2 = brute(arr, a, b, index + 1, machine, Math.max(time, cup + b));
        return Math.min(res1, res2);
    }

    /**
     * 暴力2：优化版，少了一个可变参数
     * @param arr 准备刷杯子的时间（不变）
     * @param a 洗碗机耗时（不变）
     * @param b 挥发耗时（不变）
     * @param index 遍历的位置（0...index-1的杯子都已经被决定好）
     * @param washLine 洗碗机空闲的时间点
     */
    public static int brute2(int[] arr, int a, int b, int index, int washLine) {
        if (index == arr.length - 1) {
            // 已经到了最后一杯, 直接返回用洗碗机和不用洗碗机里较小的那个值
            return Math.min(Math.max(arr[index], washLine) + a, arr[index] + b);
        }

        // 当前杯子用洗碗机洗完的时间
        int wash = Math.max(washLine, arr[index]) + a;
        // 当前杯子之后(index+1.....n）洗完的最早时间
        int next1 = brute2(arr, a, b, index + 1, wash);
        int p1 = Math.max(wash, next1);

        // 当前杯子挥发干净的结束时间
        int dry = arr[index] + b;
        // 当前杯子之后(index+1.....n）洗完的最早时间
        int next2 = brute2(arr, a, b, index + 1, washLine);
        int p2 = Math.max(dry, next2);

        return Math.min(p1, p2);
    }

    public static int dp(int[] drinks, int a, int b) {
        if (a >= b) {
            // 如果洗的时间比挥发的时间还要长，直接返回最后一杯咖啡+挥发的时间
            return drinks[drinks.length - 1] + b;
        }
        // 计算出我们的极限时间 = 每个杯子都用洗碗机洗（并不是最极限的，但是属于合理的极限时间）
        int N = drinks.length;
        int limit = 0; // 咖啡机什么时候可用
        for (int i = 0; i < N; i++) {
            limit = Math.max(limit, drinks[i]) + a;
        }
        // 创建dp缓存
        int[][] dp = new int[N][limit + 1];

        // base case：N-1行，所有的值
        for (int washLine = 0; washLine <= limit; washLine++) {
            dp[N - 1][washLine] = Math.min(Math.max(washLine, drinks[N - 1]) + a, drinks[N - 1] + b);
        }
        for (int index = N - 2; index >= 0; index--) {
            for (int washLine = 0; washLine <= limit; washLine++) {
                int p1 = Integer.MAX_VALUE;
                int wash = Math.max(washLine, drinks[index]) + a;
                if (wash <= limit) {
                    p1 = Math.max(wash, dp[index + 1][wash]);
                }
                int p2 = Math.max(drinks[index] + b, dp[index + 1][washLine]);
                dp[index][washLine] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }
}
