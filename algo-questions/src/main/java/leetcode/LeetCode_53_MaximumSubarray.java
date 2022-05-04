package leetcode;

/**
 * https://leetcode.com/problems/maximum-subarray/
 * difficulty: easy
 *
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * A subarray is a contiguous part of an array.
 */
public class LeetCode_53_MaximumSubarray {

    /**
     * 从左向右遍历，在i位置时，考虑以i位置数结尾的最大累加和
     */
    public static int solution(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int pre = arr[0];
        int ans = arr[0];
        /**
         * 两种可能
         * 1、 上一个以i-1位置结尾的累加和是负数，那么就只用arr[i] = 累加和
         * 2、 上一个以i-1位置结尾的累加和是正数，那么就只用arr[i] + pre = 累加和
         */
        for (int i = 1; i < arr.length; i++) {
            pre = Math.max(arr[i], arr[i] + pre);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
