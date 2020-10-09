package sort;

/**
 * 在一个数组中，一个数左边比它小的数的总和，叫作数的小和。所有数的小和累加起来，叫做数组小和。求数组小和
 * [1,3,4,2,5]
 * 1的小和 = 0
 * 3的小和 = 1
 * 4的小和 = 1 + 3
 * 2的小和 = 1
 * 5的小和 = 1 + 3 + 4 + 2
 * 数组的小和 = 16
 */
public class MergeSmallSum {

    public static void main(String[] args) {
        int[] arr = new int[] {1, 3, 4, 2, 5};
        int sum = sum(arr, 0, arr.length - 1);
        System.out.println(sum);
    }

    private static int sum(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return sum(arr, left, mid) + sum(arr, mid + 1, right) + merge(arr, left, mid, right);
    }

    /**
     * 两个数组比较, 且这两个数组都是有序的（因为之前的递归方法帮我们排序了）
     * A[left，mid]
     * B[mid+1, right]
     * - 如果A[i]的值比B[j]的值要小，那么A[i]就会出现在B[j]的小和里
     * - 并且，B[j+1,j+2....J]都是比B[j]要大的，所以A[i]也会出现在他们的小和里
     * - 所以总的小和 += A[i] * (J-j+1)，然后依次遍历A[]
     * - 同时我们也要把两个数组归并排序
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @return
     */
    private static int merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int pos1 = left;
        int pos2 = mid + 1;
        int sum = 0;
        while (pos1 <= mid && pos2 <= right) {
            if (arr[pos1] < arr[pos2]) {
                sum += (arr[pos1] * (right - pos2 + 1));
                help[i++] = arr[pos1++];
            } else {
                help[i++] = arr[pos2++];
            }
        }
        while (pos1 <= mid) {
            help[i++] = arr[pos1++];
        }
        while (pos2 <= right) {
            help[i++] = arr[pos2++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
        return sum;
    }
}
