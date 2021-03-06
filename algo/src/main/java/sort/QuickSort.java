package sort;

import utils.ArrayUtils;

import java.util.Arrays;

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

    private static int[] partition(int[] arr, int left, int right) {
        if (left == right) {
            return new int[] {left, right};
        }
        int val = arr[right];
        int less = left - 1;
        int more = right;
        int i  = left;
        while (i < more) {
            if (arr[i] < val) {
                swap(arr, i++, ++less);
            }
            else if (arr[i] == val) {
                i++;
            } else {
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
