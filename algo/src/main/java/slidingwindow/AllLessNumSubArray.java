package slidingwindow;

import utils.ArrayUtils;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num。某个arr中的子数组sub，如果想达标，必须满足以下：
 * - sub中最大值 - sub中最小值 <= num
 * 返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {

    public static void main(String[] args) {
        int value = 100;
        int limit = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayUtils.generate(limit, value);
            int num = 20;
            int num1 = brute(arr, num);
            int num2 = window(arr, num);
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }

    /**
     * 滑动窗口：O(N)
     * =================================================================
     * 假设[L...R]这个子数组是达标的 （max - min <= num)
     * 那么我们可以保证[L'...R']也是达标的（L <= L' && R' <= R]。因为：
     *  - [L'...R']的max肯定比[L...R]要小
     *  - [L'...R']的min肯定比[L...R]要大
     *  - 那么max' - min'的差肯定会更小
     * 相反，如果[L...R]这个子数组不达标，那么不管L和R再往外扩展，那个子数组也不会达标
     * ==================================================================
     * 流程：
     *  1. 准备两个双向队列，一个负责max，一个负责min
     *  2. L和R从0开始，R依次向右扩展，判断[L...R]子数组是否达标，当新加的数会造成不达标时，
     *  停止并计算从0开始的子数组有多少个达标的：R-L
     *  3. 然后L向右移动一格，计算从1开始的子数组有多少达标的
     *  4. 依次遍历
     */
    public static int window(int[] arr, int num) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        int res = 0;
        int left = 0;
        int right = 0;
        while (left < arr.length) {
            while (right < arr.length) {
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[right]) {
                    qmax.pollLast();
                }
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[right]) {
                    qmin.pollLast();
                }
                qmax.addLast(right);
                qmin.addLast(right);
                if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > num) {
                    break;
                }
                right++;
            }
            // 记录达标数量
            res += right - left;
            // 准备移动left，看看将要移除的数是不是最大数或者最小数，是的话从队列里弹出
            if (qmax.peekFirst() == left) {
                qmax.pollFirst();
            }
            if (qmin.peekFirst() == left) {
                qmin.pollFirst();
            }
            // 不用重置right的原因就是，我们缩小窗口只会弹出最大数或者最小数
            // 只可能从不达标 => 达标，不会从达标 => 不达标，所以总体O(N)
            left++;
        }
        return res;
    }

    /**
     * 暴力：O(n^3)
     */
    public static int brute(int[] arr, int num) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int res = 0;
        for (int left = 0; left < arr.length; left++) {
            for (int right = left; right < arr.length; right++) {
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int i = left; i <= right; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                res += (max - min) <= num ? 1 : 0;
            }
        }
        return res;
    }
}
