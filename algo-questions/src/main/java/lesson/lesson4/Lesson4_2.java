package lesson.lesson4;

/**
 * 返回一个数组中，子数组最大累加和
 */
public class Lesson4_2 {

    /**
     * 从左向右遍历，在i位置时，考虑以i位置数结尾的最大累加和
     */
    public static int solution(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int pre = arr[0];
        int ans = arr[0];
        /**
         * 两种可能
         * 1、 上一个以i-1位置结尾的累加和是负数，那么就只用arr[i] = 累加和
         * 2、 上一个以i-1位置结尾的累加和是正数，那么就只用arr[i] + pre = 累加和
         */
        for (int i = 1; i < arr.length; i++) {
            pre = Math.max(arr[i], arr[i] + pre);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
