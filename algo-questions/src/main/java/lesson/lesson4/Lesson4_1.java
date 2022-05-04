package lesson.lesson4;

import utils.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数组为{3,2,2,3,1}，查询为(0,3,2)
 * 意思是在数组下标0~3这个范围上，有几个2？
 * 假设给你一个数组arr
 * 对这个数组的查询非常频繁，都给出来
 * 请返回所有查询结果
 */
public class Lesson4_1 {

    /**
     * 优化版
     * 通过一个数据结构，来记录在nums数组里面的每个数，分别在哪些坐标上出现过
     * 然后根据给的left到right的范围做二分
     */
    public static int optimal(int[] nums, int left, int right, int target) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], (key) -> new ArrayList<>());
            map.get(nums[i]).add(i);
        }
        if (!map.containsKey(target)) {
            return 0;
        }
        List<Integer> positions = map.get(target);
        // 找出在left位置之前有多少个target
        int l = findLess(positions, left);
        // 找出在right + 1位置之前有多少个target
        int r = findLess(positions, right + 1);
        // left到right范围内有多少个target = r - l
        return r - l;
    }

    private static int findLess(List<Integer> positions, int index) {
        int L = 0;
        int R = positions.size() - 1;
        int ans = 0;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (positions.get(mid) == index) {
                // 相等，代表mid前面的都是左边界外的
                return mid;
            } else if (positions.get(mid) < index) {
                // 代表至少有mid个，因为mid坐标从0开始，所以要+1
                ans = mid + 1;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 暴力解法
     * 根据给的left到right的范围
     * 从left -> right遍历
     */
    public static int brute(int[] nums, int left, int right, int target) {
        int ans = 0;
        for (int i = left; i <= right ; i++) {
            if (nums[i] == target) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 100;
        int queryTimes = 500;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayUtils.generate(len, value);
            int N = arr.length;
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (brute(arr, L, R, v) != optimal(arr, L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }

}
