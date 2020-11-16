package sort;

import utils.ArrayUtils;

/**
 * 给定一个数组，统计数组中有多少对[x,y]符合 x > y*2
 */
public class MergeSort_TwiceAsBig {

    public static void main(String[] args) {
        System.out.println("Start");
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[] arr = ArrayUtils.generate(5, 200);
            int brute = brute(arr);
            int process = process(arr, 0, arr.length - 1);
            if (brute != process) {
                System.out.println("Oops, brute: " + brute + " process: " + process);
                return;
            }
        }
        System.out.println("Done");
    }

    /**
     * 暴力解
     */
    public static int brute(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }


    /**
     * 归并排序解法
     */
    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return process(arr, left, mid) + process(arr, mid + 1, right) + merge(arr, left, mid, right);
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        // 先计算有多少对[x,y]符合条件
        int ans = 0;
        int pos = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (pos <= right && arr[i] > (arr[pos] << 1)) {
                pos++;
            }
            ans += pos - (mid + 1);
        }
        // 再排序
        int pos1 = left;
        int pos2 = mid + 1;
        int[] helper = new int[right - left + 1];
        int i = 0;
        while (pos1 <= mid && pos2 <= right) {
            helper[i++] = arr[pos1] <= arr[pos2] ? arr[pos1++] : arr[pos2++];
        }
        while (pos1 <= mid) {
            helper[i++] = arr[pos1++];
        }
        while (pos2 <= right) {
            helper[i++] = arr[pos2++];
        }
        for (int j = 0; j < helper.length; j++) {
            arr[left + j] = helper[j];
        }
        return ans;
    }
}
