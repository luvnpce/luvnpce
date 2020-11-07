package trivia;

/**
 * 给定一个数组，调整整个数组，使奇数都在奇数位，偶数都在偶数位
 */
public class EvenOddArray {

    public static void main(String[] args) {
        int[] arr = { 0, 1, 3, 2, 2, 4, 4, 5, 5,7 };
        reorder(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }

    }

    public static void reorder(int[] arr) {
        if (arr == null || arr.length <= 1) {
            System.out.println("the array is null");
            return;
        }
        int n = arr.length;
        int even = 0;
        int odd = 1;
        while (even < n && odd < n) {
            int last = arr[n - 1];
            if (last % 2 == 0) {
                swap(arr, even, n - 1);
                even += 2;
            } else {
                swap(arr, odd, n - 1);
                odd += 2;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
