package sort;

/**
 * 给定一个整数数组nums，返回区间和在[lower, upper]之间的个数，包含lower和upper。
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 *
 * 输入: nums = [-2,5,-1], lower = -2, upper = 2,
 * 输出: 3
 * 解释: 3个区间分别是: [0,0], [2,2], [0,2]，它们表示的和分别为: -2, -1, 2。
 *
 * 前置知识：找sum[a....b]的累加和 = sum[0...b] - sum[0...a-1]
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
//        for (int i = mid + 1; i <= right ; i++) {
//            for (int j = left; j <= mid; j++) {
//                if (lower + sum[j] <= sum[i] && sum[i] <= sum[j] + upper) {
//                    ans++;
//                }
//            }
//        }
        /**
         * 优化（换一次思路）：
         * 必须以0位置结尾的子数组 有几个达标
         * 必须以1位置结尾的子数组 有几个达标
         * 必须以2位置结尾的子数组 有几个达标
         * 最后累加所有个达标数
         * ============================
         * 假设i = 10，我们看以10位置结尾的子数组有几个达标
         * arr[0...10]的累加和 = 100, lower = 20, upper = 40
         * 那么我们就要找arr[0...j]的累加和是在[60,80]之间，j < 10
         */
        int windowL = left;
        int windowR = left;
        // 我们从i=mid+1开始，是因为[left...mid]这个范围内的答案已经是merge之前就算好的
        // merge是要考虑两个半区之间还有没有正确答案
        for (int i = mid + 1; i <= right; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 找出累加和在[min,max]范围内的最右位置+1
            while (windowR <= mid && sum[windowR] <= max) {
                windowR++;
            }
            // 找出累加和在[min,max]范围内的最左位置-1
            while (windowL <= mid && sum[windowL] < min) {
                windowL++;
            }
            // 最左和最右之间的都是达标的
            ans += Math.max(0, windowR - windowL);
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
