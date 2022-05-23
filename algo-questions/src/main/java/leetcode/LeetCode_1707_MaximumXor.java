package leetcode;

/**
 * https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
 * difficulty: hard
 *
 * You are given an array nums consisting of non-negative integers. You are also given a queries array, where queries[i] = [xi, mi].
 * The answer to the ith query is the maximum bitwise XOR value of xi and any element of nums that does not exceed mi.
 * In other words, the answer is max(nums[j] XOR xi) for all j such that nums[j] <= mi. If all elements in nums are larger than mi,
 * then the answer is -1.
 *
 * Return an integer array answer where answer.length == queries.length and answer[i] is the answer to the ith query.
 *
 * Input: nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
 * Output: [3,3,7]
 * Explanation:
 * 1) 0 and 1 are the only two integers not greater than 1. 0 XOR 3 = 3 and 1 XOR 3 = 2. The larger of the two is 3.
 * 2) 1 XOR 2 = 3.
 * 3) 5 XOR 2 = 7.
 *
 */
public class LeetCode_1707_MaximumXor {

    public static int[] solution(int[] nums, int[][] queries) {
        TrieNum trieNum = new TrieNum();
        for (int num : nums) {
            trieNum.add(num);
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            ans[i] = trieNum.maxXor(query[0], query[1]);
        }

        return ans;
    }


    private static class TrieNode {
        // next[0] = 0的方向
        // next[1] = 1的方向
        public TrieNode[] next;
        public int min;

        public TrieNode() {
            this.min = Integer.MAX_VALUE; // 当前节点下遇到的最小数
            this.next = new TrieNode[2];
        }
    }

    private static class TrieNum {

        public TrieNode head = new TrieNode();

        public void add(int num) {
            TrieNode cur = head;
            head.min = Math.min(num, head.min);
            // 从左到右，遍历num的二进制数，然后在前缀树上移动
            for (int move = 30; move >= 0; move--) {
                int path = ((num >> move) & 1);
                cur.next[path] = cur.next[path] == null // 判断前缀树该方向有没有节点
                        ? new TrieNode()                // 没有则创建
                        : cur.next[path];               // 有则啥也不做
                cur = cur.next[path];                   // 去到下一个节点
                cur.min = Math.min(num, cur.min);       // 更新节点记录的最小数
            }
        }

        /**
         * 在现有的前缀树结构上，找出与num异或能得到最大结果的数
         */
        public int maxXor(int num, int min) {
            if (head.min > min) {
                // 当前节点下的最小数已经对于限制的min，无解
                return -1;
            }
            TrieNode cur = head;
            int ans = 0;
            for (int move = 30; move >= 0; move--) {
                // 取出当前位置的二进制数
                int path = ((num >> move) & 1);
                // 已知当前位置的二进制数，求它与什么异或能够得出最大的数
                int best = path ^ 1;                    // 数都希望异或和能成为1，所以要和当前位的数相反
                best = cur.next[best] != null
                        && cur.next[best].min <= min     // 前缀树里是否有best这个节点可走，且这个节点的最小数 <= min
                        ? best                          // 有best节点则选best节点
                        : (best ^ 1);                   // 没有的话只能选择相反的节点
                ans |= (path ^ best) << move;           // 记录当前位置的二进制数可异或出的最大值
                cur = cur.next[best];
            }
            return ans;
        }
    }
}
