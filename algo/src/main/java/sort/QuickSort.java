package sort;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 原理：选最右边的数x，让数组里小于等于x的放左边，大于x放右边
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generate(20, 100);
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        int[] equalArea = partition(arr, left, right);
        sort(arr, left, equalArea[0] - 1);
        sort(arr, equalArea[1] + 1, right);
    }

    /**
     * 返回一个range[]
     * range[0] = 等于pivot范围的第一个位置
     * range[1] = 等于pivot范围的最后一个位置
     */
    private static int[] partition(int[] arr, int left, int right) {
        if (left == right) {
            return new int[] {left, right};
        }
        int val = arr[right]; // pivot选最右的一个数
        int less = left - 1; // 小于pivot的位置，从最左侧-1开始
        int more = right;   // 大于pivot的位置，从pivot当前位置开始，也就是最右侧
        int i  = left;      // 当前位置i
        while (i < more) {
            if (arr[i] < val) {
                /**
                 * 当前位置的数 < pivot，那么less的范围扩大一格 ++less
                 * arr[i]和arr[less]交换
                 * i向右移动
                 */
                swap(arr, i++, ++less);
            }
            else if (arr[i] == val) {
                /**
                 * 当前位置的数 == pivot
                 * i向右移动
                 */
                i++;
            } else {
                /**
                 * 当前位置的数 > pivot
                 * 那么more的范围扩大一格，--more
                 * 注意，i的位置不移动，因为交换过来的数还没有校验
                 */
                swap(arr, i, --more);
            }
        }
        swap(arr, more, right);
        return new int[] {less + 1, more};
    }

    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[b];
        arr[b] = arr[a];
        arr[a] = tmp;
    }
}
