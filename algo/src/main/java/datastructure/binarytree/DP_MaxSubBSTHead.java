package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

/**
 * 给定一颗二叉树的Head
 * 返回这棵二叉树中最大的二叉搜索子树的头节点
 *
 * 1. 和X有关：左树右树都是BST，左树最大值 < x < 右树最小值
 * 2. 和X无关：最大的BST要不来自左树，要不来自右树
 */
public class DP_MaxSubBSTHead {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        BinaryTreeNode head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
        System.out.println(maxSubBSTHead(head).value);
    }

    public static BinaryTreeNode maxSubBSTHead(BinaryTreeNode head) {
        if (null == head) {
            return null;
        }
        return process(head).maxSubBSTHead;
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
        // 最大的BST大小
        int maxSubBSTSize = 0;
        // 最大二叉树头部
        BinaryTreeNode maxSubBSTHead = null;
        if (null != left) {
            min = Math.min(left.min, min);
            max = Math.max(left.max, max);
            maxSubBSTSize = left.maxSubBSTSize;
            maxSubBSTHead = left.maxSubBSTHead;
        }
        if (null != right) {
            min = Math.min(right.min, min);
            max = Math.max(right.max, max);
            if (right.maxSubBSTSize > left.maxSubBSTSize) {
                maxSubBSTSize = right.maxSubBSTSize;
                maxSubBSTHead = right.maxSubBSTHead;
            }
        }
        // 自己是不是BST
        if ((null == left ? true : left.maxSubBSTHead == node.left)
                && (null == right ? true : right.maxSubBSTHead == node.right)
                && (null == left ? true : left.max < node.value)
                && (null == right ? true : right.min > node.value)) {
            maxSubBSTSize = (null == left ? 0 :left.maxSubBSTSize) + (null == right ? 0 :right.maxSubBSTSize + 1) + 1;
            maxSubBSTHead = node;
        }
        return new Info(max, min, maxSubBSTHead, maxSubBSTSize);
    }

    public static class Info {
        // 最大值
        public int max;
        // 最小值
        public int min;
        // 最大的BST头部
        public BinaryTreeNode maxSubBSTHead;
        // 最大的BST大小
        public int maxSubBSTSize;

        public Info(int max, int min, BinaryTreeNode node, int maxSubBSTSize) {
            this.max = max;
            this.min = min;
            this.maxSubBSTHead = node;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }
}
