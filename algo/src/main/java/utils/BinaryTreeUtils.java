package utils;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.ArrayList;

public class BinaryTreeUtils {

    public static BinaryTreeNode generateRandomBT(int maxLevel, int maxValue) {
        return generateBT(1, maxLevel, maxValue);
    }

    public static BinaryTreeNode generateBT(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode((int) (Math.random() * maxValue));
        head.left = generateBT(level + 1, maxLevel, maxValue);
        head.right = generateBT(level + 1, maxLevel, maxValue);
        return head;
    }

    public static BinaryTreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generateBST(1, maxLevel, maxValue, 0);
    }

    public static BinaryTreeNode generateBST(int level, int maxLevel, int maxValue, int minValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode((int) (Math.random() * (maxValue - minValue + 1)));
        head.left = generateBST(level + 1, maxLevel, head.value - 1, minValue);
        head.right = generateBST(level + 1, maxLevel, maxValue, head.value + 1);
        return head;
    }

    /**
     * 从二叉树里随机取一个节点
     */
    public static BinaryTreeNode pickRandomOne(BinaryTreeNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<BinaryTreeNode> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    public static void fillPrelist(BinaryTreeNode head, ArrayList<BinaryTreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }
}
