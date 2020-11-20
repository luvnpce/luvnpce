package slidingwindow;

/**
 * 给定一个数组arr，里面包含正负数（以及0），和一个sum
 * 找出最长子数组（长度）的累加和小于等于sum
 */
public class LongestLessSumSubArray {

    public static void main(String[] args) {

    }

    public static int slideWindow(int[] arr, int sum) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // 最小累加和数组：每个位置i，记录从i开始往右，最小累加和的值
        int[] minSum = new int[arr.length];
        // 最小累加和end位置数组：每个位置i，记录它的最小累加和的结束位置j
        int[] minSumEnd = new int[arr.length];

        minSum[N - 1] = arr[N - 1];
        minSumEnd[N - 1] = N - 1;
        // 从后往前遍历
        for (int i = N - 2; i >= 0 ; i++) {
            if (minSum[i + 1] > 0) {
                // i+1的最小累加和 > 0，那么i位置的最小累加和就是它自己
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            } else {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            }
        }

        /**
         * 从前往后遍历
         * 当来到位置i，我们已经知道从i为开始位置，它的最小累加和 = minSum[i]
         *      - 如果minSum[i] > sum，那么我们可以肯定i是不在答案里的，直接i++
         *      - 如果minSum[i] < sum，那么我们来到j（j = minSumEnd[i] + 1）然后加上j位置的最小累加和，直到 > sum后停止
         * 当得到了一个范围[i...j]它的累加和 <= sum后，我们从i开始逐个从范围里剔除出去
         * ==================这个下面其实就是已经在forloop了===================
         * 每剔除一个后看看这个范围是否可以继续往右扩（whileloop）
         * 更新res，然后再剔除
         */
        int end = 0;
        int tmpSum = 0;
        int res = 0;
        for (int i = 0; i < N; i++) {
            // 从0开始，找出满足<= sum的最长子数组长度
            while(end < N && tmpSum + minSum[end] <= sum) {
                // 更新累加和
                tmpSum += minSum[end];
                // 把end位置移到当前结束的位置+1
                end = minSumEnd[end] + 1;
            }
            res = Math.max(res, end - i);
            if (end > i) {
                // 代表[i....j]这个范围还有数可以剔除
                tmpSum -= arr[i];
            } else {
                // i == end，范围里已经没有数了，那么就从下一个数开始
                end = i + 1;
            }
        }
        return res;
    }
}
