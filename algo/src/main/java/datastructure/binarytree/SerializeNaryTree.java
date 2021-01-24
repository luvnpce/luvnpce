package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/
 * Design an algorithm to encode an N-ary tree into a binary tree and decode the binary tree
 * to get the original N-ary tree. An N-ary tree is a rooted tree in which each node has no more than N children.
 * Similarly, a binary tree is a rooted tree in which each node has no more than 2 children.
 * There is no restriction on how your encode/decode algorithm should work.
 * You just need to ensure that an N-ary tree can be encoded to a binary tree and this binary tree
 * can be decoded to the original N-nary tree structure.
 *
 * Nary-Tree input serialization is represented in their level order traversal,
 * each group of children is separated by the null value (See following example).
 */
public class SerializeNaryTree {

    /**
     * 一个Node的所有children都挂在Node.left，children都往右依次连接
     *               Node
     *             /
     *           child
     *             \
     *            child
     *               \
     *              child
     *                 \
     *                 child
     * 代码逻辑：深度优先遍历，先处理Node的child的child，然后再处理平级的child
     */
    public BinaryTreeNode encode(Node root) {
        if (null == root) {
            return null;
        }
        BinaryTreeNode node = new BinaryTreeNode(root.val);
        node.left = encodeChildren(root.children);
        return node;
    }

    private BinaryTreeNode encodeChildren(List<Node> children) {
        BinaryTreeNode head = null;
        BinaryTreeNode cur = null;
        for (Node child : children) {
            BinaryTreeNode tmp = new BinaryTreeNode(child.val);
            if (null == head) {
                head = tmp;
            } else {
                cur.right = tmp;
            }
            cur = tmp;
            cur.left = encodeChildren(child.children);
        }
        return head;
    }


    public Node decode(BinaryTreeNode root) {
        if (null == root) {
            return null;
        }
        Node node = new Node(root.value, decodeChildren(root));
        return node;
    }

    private List<Node> decodeChildren(BinaryTreeNode root) {
        if (null == root.left) {
            return null;
        }
        List<Node> children = new ArrayList<>();
        BinaryTreeNode child = root.left;
        while (null != child) {
            children.add(new Node(child.value, decodeChildren(child)));
            child = child.right;
        }
        return children;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
}
