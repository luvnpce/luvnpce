package sort;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 从N，N-1，N-2...到1遍历，每次找出范围内最大的值放在最后
 * [5, 1, 3, 4, 6]
 * [4, 1, 3, 5, 6]
 * [3, 1, 4, 5, 6]
 * [1, 3, 4, 5, 6]
 */
public class BubbleSort {

    public static void main(String[] args) {
//        int[] list = {5, 1, 3, 4, 6};
        int[] list = ArrayUtils.generate(10, 100);
        System.out.println(Arrays.toString(list));
        sort(list);
        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list) {
        for (int i = list.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (list[j] > list[i]) {
                    swap(list, j, i);
                }
            }
//            System.out.println(Arrays.toString(list));
        }
    }

    private static void swap(int[] list, int i, int j) {
        int tmp = list[j];
        list[j] = list[i];
        list[i] = tmp;
    }
}
