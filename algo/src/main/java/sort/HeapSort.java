package sort;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {5, 1, 3, 4, 6};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        // 先遍历一遍，然后每次使用heapInsert，保证最终是大根堆
//        for (int i = 0; i < arr.length; i++) {  // O(N)
//            heapInsert(arr, i);                 // O(logN)
//        }

        // 优化版的遍历 O(N)
        for (int i = arr.length - 1; i >= 0 ; i--) {
            heapify(arr, i, arr.length);
        }

        // 逐个把MaxHeap里面的头部（最大的）移除来放到arr最后
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    private static void heapInsert(int[] arr, int index) {
        if (index == 0) {
            return;
        }
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private static void heapify(int[] arr, int index, int heapSize) {
        int leftIndex = 2 * index + 1;
        while (leftIndex < heapSize) {
            int largest = leftIndex + 1 < heapSize && arr[leftIndex + 1] > arr[leftIndex] ? leftIndex + 1 : leftIndex;
            if (arr[largest]  <= arr[index]) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            leftIndex = 2 * index + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
