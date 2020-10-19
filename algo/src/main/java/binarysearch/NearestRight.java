package binarysearch;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 在有序数组中，找出<=value最右侧的位置
 * ie: 123334456789, value = 3
 * 最右侧位置（index）= 4
 */
public class NearestRight {

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generateSorted(20, 100);
        System.out.println(Arrays.toString(arr));
        int value = 29;
        System.out.println(value);
        int res = search(arr, value);
        System.out.println(res);
    }

    private static int search(int[] arr, int num) {
        int left = 0;
        int right = arr.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] <= num) {
                index = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }
}
