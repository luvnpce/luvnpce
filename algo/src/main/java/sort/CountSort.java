package sort;

import utils.RandomArrayGenerator;

import java.util.Arrays;

/**
 * 桶排序之计数排序
 * - 用一个有序的容器来记录哪些数字出现过，然后再有序的遍历那个容器，把数字倒出来
 */
public class CountSort {

    public static void main(String[] args) {
//        int[] list = {5, 1, 3, 4, 6};
        int[] list = RandomArrayGenerator.generateAbs(10, 100);
        System.out.println(Arrays.toString(list));
        sort(list);
        System.out.println(Arrays.toString(list));
    }

    // only for 0~200 value
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int[] bucket = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }


}
