package datastructure.binarytree;

import utils.ArrayUtils;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        int[] arr = ArrayUtils.generate(20, 100);
        System.out.println(Arrays.toString(arr));
        test.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        int size = arr.length;
        swap(arr, 0, --size);
        while (size > 1) {
            heapify(arr, 0, size);
            swap(arr, 0, --size);
        }
    }

    private void heapify(int[] arr, int i, int size) {
        int left = 2*i+1;
        while (left < size) {
            int largest = left + 1 < size && arr[left+1] > arr[left] ? left+1 : left;
            if (arr[i] >= arr[largest]) {
                break;
            }
            swap(arr, i, largest);
            i = largest;
            left = 2*i+1;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
