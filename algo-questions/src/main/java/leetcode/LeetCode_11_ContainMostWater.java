package leetcode;

/**
 * https://leetcode.com/problems/container-with-most-water/
 * difficulty: medium
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints
 * of the ith line are (i, 0) and (i, height[i]).
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * Return the maximum amount of water a container can store.
 *
 *  Notice that you may not slant the container.
 */
public class LeetCode_11_ContainMostWater {

    public static int brute(int[] arr) {
        int max = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                max = Math.max(max, Math.min(arr[i], arr[j]) * (j - i));
            }
        }
        return max;
    }

    public static int optimize(int[] arr) {
        int max = 0;
        int l = 0;
        int r = arr.length - 1;
        while (l < r) {
            max = Math.max(max, Math.min(arr[l], arr[r]) * (r - l));
            if (arr[l] < arr[r]) {
                l++;
            } else {
                r++;
            }
        }
        return max;
    }


}
