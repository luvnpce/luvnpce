package test;

import utils.ArrayUtils;

import java.util.Arrays;

public class RedixSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generateAbs(10, 100);
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1, maxDigits(arr));
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr, int left, int right, int maxDigits) {
        // bucket[0....9]
        int[] tmp = new int[right - left + 1];

        // 从个位数到最高位数，一次进行基数排序
        for (int i = 1; i <= maxDigits; i++) {
            int[] bucket = new int[10];
            // 统计该位置上每个数出现的次数
            for (int j = 0; j < arr.length; j++) {
                int digit = getDigit(arr[j], i);
                bucket[digit]++;
            }
            // 计算prefix sum
            for (int j = 1; j < bucket.length; j++) {
                bucket[j] = bucket[j] + bucket[j - 1];
            }
            // 排序
            for (int j = right; j >= left; j--) {
                int digit = getDigit(arr[j], i);
                int index = bucket[digit] - 1;
                tmp[index] = arr[j];
                bucket[digit]--;
            }
            for (int j = left, k = 0; j <= right; j++, k++) {
                arr[j] = tmp[k];
            }
        }
    }

    // 1 = 个位，2 = 百位， 3 = 千位
    public static int getDigit(int num, int offset) {
        int factor = (int) Math.pow(10, offset - 1);
        num = num / factor;
        return num % 10;
    }

    public static int maxDigits(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int res = 0;
        while (max > 0) {
            max = max / 10;
            res++;
        }
        return res;
    }
}
