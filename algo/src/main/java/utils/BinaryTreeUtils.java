package utils;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.ArrayList;
import java.util.HashMap;

public class BinaryTreeUtils {

    public static BinaryTreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static BinaryTreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
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
