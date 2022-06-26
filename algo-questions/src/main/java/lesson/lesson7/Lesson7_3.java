package lesson.lesson7;

/**
 * 给定一个有序数组arr，其中值可能为正、负、0
 * 返回arr中每个数平方之后不同的结果有多少种
 */
public class Lesson7_3 {

    /**
     * 解法
     * 双指针，对比两个指针对应的数的绝对值，哪个大哪个指针就移动
     * **注意，有可能数组里有相同的数，那么指针每次在移动时要跳过这些相同的数
     */
    public static int solution(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }
        int n = arr.length;
        int left = 0;
        int right = n - 1;

        int res = 0;
        while (left <= right) {
            int leftAbs = Math.abs(arr[left]);
            int rightAbs = Math.abs(arr[right]);
            if (leftAbs > rightAbs) {
                // case 1：left比right大
                while (left < n && Math.abs(arr[left]) == leftAbs) {
                    left++;
                }
            } else if (leftAbs < rightAbs) {
                // case 2：left比right小
                while (right >= 0 && Math.abs(arr[right]) == rightAbs) {
                    right--;
                }
            } else {
                // case 3：两个数的绝对值相同
                while (left < n && Math.abs(arr[left]) == leftAbs) {
                    left++;
                }
                while (right >= 0 && Math.abs(arr[right]) == rightAbs) {
                    right--;
                }
            }
            res++;
        }
        return res;
    }

}
