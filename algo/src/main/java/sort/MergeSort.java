package sort;

import utils.RandomArrayGenerator;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
//        int[] list = {-8, -32, 71, 9, -82, 7, -39, -24, -2, -2, -29, 6};
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
                // 主要防止超出Integer最大值
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

    /**
     * 使用了一个临时数组
     * @param list
     * @param left
     * @param mid
     * @param right
     */
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

    /**
     * in place merge
     * @param list
     * @param left
     * @param mid
     * @param right
     */
    private static void merge2(int[] list, int left, int mid, int right) {
        int pos1 = left;
        int pos2 = mid + 1;
        while (pos1 <= mid && pos2 <= right) {
            if (list[pos1] <= list[pos2]) {
                pos1++;
            } else {
                // 我们发现了pos2的值比pos1的要小，所以我们得把pos1到pos2的值都向右移动一位，然后把pos2的值放到pos1
                int tmp = list[pos2];
                int i = pos2;
                while (i > pos1) {
                    list[i] = list[i - 1];
                    i--;
                }
                list[pos1] = tmp;
                pos1++;
                pos2++;
                // 这里mid需要++是因为，我们整体向右移动了一格，所以原mid的值被移动到了mid+1，所以要mid++来确保我们最后能遍历到那个值
                mid++;
            }
        }


    }
}
