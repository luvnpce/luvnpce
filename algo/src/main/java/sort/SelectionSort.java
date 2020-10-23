package sort;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 从0~N开始遍历，每次取出最小的值
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] list = ArrayUtils.generate(20, 100);
        System.out.println(Arrays.toString(list));
        sort(list);
        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list) {
        for (int i = 0; i < list.length; i++) {
            int minPos = i;
            for (int j = i + 1; j < list.length; j++) {
                minPos = list[j] < list[minPos] ? j : minPos;
//                if (list[j] < list[i]) {
//                    swap(list, i, j);
//                }
            }
            swap(list, i, minPos);
        }
    }

    private static void swap(int[] list, int i, int j) {
        int tmp = list[j];
        list[j] = list[i];
        list[i] = tmp;
    }
}
