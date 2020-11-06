package kmp;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

import java.util.ArrayList;

/**
 * 给定两颗二叉树a，b
 * 假设a为较大的那颗，判断a是否有某颗子树的结构和b相等
 */
public class TreeEqual {

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode big = BinaryTreeUtils.generateRandomBST(bigTreeLevel, nodeMaxValue);
            BinaryTreeNode small = BinaryTreeUtils.generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = brute(big, small);
            boolean ans2 = kmp(big, small);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish!");
    }

    /**
     * kmp算法：把两个树都通过先序转换成字符串a'，b'，然后判断a'是否包含b'
     */
    public static boolean kmp(BinaryTreeNode a, BinaryTreeNode b) {
        ArrayList<String> aSerial = preSerial(a);
        ArrayList<String> bSerial = preSerial(b);
        String[] aChar = new String[aSerial.size()];
        aChar = aSerial.toArray(aChar);
        String[] bChar = new String[bSerial.size()];
        bChar = bSerial.toArray(bChar);
        return kmp(aChar, bChar) != -1;
    }

    private static int kmp(String[] aChar, String[] bChar) {
        if (null == aChar || null == bChar || bChar.length < 1 || aChar.length < bChar.length) {
            return -1;
        }

        int[] next = generateNext(bChar);
        int x = 0;
        int y = 0;
        while (x < aChar.length && y < bChar.length) {
            if (isEqual(aChar[x], bChar[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == bChar.length ? x-y : -1;
    }

    private static int[] generateNext(String[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[arr.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0;
        int i = 2;
        while (i < arr.length) {
            if (isEqual(arr[i-1], arr[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    private static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    private static ArrayList<String> preSerial(BinaryTreeNode node) {
        ArrayList<String> res = new ArrayList<>();
        pre(node, res);
        return res;
    }

    private static void pre(BinaryTreeNode node, ArrayList<String> res) {
        if (null == node) {
            res.add(null);
        } else {
            res.add(String.valueOf(node.value));
            pre(node.left, res);
            pre(node.right, res);
        }
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
