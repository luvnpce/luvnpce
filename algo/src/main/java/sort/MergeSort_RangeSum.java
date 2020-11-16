package sort;

/**
 * 给定一个整数数组nums，返回区间和在[lower, upper]之间的个数，包含lower和upper。
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 *
 * 输入: nums = [-2,5,-1], lower = -2, upper = 2,
 * 输出: 3
 * 解释: 3个区间分别是: [0,0], [2,2], [0,2]，它们表示的和分别为: -2, -1, 2。
 *
 */
public class MergeSort_RangeSum {

    public static int count(int[] nums, int lower, int upper) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return doCount(sum, 0, sum.length - 1, lower, upper);
    }

    public static int doCount(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return lower <= sum[left] && sum[left] <= upper ? 1 : 0;
        }
        int mid = left + ((right - left) >> 1);
        return doCount(sum, left, mid, lower, upper)
                + doCount(sum, mid + 1, right, lower, upper)
                + merge(sum, left, mid, right, lower, upper);
    }

    private static int merge(long[] sum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        /**
         * 这里主要是判断，两个半区之间有没有一些区间是能够满足lower ~ upper范围的
         * 假设[j....i]，j在左半区，i在右半区，我们需要找到j和i能够满足：
         * lower<= sum[i] - sum[j] <= upper
         * 可以转换成
         * lower + sum[j] <= sum[i] <= upper + sum[j]
         * 或者
         * min = sum[i] - upper
         * max = sum[i] - lower
         * 找出满足：sum[j] > min && sum[j] <= max的范围
         *
         */
        for (int i = mid + 1; i <= right ; i++) {
            for (int j = left; j <= mid; j++) {
                if (lower + sum[j] <= sum[i] && sum[i] <= sum[j] + upper) {
                    ans++;
                }
            }
        }

        long[] helper = new long[right - left + 1];
        int pos1 = left;
        int pos2 = mid + 1;
        int i = 0;
        while (pos1 <= mid && pos2 <= right) {
            helper[i++] = sum[pos1] <= sum[pos2] ? sum[pos1++] : sum[pos2++];
        }
        while (pos1 <= mid) {
            helper[i++] = sum[pos1++];
        }
        while (pos2 <= right) {
            helper[i++] = sum[pos2++];
        }
        for (int j = 0; j < helper.length; j++) {
            sum[left + j] = helper[j];
        }
        return ans;
    }
}
