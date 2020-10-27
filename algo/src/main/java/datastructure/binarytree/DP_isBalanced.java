package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 判断一个二叉树是不是平衡树
 * 如何判断：
 *  1. 左树右树之间的高度差不能大于1
 *  2. 左树和右树都必须是平衡树
 *
 *  根据我们二叉树的递归套路，我们要求左右树返回自己是否是平衡树，以及自己的高度
 */
public class DP_isBalanced {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static boolean isBalanced1(BinaryTreeNode head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(BinaryTreeNode head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static boolean isBalanced2(BinaryTreeNode head) {
        return process2(head).isBalaced;
    }

    public static Info process2(BinaryTreeNode X) {
        if (X == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process2(X.left);
        Info rightInfo = process2(X.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        if (!leftInfo.isBalaced || !rightInfo.isBalaced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }

    // for test
    public static BinaryTreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static BinaryTreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // 左、右要求一样，Info 信息返回的结构体
    public static class Info {
        public boolean isBalaced;
        public int height;

        public Info(boolean b, int h) {
            isBalaced = b;
            height = h;
        }
    }
}
