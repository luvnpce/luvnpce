package lesson.lesson3;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 给定一个数组arr，代表每个人的能力值。再给定一个非负数k
 * 如果两个人的能力差值正好为k，那么可以凑在一起比赛
 * 一局比赛只有两个人
 * 返回最多可以同时有多少场比赛
 */
public class Lesson3_2 {

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int maxK = 5;
        int testTime = 100;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * (maxLen + 1));
            int[] arr = ArrayUtils.generate(N, maxValue);
            int[] arr1 = ArrayUtils.copyArray(arr);
            int[] arr2 = ArrayUtils.copyArray(arr);
            int k = (int) (Math.random() * (maxK + 1));
            int ans1 = brute(arr1, k);
            int ans2 = slidingWindow(arr2, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

    /**
     * 滑动窗口解法
     */
    public static int slidingWindow(int[] arr, int k) {
        if (k < 0 || null == arr || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        int L = 0;
        int R = 0;
        int N = arr.length;
        boolean[]  used = new boolean[N];

        Arrays.sort(arr);
        while (L < N && R < N) {
            if (used[L]) {
                L++;
            } else if (L >= R) {
                R++;
            } else {
                if (arr[R] - arr[L] == k) {
                    ans++;
                    used[R] = true;
                    R++;
                    L++;
                } else {
                    if (arr[R] - arr[L] < k) {
                        R++;
                    } else {
                        L++;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 暴力解，全排列，找符合差值=k
     * 体系学习班 17节（认识一些经典递归过程）
     * ===================================
     * i从0到N
     * 递归方法，在位置i上面尝试和后面每一个数就交换一次 == 得到全排列
     * 然后计算这一次排列下 符合差值k的配对数量
     */
    public static int brute(int[] arr, int k) {
        if (k < 0) {
            return -1;
        }
        return doBrute(arr, 0, k);
    }

    public static int doBrute(int[] arr, int index, int k) {
        int ans = 0;
        if (index == arr.length) {
            for (int i = 1; i < arr.length; i += 2) {
                if (arr[i] - arr[i - 1] == k) {
                    ans++;
                }
            }
        } else {
            for (int i = index; i < arr.length; i++) {
                swap(arr, index, i);
                ans = Math.max(ans, doBrute(arr, index + 1, k));
                swap(arr, index, i);
            }
        }
        return ans;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
