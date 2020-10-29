package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 找出一棵树上面的最大距离
 * 在一个节点X上面考虑
 * 1. 最大距离和自己没有关系：最大距离在左树或者右树里面产生
 * 2. 最大距离和自己有关系：左树高度+自己+右树高度
 *
 * 根据我们二叉树的递归套路，我们要求左右树都返回自己的最大距离，以及自己的高度
 */
public class DP_MaxDistance {

    public static int getMaxDistance(BinaryTreeNode head) {
        Info info = process(head);
        return info.maxDistance;
    }

    public static Info process(BinaryTreeNode node) {
        if (null == node) {
            return new Info(0, 0);
        }
        Info left = process(node.left);
        Info right = process(node.right);

        int height = Math.max(left.height, right.height) + 1;
        int distance = Math.max(left.maxDistance, right.maxDistance);
        distance = Math.max(distance, left.height + right.height + 1);

        return new Info(distance, height);
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int distance, int height) {
            this.maxDistance = distance;
            this.height = height;
        }
    }
}
