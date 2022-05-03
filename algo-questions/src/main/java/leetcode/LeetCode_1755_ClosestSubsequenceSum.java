package leetcode;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/closest-subsequence-sum/
 * difficulty: hard
 *
 * You are given an integer array nums and an integer goal.
 * You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal.
 * That is, if the sum of the subsequence's elements is sum, then you want to minimize the absolute difference abs(sum - goal).
 * Return the minimum possible value of abs(sum - goal).
 * Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the original array.
 *
 * ie:
 * Input: nums = [5,-7,3,5], goal = 6
 * Output: 0
 * Explanation: Choose the whole array as a subsequence, with a sum of 6.
 * This is equal to the goal, so the absolute difference is 0.
 *
 * Input: nums = [7,-9,15,-2], goal = -5
 * Output: 1
 * Explanation: Choose the subsequence [7,-9,-2], with a sum of -4.
 * The absolute difference is abs(-4 - (-5)) = abs(1) = 1, which is the minimum.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 40
 * -107 <= nums[i] <= 107
 * -109 <= goal <= 109
 * 注：通过这个数据量描述可知，需要用到分治，因为数组长度不大
 * 但是每个数的值很大，如果用动态规划，表会爆
 */
public class LeetCode_1755_ClosestSubsequenceSum {

    public static int[] l = new int[1 << 20];
    public static int[] r = new int[1 << 20];

    /**
     * 因为用分治，所以把nums拆成两组，每组各20个数
     * 然后分别去计算这两组的所有累加和
     * 计算完后，再去把这两组累加和 遍历和goal做比较，选差值最小的
     */
    public static int solution(int[] nums, int goal) {
        if (null == nums || nums.length == 0) {
            return goal;
        }
        int lEnd = process(nums, 0, nums.length >> 1, 0, 0, l);
        int rEnd = process(nums, nums.length >> 1, nums.length, 0, 0, r);

        Arrays.sort(l, 0, lEnd);
        Arrays.sort(l, 0, rEnd);
        int ans = Math.abs(goal);
        // 遍历l里面的每个累加和
        for (int i = 0; i < lEnd; i++) {
            // 先计算出和goal之间的差
            int rest = goal - l[i];
            // 再大到小遍历r里面的累加和
            // 如果l[i] + r[rEnd]和goal之间的绝对差 大于 l[i] + r[rEnd -1]的绝对差，那么就继续遍历，否则就代表已经找到l[i]下的最优解
            while (rEnd > 0 && Math.abs(rest - r[rEnd - 1]) <= Math.abs(rest - r[rEnd])) {
                rEnd--;
            }
            ans = Math.min(ans, Math.abs(rest - r[rEnd]));
        }
        return ans;
    }

    /**
     * 计算累加和
     */
    private static int process(int[] nums, int index, int end, int sum, int fill, int[] arr) {
        if (index == end) {
            // 已经遍历到结尾了，把当前累加和记录下来
            arr[fill++] = sum;
        } else {
            // 不用当天数
            fill = process(nums, index + 1, end, sum, fill, arr);
            // 用当前数
            fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
        }
        // 这个是记录数组里一共有多少个累加和
        return fill;
    }

}
