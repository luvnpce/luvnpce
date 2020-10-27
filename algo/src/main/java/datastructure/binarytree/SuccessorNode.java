package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 给与一个节点X,找出X的后继节点
 *
 * 后继节点：在中序排序里，X的后一位
 */
public class SuccessorNode {

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(6);
        head.parent = null;
        head.left = new BinaryTreeNode(3);
        head.left.parent = head;
        head.left.left = new BinaryTreeNode(1);
        head.left.left.parent = head.left;
        head.left.left.right = new BinaryTreeNode(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new BinaryTreeNode(4);
        head.left.right.parent = head.left;
        head.left.right.right = new BinaryTreeNode(5);
        head.left.right.right.parent = head.left.right;
        head.right = new BinaryTreeNode(9);
        head.right.parent = head;
        head.right.left = new BinaryTreeNode(8);
        head.right.left.parent = head.right;
        head.right.left.left = new BinaryTreeNode(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new BinaryTreeNode(10);
        head.right.right.parent = head.right;

        BinaryTreeNode test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessor(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessor(test));
    }


    public static BinaryTreeNode getSuccessor(BinaryTreeNode node) {
        if (null == node) {
            return null;
        }
        if (null != node.right) {
            // 如果有右子树，那么后继节点就是右子树里的最左节点
            return getChild(node.right);
        } else {
            // 没有右树，那么后继节点就是第一个右上的父亲节点，没有的话则代表后继节点是null
            return getParent(node);
        }
    }

    private static BinaryTreeNode getChild(BinaryTreeNode node) {
        while (null != node.left) {
            node = node.left;
        }
        return node;
    }

    private static BinaryTreeNode getParent(BinaryTreeNode node) {
        BinaryTreeNode parent = node.parent;
        while (null != parent && parent.left != node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }


}
