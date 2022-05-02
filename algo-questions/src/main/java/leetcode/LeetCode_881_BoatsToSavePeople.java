package leetcode;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/boats-to-save-people/
 * difficulty: medium
 *
 * You are given an array people where people[i] is the weight of the ith person,
 * and an infinite number of boats where each boat can carry a maximum weight of limit.
 * Each boat carries at most two people at the same time,
 * provided the sum of the weight of those people is at most limit.
 *
 * Return the minimum number of boats to carry every given person.
 */
public class LeetCode_881_BoatsToSavePeople {

    /**
     * O(nlogn)
     */
    public static int solution(int[] arr, int limit) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        // 先排序
        Arrays.sort(arr);
        // 如果最重的人大于载重，此题无解
        if (arr[arr.length - 1] > limit) {
            return -1;
        }

        int ans = 0;
        int left = 0;
        int right = arr.length - 1;
        int sum = 0;
        while (left <= right) {
            sum = left == right ? arr[left] : arr[left] + arr[right];
            if (sum > limit) {
                right--;
            } else {
                left++;
                right--;
            }
            ans++;
        }
        return ans;
    }


}
