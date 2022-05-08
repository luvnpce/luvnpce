package leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 * difficulty: medium
 *
 * Given an array of integers preorder, which represents the preorder traversal of a BST (i.e., binary search tree),
 * construct the tree and return its root.
 * It is guaranteed that there is always possible to find a binary search tree with the given requirements for the given test cases.
 *
 * A binary search tree is a binary tree where for every node, any descendant of Node.left has a value strictly less than Node.val,
 * and any descendant of Node.right has a value strictly greater than Node.val.
 *
 * A preorder traversal of a binary tree displays the value of the node first, then traverses Node.left, then traverses Node.right.
 */
public class LeetCode_1008_BST {

    /**
     * 最优解 O(N)
     * 通过使用单调栈生成数组bigger[]，
     * bigger[i]代表离i最近且大于它的数的位置
     * ==================================
     * 这里还能继续优化常量时间，通过数组来实现stack的功能
     */
    public static TreeNode optimal(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        int[] bigger = new int[nums.length];
        // 默认全是0
        Arrays.fill(bigger, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < bigger.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                /**
                 * 看看栈顶部的位置x，nums[x]是否 < 当前位置i的值 nums[i]
                 * 那么取栈顶的位置，bigger[x] = i
                 */
                bigger[stack.pop()] = i;
            }
            // 把当前数的位置压栈
            stack.push(i);
        }

        return doOptimal(nums, 0, nums.length - 1, bigger);
    }

    private static TreeNode doOptimal(int[] nums, int left, int right, int[] bigger) {
        if (left > right) {
            return null;
        }
        // 找出第一个为nums[left]大的数的位置
        int nextRight = (bigger[left] == -1 || bigger[left] > right) ? right + 1 : bigger[left];

        TreeNode head = new TreeNode(nums[left]);
        head.left = doOptimal(nums, left + 1, nextRight - 1, bigger);
        head.left = doOptimal(nums, nextRight, right, bigger);
        return head;
    }

    /**
     * 递归思维
     */
    public static TreeNode solution(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return doSolution(nums, 0, nums.length - 1);
    }

    /**
     * 时间复杂度O(N^2)
     * 因为每次要遍历找出位置x
     */
    public static TreeNode doSolution(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int x = left + 1;
        // 找出位置x，s.t nums[x] > head.val，那么从x到right就是右树，left到x-1就是左树
        for (; x <= right; x++) {
            if (nums[x] > nums[left]) {
                break;
            }
        }
        TreeNode head = new TreeNode(nums[left]);
        // 解左树
        head.left = doSolution(nums, left + 1, x - 1);
        // 解右树
        head.right = doSolution(nums, x, right);
        return head;
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
