package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 判断一个二叉树是不是满二叉树
 * 假设二叉树高度是L
 * 那么满二叉树必须是 N = 2^L - 1，N = 节点总数
 */
public class DP_IsFull {

    public static void main(String[] args) {

    }

    public static boolean isFull(BinaryTreeNode head) {
        if (null == head) {
            return true;
        }

        Info info = process(head);
        return (1 << info.height) - 1 == info.nodes;
    }

    public static Info process(BinaryTreeNode head) {
        if (null == head) {
            return new Info(0 , 0);
        }
        Info left = process(head.left);
        Info right = process(head.right);
        int height = Math.max(left.height, right.height) + 1;
        int nodes = left.nodes + right.nodes + 1;
        return new Info(height, nodes);
    }


    public static class Info {
        // 高度
        public int height;
        // 节点数
        public int nodes;

        public Info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }
}
