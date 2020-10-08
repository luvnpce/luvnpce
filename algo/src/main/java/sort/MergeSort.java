package sort;

import utils.RandomArrayGenerator;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
//        int[] list = {5, 1, 3, 4, 6};
        int[] list = RandomArrayGenerator.generate(20, 100);
        System.out.println(Arrays.toString(list));
//        sort(list, 0, list.length - 1);
        sort2(list);
        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        sort(list, left, mid);
        sort(list, mid + 1, right);
        merge(list, left, mid, right);
    }

    private static void sort2(int[] list) {
        int k = 1;
        while (k < list.length) {
            System.out.println(k);
            int left = 0;
            while (left < list.length) {
                int mid = left + k - 1;
                if (mid >= list.length) {
                    break;
                }
                int right = Math.min(mid + k, list.length - 1);
                merge(list, left, mid, right);
                left = right + 1;
            }
            if (k > list.length / 2) {
                break;
            }
            k <<= 1;
        }
    }

//    private static void sort2(int[] list) {
//        int k = 2;
//        while (k < list.length * 2) {
//            System.out.println(k);
//            int left = 0;
//            int right = Math.min(k - 1, list.length - 1); // 7
//            while (left < list.length - 1) {
//                int mid = left + (k / 2) - 1;
//                if (mid >= list.length) {
//                    break;
//                }
//                merge(list, left, mid, right);
//                left = right + 1;
//                right = Math.min(right + k, list.length - 1);
//            }
//            System.out.println(Arrays.toString(list));
//            k <<= 1;
//        }
//    }

    private static void merge(int[] list, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int pos1 = left;
        int pos2 = mid + 1;
        while (pos1 <= mid && pos2 <= right) {
            help[i++] = list[pos1] <= list[pos2] ? list[pos1++]  : list[pos2++];
        }
        while (pos1 <= mid) {
            help[i++] = list[pos1++];
        }
        while (pos2 <= right) {
            help[i++] = list[pos2++];
        }
        for (int j = 0; j < help.length; j++) {
            list[left + j] = help[j];
        }
    }
}
