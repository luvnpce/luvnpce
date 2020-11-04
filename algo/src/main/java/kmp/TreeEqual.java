package kmp;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 给定两颗二叉树a，b
 * 假设a为较大的那颗，判断a是否有某颗子树的结构和b相等
 */
public class TreeEqual {

    public static void main(String[] args) {
    }

    /**
     * kmp算法：把两个树都通过先序转换成字符串a'，b'，然后判断a'是否包含b'
     */
    public static boolean kmp(BinaryTreeNode a, BinaryTreeNode b) {
        return false;
    }

    /**
     * 暴力：O(NM)
     */
    public static boolean brute(BinaryTreeNode a, BinaryTreeNode b) {
        if (null == b) {
            return true;
        }
        if (null == a) {
            return false;
        }
        if (isSame(a, b)) {
            return true;
        }
        return brute(a.left, b) || brute(a.right, b);
    }

    private static boolean isSame(BinaryTreeNode a, BinaryTreeNode b) {
        if (null == a && null != b) {
            return false;
        }
        if (null != a && null == b) {
            return false;
        }
        if (null == a && null == b) {
            return true;
        }
        if (a.value != b.value) {
            return false;
        }
        return isSame(a.left, b.left) && isSame(a.right, b.right);
    }
}
