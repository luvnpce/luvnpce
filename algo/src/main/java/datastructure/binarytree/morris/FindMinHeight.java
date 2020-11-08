package datastructure.binarytree.morris;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

/**
 * 给定一棵树，找出这棵树的最小高度
 */
public class FindMinHeight {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            BinaryTreeNode head = BinaryTreeUtils.generateRandomBT(10, 100);
            if (brute(head) != morris(head)) {
                System.out.println("Oops");
                return;
            }
        }
    }

    public static int morris(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        // level记录的是上一个节点的高度
        int level = 0;
        int min = Integer.MAX_VALUE;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
                level++;
                current = current.right;
            } else {
                // 记录子树的高度
                int subHeight = 1;
                // 找到current左树的真实最右节点
                mostRight = current.left;
                while (null != mostRight.right && mostRight.right != current) {
                    mostRight = mostRight.right;
                    subHeight++;
                }
                // 判断
                if (mostRight.right == current) {
                    // 发现是第二次到达current,因为左侧最右节点已经指向了自己
                    if (null == mostRight.left) {
                        // 判断mostright是否是叶节点
                        min = Math.min(min, level);
                    }
                    level -= subHeight;
                    mostRight.right = null;
                    current = current.right;
                } else {
                    // 发现是第一次到达current
                    level++;
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
        int rightHeight = 1;
        current = head;
        while (null != current.right) {
            current = current.right;
            rightHeight++;
        }
        if (null == current.left && null == current.right) {
            min = Math.min(min, rightHeight);
        }
        return min;
    }

    /**
     * 递归
     * 空间复杂度：O(H)
     */
    public static int brute(BinaryTreeNode head) {
        if (null == head) {
            return 0;
        }
        return doBrute(head);
    }

    private static int doBrute(BinaryTreeNode head) {
        if (null == head.left && null == head.right) {
            return 1;
        }
        int left = Integer.MAX_VALUE;
        if (null != head.left) {
            left = doBrute(head.left);
        }
        int right = Integer.MAX_VALUE;
        if (null != head.right) {
            right = doBrute(head.right);
        }
        return Math.min(left, right) + 1;
    }
}
