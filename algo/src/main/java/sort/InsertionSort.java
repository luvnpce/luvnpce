package sort;

import java.util.Arrays;

/**
 * 从1到N开始遍历，把值放到左边对应的位置去（好比打牌，每次抓牌把牌放到对应的位置去）
 * [5, 1, 3, 4, 6]
 * [1, 5, 3, 4, 6]
 * [1, 3, 5, 4, 6]
 * [1, 3, 4, 5, 6]
 * [1, 3, 4, 5, 6]
 */
public class InsertionSort {

    public static void main(String[] args) {

        int[] list = {5, 1, 3, 4, 6};
        System.out.println(Arrays.toString(list));
        sort(list);
        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list) {
        // 从1~N开始遍历（摸牌）
        for (int i = 1; i < list.length; i++) {
            // 遍历前面的数（之前摸到的牌），如果前面的数要大于我们则swap
            for (int j = i - 1; j >= 0 && list[j] > list[j + 1]; j--) {
                swap(list, j, j+1);
            }
        }
    }

    private static void swap(int[] list, int i, int j) {
        int tmp = list[j];
        list[j] = list[i];
        list[i] = tmp;
    }
}
