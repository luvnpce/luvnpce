package datastructure;

import java.util.Arrays;

/**
 * 小根堆：父节点的值小于或等于子节点的值
 * left child = 2*i + 1
 * right child = 2*i + 2
 * parent = (i-1)/2
 */
public class MinHeap {

    private int[] heap;
    private int limit;
    private int heapSize;

    public MinHeap(int limit) {
        this.heapSize = 0;
        this.limit = limit;
        this.heap = new int[limit];
    }

    public boolean isFull() {
        return heapSize == limit;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * 取出最小的数，保证剩余的数还是小根堆
     * @return
     */
    public int pop() {
        int ans = heap[0];
        swap(heap, 0, --heapSize);
        heapify(heap, 0, heapSize);
        return ans;
    }

    /**
     * 插入一个值
     * @param element
     */
    public void push(int element) {
        if (isFull()) {
            throw new RuntimeException("heap is full");
        }
        heap[heapSize] = element;
        heapInsert(heap, heapSize++);
    }

    private void swap(int[] heap, int i, int j) {
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * 插入数在堆的最后，保证小根堆成立
     * O(logN)
     * @param arr
     * @param index
     */
    private void heapInsert(int[] arr, int index) {
        // 和父节点对比
        while (arr[index] < arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 向下比较，跟比自己小的（最小的）child互换位置
     * @param arr
     * @param index
     * @param heapSize
     */
    private void heapify(int[] arr, int index, int heapSize) {
        // 判断有没有children
        int leftIndex = 2 * index + 1;
        while (leftIndex < heapSize) {
            int smallest = leftIndex + 1 < heapSize && arr[leftIndex + 1] < arr[leftIndex] ? leftIndex + 1 : leftIndex;
            if (arr[smallest] >= arr[index]) {
                break;
            }
            swap(arr, index, smallest);
            index = smallest;
            leftIndex = 2 * index + 1;
        }
    }

    public static class RightMinHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMinHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int minIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] <arr[minIndex]) {
                    minIndex = i;
                }
            }
            int ans = arr[minIndex];
            arr[minIndex] = arr[--size];
            return ans;
        }

    }


    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MinHeap my = new MinHeap(curLimit);
            RightMinHeap test = new RightMinHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        int num1 = my.pop();
                        int num2 = test.pop();
                        if (num1 != num2) {
                            System.out.println("xxxOops!" + num1 + ":" + num2);
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

}
