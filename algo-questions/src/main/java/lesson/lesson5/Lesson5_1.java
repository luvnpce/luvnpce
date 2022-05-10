package lesson.lesson5;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 如果一个节点x，它的左树结构和右树结构完全一样
 * 那么我们可以说以x为头的树是相等树
 * 给定一颗二叉树的头节点head
 * 返回head整棵树上有多少棵相等子树
 */
public class Lesson5_1 {


    /**
     * 最优解，O(N)
     * =====================================
     * 递归方法里面，时间的瓶颈在于same()方法
     * 判断自己是否是相等树需要去遍历一遍自己的子树
     * 如果可以把这个判断变成O(1)，那么整体就可以是O(N)
     * =====================================
     * 可以通过把左树和右树的结构序列化成一个字符串，然后进行比对
     * 但是这个字符串的长度和树的深度有关，
     * 所以可以对这个字符串进行hashCode，返回一个固定长度的来进行比对
     * 最终可以达成O(1)
     */
    public static int optimal(Node head) {
        String algorithm = "SHA-256";
        Hash hash = new Hash(algorithm);
        return doOptimal(head, hash).ans;
    }

    private static NodeInfo doOptimal(Node head, Hash hash) {
        if (null == head) {
            return new NodeInfo(0, hash.hashCode("#,"));
        }
        NodeInfo left = doOptimal(head.left, hash);
        NodeInfo right = doOptimal(head.right, hash);
        // 计算子树的相等树数量
        int ans = left.ans + right.ans + (left.hashCode.equals(right.hashCode) ? 1 : 0);
        // 计算自己是否是相等树
        String hashCode = hash.hashCode(left.hashCode + "," + head.val + "," + right.hashCode);
        return new NodeInfo(ans, hashCode);
    }

    /**
     * 递归，O(NlogN)
     */
    public static int solution(Node head) {
        if (null == head) {
            return 0;
        }
        // 找出左树有多少棵
        int a1 = solution(head.left);
        // 找出右树有多少棵
        int a2 = solution(head.right);
        // 判断自己是不是相等树，左树是否等于右树
        int a3 = (same(head.left, head.right) ? 1 : 0);
        return a1 + a2 + a3;
    }

    public static boolean same(Node n1, Node n2) {
        if (null == n1 ^ null == n2) {
            // 一个为null，一个不为null
            return false;
        }
        if (null == n1 && null == n2) {
            return true;
        }
        return n1.val == n2.val && same(n1.left, n2.left) && same(n1.right, n2.right);
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class NodeInfo {
        public int ans;
        public String hashCode;

        public NodeInfo(int ans, String hashCode) {
            this.ans = ans;
            this.hashCode = hashCode;
        }
    }

    public static class Hash {

        private MessageDigest hash;

        public Hash(String algorithm) {
            try {
                hash = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        public String hashCode(String input) {
            return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
        }
    }
}
