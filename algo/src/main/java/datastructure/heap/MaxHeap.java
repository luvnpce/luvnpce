package datastructure.heap;

/**
 * 大根堆：父节点的值大于或等于子节点的值
 * left child = 2*i + 1
 * right child = 2*i + 2
 * parent = (i-1)/2
 */
public class MaxHeap {

    private int[] heap;
    private int limit;
    private int heapSize;

    public MaxHeap(int limit) {
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
     * 取出最大的数，保证剩余的数还是大根堆
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
     * 插入数在堆的最后，保证大根堆成立
     * O(logN)
     * @param arr
     * @param index
     */
    private void heapInsert(int[] arr, int index) {
        // 和父节点对比
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 向下比较，跟比自己大的（最大的）child互换位置
     * @param arr
     * @param index
     * @param heapSize
     */
    private void heapify(int[] arr, int index, int heapSize) {
        // 判断有没有children
        int leftIndex = 2 * index + 1;
        while (leftIndex < heapSize) {
            int largest = leftIndex + 1 < heapSize && arr[leftIndex + 1] > arr[leftIndex] ? leftIndex + 1 : leftIndex;
            if (arr[largest] <= arr[index]) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            leftIndex = 2 * index + 1;
        }
    }

    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
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
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MaxHeap my = new MaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
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
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
