package lesson.lesson6;

import utils.ArrayUtils;

import java.util.Arrays;

/**
 * 数组中所有数都异或起来的结果，叫做异或和
 * 给定一个数组arr，返回arr的最大子数组异或和
 */
public class Lesson6_1 {

    /**
     * 最优解
     * 整体逻辑还是一样，计算0...i整体的异或和a
     * 然后针对每个前缀异或和，能够在O(1)的时间内找出能够匹配出最大异或和的b
     * 需要使用前缀树
     * ie 0010前缀树
     *          *
     *        /
     *       0
     *      /
     *     0
     *      \
     *       1
     *      /
     *     0
     */
    public static int optimal(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        int xor = 0;
        TrieNum trieNum = new TrieNum();
        // base case，可以谁都不异或（任何数和0异或都是它本身）
        trieNum.add(0);
        // 遍历计算每一个以i结尾的前缀异或和，然后计算出以i结尾能得到的最大异或和是多少
        for (int i = 0; i < arr.length; i++) {
            xor = xor ^ arr[i];
            ans = Math.max(ans, trieNum.maxXor(xor));
            trieNum.add(xor);   // 把这个前缀异或和也记录进前缀树
        }
        return ans;
    }

    private static class TrieNode {
        // next[0] = 0的方向
        // next[1] = 1的方向
        public TrieNode[] next = new TrieNode[2];
    }

    private static class TrieNum {

        public TrieNode head = new TrieNode();

        public void add(int num) {
            TrieNode cur = head;
            // 从左到右，遍历num的二进制数，然后在前缀树上移动
            for (int move = 31; move >= 0; move--) {
                int path = ((num >> move) & 1);
                cur.next[path] = cur.next[path] == null // 判断前缀树该方向有没有节点
                        ? new TrieNode()                // 没有则创建
                        : cur.next[path];               // 有则啥也不做
                cur = cur.next[path];                   // 去到下一个节点
            }
        }

        /**
         * 在现有的前缀树结构上，找出与num异或能得到最大结果的数
         */
        public int maxXor(int num) {
            TrieNode cur = head;
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                // 取出当前位置的二进制数
                int path = ((num >> move) & 1);
                // 已知当前位置的二进制数，求它与什么异或能够得出最大的数
                int best = move == 31
                        ? path                          // 最左位（第32位）是符号位，所以要让它异或后保持为0，所以特殊处理
                        : path ^ 1;                     // 其余的数都希望异或和能成为1，所以要和当前位的数相反
                best = cur.next[best] != null           // 前缀树里是否有best这个节点可走
                        ? best                          // 有best节点则选best节点
                        : (best ^ 1);                   // 没有的话只能选择相反的节点
                ans |= (path ^ best) << move;           // 记录当前位置的二进制数可异或出的最大值
                cur = cur.next[best];
            }
            return ans;
        }
    }

    /**
     * 暴力解
     * 假设要求出0...i位置上最大子数组异或和，那就是先求
     * 1、0...i整体的异或和，假设是a
     * 2、求0~0，0~1，0~2...0~i的每个异或和，看看这些异或和里哪个和a异或能得到最大值
     * ****假设0~2的异或和b，和a异或能得到最大值，那么就等于3~i这个子数组能返回最大异或和
     * ****因为a是0~i的整体异或和，再和0~2的异或和 进行异或，就等于把0~2给抵消了，最终得出3~i可以得出最大异或和
     * 所以暴力解法就是i从0~N开始遍历，寻找每个阶段的最大异或和，最后返回最大值
     */
    public static int brute(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        /**
         * 准备一个前缀异或和数组 xor
         * xor[3] = 0...3的异或和
         */
        int[] xor = new int[arr.length];
        xor[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            xor[i] = xor[i - 1] ^ arr[i];
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length ; j++) {
                // 逐个尝试arr[0...j]，arr[1...j]，arr[2...j]，arr[i...j]，arr[j...j]
                ans = Math.max(ans, i == 0 ? xor[j] : xor[j] ^ xor[i - 1]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtils.generate(maxSize, maxValue);
            if (brute(arr) != optimal(arr)) {
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
    }
}
