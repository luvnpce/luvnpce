package sort;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 桶排序之基数排序（只针对非负数）
 * 1.准备0~9，共10个桶（先进先出），然后从个位数到最高位数开始，遍历整个数组
 * 2.位数值为多少就进哪个桶
 * 3.遍历完后，开始遍历每个桶，把数依次倒出到一个数组
 * 4.进一位数，然后重复1~3步。最后倒出的数组就是已排序过的
 *
 * 逻辑流程
 * 3 56 17 100              降序的话流程大致一样，只不过是从后往前倒出
 * 个位：100 3 56 17           17 56 3 100
 * 十位：100 3 17 56           56 17 3 100
 * 百位：3 17 56 100           100 56 17 3
 *
 * 代码流程
 * 3 56 17 100
 * ===============个位=====================
 * count  = [1, 0, 0, 1, 0, 0, 1, 1, 0, 0]
 * count' = [1, 1, 1, 2, 2, 2, 3, 4, 4, 4]
 * help   = [100, 3, 56, 17]
 * ===============十位=====================
 * count  = [2, 1, 0, 0, 0, 1, 0, 0, 0, 0]
 * count' = [2, 3, 3, 3, 3, 4, 4, 4, 4, 4]
 * help   = [100, 3, 17, 56]
 * ===============百位=====================
 * count  = [3, 1, 0, 0, 0, 0, 0, 0, 0, 0]
 * count' = [3, 4, 4, 4, 4, 4, 4, 4, 4, 4]
 * help   = [3, 17, 56, 100]
 */
public class RedixSort {

    public static void main(String[] args) {
//        int[] list = {5, 1, 3, 4, 6};
        int[] arr = ArrayUtils.generateAbs(10, 100);
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }
        doSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * @param arr
     * @param left
     * @param right
     * @param maxBits 最大位数
     */
    private static void doSort(int[] arr, int left, int right, int maxBits) {
        final int radix = 10;
        int i = 0, j = 0;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[right - left + 1];

        for (int d = 1; d <= maxBits; d++) {
            int[] count = new int[radix];
            // 开始遍历数组里的数，得出在这个位数上，0~9里每个数出现过几次
            for (i = left; i <= right; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            // 得出在这个位数上，i = 0~9, 小于等于i的数出现过几次
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i-1];
            }
            // 根据上面的count，开始重新排序
            for (i = right; i >= left; i--) {
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            for (i = left, j = 0; i <= right; i++, j++) {
                arr[i]  = help[j];
            }
        }

        
    }

    /**
     * 找出位数上的值
     * @param num
     * @param digit 位数（1：个位 2：十位 3：百位...）
     * @return
     */
    private static int getDigit(int num, int digit) {
        return ((num / ((int) Math.pow(10, digit - 1))) % 10);
    }

    /**
     * 找出最大位数
     * @param arr
     * @return
     */
    private static int maxBits(int[] arr) {
        // 先找出数组里最大的数
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        int maxBits = 0;
        while (max > 0) {
            max /= 10;
            maxBits++;
        }
        return maxBits;
    }


}
