package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

/**
 * 给定一颗二叉树的Head
 * 返回这棵二叉树中最大的二叉搜索子树的大小
 *
 * 1. 和X有关：左树右树都是BST，左树最大值 < x < 右树最小值
 * 2. 和X无关：最大的BST要不来自左树，要不来自右树
 */
public class DP_MaxSubBSTSize {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        BinaryTreeNode head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
        System.out.println(maxSubBSTSize(head));
    }

    public static int maxSubBSTSize(BinaryTreeNode head) {
        if (null == head) {
            return 0;
        }
        return process(head).maxSubBSTSize;
    }


    public static Info process(BinaryTreeNode node) {
        if (null == node) {
            return null;
        }
        Info left = process(node.left);
        Info right = process(node.right);

        // 最大值最小值
        int min = node.value;
        int max = node.value;
        if (null != left) {
            min = Math.min(left.min, min);
            max = Math.max(left.max, max);
        }
        if (null != right) {
            min = Math.min(right.min, min);
            max = Math.max(right.max, max);
        }

        // 最大的BST大小
        int maxSubBSTSize = 0;
        if (null != left) {
            maxSubBSTSize = left.maxSubBSTSize;
        }
        if (null != right) {
            maxSubBSTSize = Math.max(right.maxSubBSTSize, maxSubBSTSize);
        }

        // 是不是BST
        boolean isAllBST = false;
        if ((null == left ? true : left.isAllBST)
                && (null == right ? true : right.isAllBST)
                && (null == left ? true : left.max < node.value)
                && (null == right ? true : right.min > node.value)) {
            maxSubBSTSize = (null == left ? 0 :left.maxSubBSTSize) + (null == right ? 0 :right.maxSubBSTSize + 1) + 1;
            isAllBST = true;
        }
        return new Info(max, min, isAllBST, maxSubBSTSize);
    }

    public static class Info {
        // 最大值
        public int max;
        // 最小值
        public int min;
        // 是不是BST
        public boolean isAllBST;
        // 最大的BST大小
        public int maxSubBSTSize;

        public Info(int max, int min, boolean isAllBST, int maxSubBSTSize) {
            this.max = max;
            this.min = min;
            this.isAllBST = isAllBST;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }
}
