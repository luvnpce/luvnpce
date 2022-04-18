package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/3sum/
 * difficulty: medium
 *
 * Given an integer array nums, return all the triplets
 * [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 */
public class LeetCode_15_3Sum {

    /**
     * 和2Sum双指针的打解法类似
     *
     * 先有一个指针i从左向右遍历，假设当前位置的数nums[i]是三个数的其中之一
     * 那么剩余两个数j，k的累加和必须等于0-nums[i]
     * nums[i] + nums[j] + nums[k] = 0
     * nums[j] + nums[k] = 0 - nums[i]
     */
    public static List<List<Integer>> solution1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // 先排序
        Arrays.sort(nums);
        // i从0开始遍历，这里只遍历到N - 2因为我们后面还要找两个数
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            List<List<Integer>> lists = twoSum(nums, i + 1, target);
            /**
             * 这里可以有个小优化，就是把nums[i]加到0位置，对于arraylist来说耗时会很大
             * 所以可以让i从右往左遍历，这样就可以把nums[i]加到arraylist的尾部
             */
            for (List<Integer> cur : lists) {
                cur.add(0, nums[i]);
                res.add(cur);
            }
        }
        return res;
    }

    /**
     * 从nums[begin.....N]的范围上，找到累加和等于target的二元组
     */
    private static List<List<Integer>> twoSum(int[] nums, int begin, int target) {
        List<List<Integer>> res = new ArrayList<>();

        int left = begin;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left++;
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                if (left == begin || nums[left] != nums[left - 1]) {
                    List<Integer> inner = new ArrayList<>();
                    inner.add(nums[left]);
                    inner.add(nums[right]);
                    res.add(inner);
                }
                left++;
            }
        }
        return res;
    }

}
