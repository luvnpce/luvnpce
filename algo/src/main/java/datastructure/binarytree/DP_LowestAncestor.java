package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.HashMap;
import java.util.HashSet;

import static utils.BinaryTreeUtils.*;

/**
 * 最低公共祖先
 * 给定一棵二叉树的head，和另外两个节点a,b。返回a和b的最低公共祖先
 * 如果A是B的祖先，那么最低公共祖先就是A
 */
public class DP_LowestAncestor {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = generateRandomBT(maxLevel, maxValue);
            BinaryTreeNode o1 = pickRandomOne(head);
            BinaryTreeNode o2 = pickRandomOne(head);
            if (findAncestorByRecursion(head, o1, o2) != findAncestorByMap(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    /**
     * 递归解法
     */
    public static BinaryTreeNode findAncestorByRecursion(BinaryTreeNode head, BinaryTreeNode a, BinaryTreeNode b) {
        if (null == head) {
            return null;
        }
        Info info = doRecursion(head, a, b);
        return info.ancestor;
    }

    public static Info doRecursion(BinaryTreeNode node, BinaryTreeNode a, BinaryTreeNode b) {
        if (null == node) {
            return new Info(null, false, false);
        }
        Info left = doRecursion(node.left, a, b);
        Info right = doRecursion(node.right, a, b);

        boolean hasA = left.hasA || right.hasA || node == a;
        boolean hasB = left.hasB || right.hasB || node == b;
        BinaryTreeNode ancestor = null;
        if (null != left.ancestor|| null != right.ancestor) {
            ancestor = null != left.ancestor ? left.ancestor : right.ancestor;
        } else if (hasA && hasB) {
            ancestor = node;
        }
        return new Info(ancestor, hasA, hasB);
    }

    public static class Info {
        // 公共祖先
        public BinaryTreeNode ancestor;
        // 有没有遇见a
        public boolean hasA;
        // 有没有遇见b
        public boolean hasB;

        public Info(BinaryTreeNode node, boolean hasA, boolean hasB) {
            this.ancestor = node;
            this.hasA = hasA;
            this.hasB = hasB;
        }
    }

    /**
     * 使用Map
     * Map记录每个节点的父节点
     */
    public static BinaryTreeNode findAncestorByMap(BinaryTreeNode head, BinaryTreeNode o1, BinaryTreeNode o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<BinaryTreeNode, BinaryTreeNode> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        // 先从一个节点，找到他的所有父、祖先节点
        HashSet<BinaryTreeNode> o1Set = new HashSet<>();
        BinaryTreeNode cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        // 再从另外一个节点向上遍历，找到第一个存在set里面的节点，就是最低共同祖先
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public static void fillParentMap(BinaryTreeNode head, HashMap<BinaryTreeNode, BinaryTreeNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }
}
