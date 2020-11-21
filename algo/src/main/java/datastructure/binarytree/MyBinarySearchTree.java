package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 最基本的BinarySearchTree
 * worstcase：insert的数是升序，就等于一个链表
 */
public class MyBinarySearchTree {

    // 头节点
    public BinaryTreeNode root;

    // 大小
    protected int size;

    public BinaryTreeNode search(int element) {
        BinaryTreeNode node = this.root;
        while(null != node && node.value != element) {
            node = element < node.value ? node.left : node.right;
        }
        return node;
    }

    public BinaryTreeNode insert(int element) {
        if (null == root) {
            root = new BinaryTreeNode(element);
            size++;
            return root;
        }
        // 从头节点开始，找到一个null的地方
        // 因为添加的新节点是要挂在一个已有节点下面的，所以有个一个parent变量来记录当前位置的父节点
        BinaryTreeNode parent = null;
        BinaryTreeNode search = root;
        while (null != search) {
            parent = search;
            search = element < search.value ? search.left : search.right;
        }
        // 创建这个新节点
        BinaryTreeNode node = new BinaryTreeNode(element);
        // 判断待插入的值是小于还是大于父节点
        if (element < parent.value) {
            // 小于，挂在父节点的左边
            parent.left = node;
        } else {
            // 大于，挂在父节点的右边
            parent.right = node;
        }
        size++;
        return node;
    }

    public BinaryTreeNode delete(int element) {
        BinaryTreeNode node = search(element);
        if (null != node) {
            return doDelete(node);
        } else {
            return null;
        }
    }

    protected BinaryTreeNode doDelete(BinaryTreeNode node) {
        BinaryTreeNode res = null;
        if (null != node) {
            if (null == node.left) {
                // 左孩子为null，那就用右孩子替代node
                res = transplant(node, node.right);
            } else if (null == node.right) {
                // 右孩子为null，那就用左孩子替代node
                res = transplant(node, node.left);
            } else {
                // 两边孩子都不为null，找到右孩子下的最小节点
                BinaryTreeNode successor = getMinimum(node.right);
                if (successor.parent != node) {
                    // successor不是node的右孩子，正常处理，如果successor就是node的右孩子，就不需要下面的操作
                    // 因为successor要去代替node，所以successor的右孩子要来代替successor
                    transplant(successor, successor.right);
                    // 修改successor的右孩子为node的右孩子
                    successor.right = node.right;
                    successor.right.parent = successor;
                }
                transplant(node, successor);
                successor.left = node.left;
                successor.left.parent = successor;
                res = successor;
            }
            size--;
        }
        return res;
    }

    protected BinaryTreeNode getMinimum(BinaryTreeNode node) {
        while (null != node && null != node.left) {
            node = node.left;
        }
        return node;
    }

    private BinaryTreeNode transplant(BinaryTreeNode oldNode, BinaryTreeNode newNode) {
        if (null == oldNode.parent) {
            // 需要替换的就是root
            this.root = newNode;
        } else if (oldNode == oldNode.parent.left) {
            // 需要替换的节点是父节点的左边
            oldNode.parent.left = newNode;
        } else {
            // 需要替换的节点是父节点的右边
            oldNode.parent.right = newNode;
        }
        if (null != newNode) {
            newNode.parent = oldNode.parent;
        }
        return newNode;
    }
}
