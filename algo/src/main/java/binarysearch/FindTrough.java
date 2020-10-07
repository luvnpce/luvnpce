package binarysearch;

import java.util.Arrays;

/**
 * 找寻一个局部最小
 * 1. 如果arr[0] < arr[1]，那么arr[0]就是局部最小
 * 2. 如果arr[N-1] < arr[N-2]，那么arr[N-1]就是局部最小
 * 3. 如果arr[x-1] > arr[x] < arr[x+1]，那么arr[x]就是局部最小
 */
public class FindTrough {

    public static void main(String[] args) {
        int[] arr = {4, 3, 2, 1, 7, 6, 5, 8};
        System.out.println(Arrays.toString(arr));
        int res = search(arr);
        System.out.println(res);
    }

    public static int search(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        // 边界检查
        if (null == arr || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[left] < arr[left + 1]) {
            return 0;
        }
        if (arr[right] < arr[right - 1]) {
            return right;
        }
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
                return mid;
            } else if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}
