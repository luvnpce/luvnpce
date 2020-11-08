package datastructure.binarytree.morris;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

import java.util.Stack;

/**
 * 给定一棵树的头节点，判断这棵树是不是BST
 */
public class IsBST {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            BinaryTreeNode head = BinaryTreeUtils.generateRandomBST(5, 50);
            boolean res1 = byInOrder(head);
            boolean res2 = byMorris(head);
            if (res1 != res2) {
                System.out.println("Oops");
                return;
            }
        }

    }

    public static boolean byInOrder(BinaryTreeNode head) {
        if (null == head) {
            return true;
        }
        Stack<BinaryTreeNode> stack = new Stack<>();
        Integer pre = null;
        while (!stack.isEmpty() || null != head) {
            if (null != head) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (null != pre && pre >= head.value) {
                    return false;
                }
                pre = head.value;
                head = head.right;
            }
        }
        return true;
    }

    public static boolean byMorris(BinaryTreeNode head) {
        if (head == null) {
            return true;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        Integer pre = null;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
                if (null != pre && pre >= current.value) {
                    return false;
                }
                pre = current.value;
                current = current.right;
            } else {
                // 找到current左树的真实最右节点
                mostRight = current.left;
                while (null != mostRight.right && mostRight.right != current) {
                    mostRight = mostRight.right;
                }
                // 判断
                if (mostRight.right == current) {
                    mostRight.right = null;
                    if (null != pre && pre >= current.value) {
                        return false;
                    }
                    pre = current.value;
                    current = current.right;
                } else {
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
        return true;
    }
}
