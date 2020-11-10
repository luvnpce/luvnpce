package dp;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number)
 * which has the largest sum and return its sum.
 *
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 */
public class MaxSubArray {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int[] arr = ArrayUtils.generate(20, 100);
            if (brute(arr) != brute2(arr)) {
                System.out.println("Oops!");
                System.out.println(Arrays.toString(arr));
            }
        }
    }

    public static int brute(int[] arr) {
        if (arr.length == 1) {
            return arr[0];
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            max = Math.max(max, num);
            for (int j = i + 1; j < arr.length; j++) {
                num += arr[j];
                max = Math.max(max, num);
            }
        }
        return max;
    }

    public static int brute2(int[] arr) {
        if (arr.length == 1) {
            return arr[0];
        }
        int max = arr[0];
        int num = arr[0];
        for (int i = 1; i < arr.length; i++) {
            num = Math.max(arr[i], (num + arr[i]));
            max = Math.max(max, num);
        }
        return max;
    }

}
