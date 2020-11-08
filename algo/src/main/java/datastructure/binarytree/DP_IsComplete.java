package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

import static utils.BinaryTreeUtils.generateRandomBT;

/**
 * 判断是不是完全二叉树
 * - 叶节点可以不是满的，但是必须是从左到右
 */
public class DP_IsComplete {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = generateRandomBT(maxLevel, maxValue);
            if (isCompleteByBFS(head) != isCompleteByRecursion(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }

    public static boolean isCompleteByBFS(BinaryTreeNode head) {
        if (null == head) {
            return true;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        boolean flag = false;
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            if (flag && (null != node.left || null != node.right)) {
                return false;
            }
            if (null != node.right && null == node.left) {
                return false;
            }
            if ((null == node.left || null == node.right) && !flag) {
                flag = true;
            }
            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }
        }
        return true;
    }

    /**
     * 分4种情况
     * 1. 满二叉树：左树是满的，右树也是满的，且两边高度相等
     * 2. 左树是满的，右树是满的，左树高度 > 右树高度
     * 3. 左树是满的，右树是完全二叉树，左树高度 == 右树高度
     * 4. 左树是完全二叉树，右树是满的， 左树高度 > 右树高度
     */
    public static boolean isCompleteByRecursion(BinaryTreeNode head) {
        if (null == head) {
            return true;
        }
        Info info = doRecursion(head);
        return info.isComplete;
    }

    private static Info doRecursion(BinaryTreeNode head) {
        if (null == head) {
            return new Info(0, true, true);
        }
        Info left = doRecursion(head.left);
        Info right = doRecursion(head.right);
        int height = Math.max(left.height, right.height) + 1;
        boolean isFull = left.height == right.height && left.isFull && right.isFull;
        boolean isComplete = false;
        if (isFull) {
            isComplete = true;
        } else {
            if (left.isComplete && right.isComplete) {
                if (left.isComplete && right.isFull && left.height == right.height + 1) {
                    isComplete = true;
                }
                if (left.isFull && right.isFull && left.height == right.height + 1) {
                    isComplete = true;
                }
                if (left.isFull && right.isComplete && left.height == right.height) {
                    isComplete = true;
                }
            }
        }
        return new Info(height, isFull, isComplete);
    }

    public static class Info {
        // 高度
        public int height;
        // 是不是满二叉树
        public boolean isFull;
        // 是不是完全二叉树
        public boolean isComplete;

        public Info(int height, boolean isFull, boolean isComplete) {
            this.height = height;
            this.isFull = isFull;
            this.isComplete = isComplete;
        }
    }
}
