package leetcode;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/two-sum/
 * difficulty: easy
 *
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 */
public class LeetCode_1_2Sum {

    public int[] unsortedArray(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] result = null;
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                result = new int[] {i, map.get(target - nums[i])};
                break;
            }
            map.put(nums[i], i);
        }
        return result;
    }

    /**
     * 如果数组已经是有序的，直接用双指针，可以节省使用hashmap的空间
     */
    public int[] sortedArray(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        /**
         * left指向数组最左侧，right指向最右侧
         * nums[left] + nums[right]，如果
         * 1：sum < target，left++，因为nums已经排序过
         * 2：sum > target，right--
         * 3：sum == target，return
         */
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left++;
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                break;
            }
        }
        return new int[] {left, right};
    }
}
