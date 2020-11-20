package slidingwindow;

import java.util.HashMap;

/**
 * 给定一个只有正数的数组，和一个sum值，找出子数组的累加和等于sum的最长子数组（长度）
 */
public class LongestSumSubArray {

    public static void main(String[] args) {

    }

    /**
     * 因为给定的数组是只包含正数的（0除外），那么这个数组就具有单调性
     *  - right往右扩，[left...right]的sum上升
     *  - left往右扩，[left...right]的sum下降
     */
    public static int slidingWindow(int[] arr, int sum) {
        if (null == arr || arr.length == 0 || sum <= 0) {
            return 0;
        }
        int windowSum = arr[0];
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < arr.length) {
            if (windowSum == sum) {
                // 找到一个答案，看看是否是目前最长
                res = Math.max(res, right - left + 1);
                // left往右扩，windowSum更新
                windowSum -= arr[left++];
            } else if (windowSum < sum) {
                right++;
                if (right == arr.length) {
                    break;
                }
                windowSum += arr[right];
            } else {
                windowSum -= arr[left--];
            }
        }
        return res;
    }

    /**
     * 适用于数组里包含负数和0的场景
     */
    public static int prefixSum(int[] arr, int sum) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        // map用来记录prefix sum，但是只记录prefix sum第一次出现的位置
        // 假设i = 3, i = 10的时候，prefix sum都是5，那么map里面只会记录3的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int res = 0;
        int prefixSum = 0;
        for (int i = 0; i < arr.length; i++) {
            prefixSum += arr[i];
            if (map.containsKey(prefixSum - sum)) {
                Integer index = map.get(prefixSum - sum);
                res = Math.max(res, i - index);
            }
            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            }
        }
        return res;
    }
}
