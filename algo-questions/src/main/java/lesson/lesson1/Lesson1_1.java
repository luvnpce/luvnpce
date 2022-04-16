package lesson.lesson1;

/**
 * 给定一个有序数组arr，代表坐落在X轴上的点,给定一个正数K，代表绳子的长度,返回绳子最多压中几个点
 */
public class Lesson1_1 {

    public static void main(String[] args) {
        int[] arr = {1,3,4,7,13,16,17};
        System.out.println(brute(arr, 10));
        System.out.println(window(arr, 10));
    }

    public static int window(int[] arr, int k) {
        int left = 0;
        int right = 0;
        int length = arr.length;
        int result = 0;
        while (left < length) {
            while (right < length && arr[right] - arr[left] <= k) {
                right++;
            }
            result = Math.max(result, right - (left++));
        }
        return result;
    }

    public static int brute(int[] arr, int k) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            int temp = 0;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] - arr[i] <= k) {
                    temp++;
                } else {
                    break;
                }
            }
            res = Math.max(res, temp);
        }
        return res;
    }
}
