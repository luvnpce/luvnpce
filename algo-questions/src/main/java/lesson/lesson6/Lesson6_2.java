package lesson.lesson6;

import utils.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数组中所有数都异或起来的结果，叫做异或和
 * 给定一个数组arr，可以任意切分成若干个不相交的子数组
 * 其中一定存在一种最优方案，使得切分异或和为0的子数组最多
 * 返回这个最多数量
 */
public class Lesson6_2 {

    public static void main(String[] args) {
        int times = 1000;
        int maxSize = 12;
        int maxValue = 5;
        for (int i = 0; i < times; i++) {
            int[] arr = ArrayUtils.generate(maxSize, maxValue);
            if (brute(arr) != optimize(arr)) {
                System.out.println("Ooops");
                System.out.println("brute = " + brute(arr));
                System.out.println("optimize = " + optimize(arr));
            }
        }
    }

    /**
     * 动态规划：O(N)
     * dp[i]：数组[0...i]的最多子数组异或和为0的数量
     * ==============
     * 如何计算dp[i]，分以下两种情况：
     * 1. 假设以i结尾的子数组的异或和为0，那么需要找出这个子数组起始位置的前一位j，因为dp[i] = dp[j] + 1
     * 已知[0...i]的前缀异或和是xor，那么只要找出上一次出现前缀异或和为xor的位置，那就是j
     * 因为[j+1...i]的异或和是0，等于[0...i] == [0...j]，因为[0...j]^[0...i] = 0
     * 2. 假设以i结尾的子数组的异或和不是0，那么dp[i] = dp[i-1]
     *
     */
    public static int optimize(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int[] dp = new int[arr.length];
        // key:前缀异或和xor
        // value:上一次xor出现的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        // base case，上一次0出现的位置
        map.put(0, -1);
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
            if (map.containsKey(xor)) {
                // 情况1
                int prev = map.get(xor);
                dp[i] = prev == -1 ? 1 : (dp[prev] + 1);
            }
            if (i > 0) {
                // 情况2
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            map.put(xor, i);
        }
        return dp[arr.length - 1];
    }

    /**
     * 暴力解：O(2^n)
     * 每个节点有两个分支（切分和不切分）
     */
    public static int brute(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        // 前缀异或和数组
        int[] eor = new int[arr.length];
        eor[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        return doBrute(eor, 1, new ArrayList<>());
    }

    /**
     * index来到每个位置，判断在index位置要不要做切分，[0....index-1][index....N]
     * parts记录哪些位置要做切分
     * eor代表前缀异或和数组
     * 如果要计算[0....index]的异或和是否为0，那么就可以取eor[index]
     * 如果要计算[3....index]的异或和是否为0，那么就等于eor[index] ^ eor[2]
     */
    private static int doBrute(int[] eor, int index, List<Integer> parts) {
        int ans = 0;
        if (index == eor.length) {
            // 已经到数组尾N，那么需要把N加到parts里，代表最后这一段要做切分
            parts.add(index);
            // 计算所有子数组异或和等于0的数量
            ans = eorZeroParts(eor, parts);
            parts.remove(parts.size() - 1);
        } else {
            // 这个位置不做切分
            int p1 = doBrute(eor, index + 1, parts);
            // 这个位置做切分
            parts.add(index);
            int p2 = doBrute(eor, index + 1, parts);
            parts.remove(parts.size() - 1);     // 还原现场
            ans = Math.max(p1, p2);
        }
        return ans;
    }

    private static int eorZeroParts(int[] eor, List<Integer> parts) {
        int ans = 0;
        int prev = 0;
        for (Integer index : parts) {
            int temp = eor[index - 1] ^ prev;
            ans = temp == 0 ? ans + 1 : ans;
            prev = eor[index - 1];
        }
        return ans;
    }

}
