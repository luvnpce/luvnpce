package lesson.lesson7;

/**
 * 给定一个数组arr
 * 返回如果排序后，相邻两数的最大差值
 */
public class Lesson7_2 {

    /**
     * 解法：
     * arr里面有n个数，找出arr数组里面数字的返回，最小值~最大值
     * 把范围区间按N+1等分
     * 例：arr里面有9个数，范围是0~99，那我们就把0~99按10等分
     * 0~9，10~19，20~29，30~39...
     * 我们遍历整个数组，把每个数放到对应的桶里。
     * 因为arr数组总共只有n个数，而我们有n+1个桶，那么注定有一个桶是空的，
     * 有空桶的含义就是在同一个桶里面的数他们注定不会是【正解】，
     * 因为离空桶左侧最近的桶的最大值，和离空桶右侧最近的桶的最小值，
     * 他们的两个的差值肯定比同一个桶内任意数之间的差值要大。
     * **注意，但是答案不一定是空桶左侧max ~ 空桶右侧min，空桶只是帮我们排除了同一桶内的数不可能是解
     */
    public static int solution(int[] arr) {
        // base case 1
        if (null == arr || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        // base case 2
        if (min == max) {
            return 0;
        }

        // 记录每个桶里是否有数
        boolean[] hasNum = new boolean[len + 1];
        // 记录每个桶里的最小值
        int[] mins = new int[len + 1];
        // 记录每个桶里的最大值
        int[] maxs = new int[len + 1];

        // 把每个数放到各自对应的桶里
        int bucket = 0;
        for (int i = 0; i < arr.length; i++) {
            bucket = getBucket(arr[i], len, min, max);
            mins[bucket] = hasNum[bucket] ? Math.min(mins[bucket], arr[i]) : arr[i];
            maxs[bucket] = hasNum[bucket] ? Math.max(maxs[bucket], arr[i]) : arr[i];
            hasNum[bucket] = true;
        }

        int res = 0;
        int lastMax = maxs[0];
        for (int i = 1; i < len + 1; i++) {
            if (hasNum[i]) {
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return res;
    }

    private static int getBucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

}
