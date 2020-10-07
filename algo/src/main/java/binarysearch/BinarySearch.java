package binarysearch;

import utils.RandomArrayGenerator;

import java.util.Arrays;

public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generateSorted(50, 100);
        int num = 15;
        System.out.println(Arrays.toString(arr));
        System.out.println(num);
        System.out.println(search(arr, num));
    }

    private static boolean search(int[] arr, int num) {
        if (null == arr || arr.length == 0) {
            return false;
        }
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] == num) {
                return true;
            } else if (num < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return arr[left] == num;
    }
}
