package greedy;

import utils.ArrayUtils;

import java.util.PriorityQueue;

/**
 * 一条金条切成两半，是需要花费和长度数值一样的铜板的。
 * 比如长度为20的金条，不管怎么切，都要花费20个铜板。一群人想整分整块金条，怎么分最省铜板？
 * 例如：给定数组{10, 20, 30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分
 * 如果先把长度60的金条分成10和50，花费60.再把长度50的金条分成20和30，花费50.一共花费110铜板。但如果先把长度的金条分成30和30，花费60。再把长度30的金条分成10和20，花费30.一共花费90铜板
 * 输入一个数组，返回分割的最小代价。
 */
public class GoldNugget {

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtils.generateAbs(maxSize, maxValue);
            if (brute(arr) != greedy(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    /**
     * 暴力：尝试每两两的组合
     */
    private static int brute(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        return doBrute(arr, 0);
    }

    private static int doBrute(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, doBrute(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    /**
     * 指定要合并2个元素，其他的不变
     */
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        // 创建一个新的数组
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                // 把无关的元素先放进新的数组
                ans[ansi++] = arr[arri];
            }
        }
        // 最后把两个合并的元素合并，放进最后一个位置
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    /**
     * 贪心：Huffman树
     */
    public static int greedy(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : arr) {
            queue.add(i);
        }
        int cost = 0;
        while (queue.size() > 1) {
            int sum = queue.poll() + queue.poll();
            queue.add(sum);
            cost += sum;
        }
        return cost;
    }

}
