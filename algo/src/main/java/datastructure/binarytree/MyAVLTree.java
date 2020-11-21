package datastructure.binarytree;

import datastructure.binarytree.structure.AVLNode;
import datastructure.binarytree.structure.BinaryTreeNode;

public class MyAVLTree extends MySelfBalancingBST{

    @Override
    public BinaryTreeNode insert(int element) {
        BinaryTreeNode newNode = super.insert(element);
        rebalance((AVLNode) newNode);
        return newNode;
    }

    @Override
    public BinaryTreeNode delete(int element) {
        BinaryTreeNode deleteNode = super.search(element);
        if (deleteNode != null) {
            BinaryTreeNode successorNode = super.doDelete(deleteNode);
            if (successorNode != null) {
                // if replaced from getMinimum(deleteNode.right) then come back there and update
                // heights
                AVLNode minimum = successorNode.right != null ? (AVLNode) getMinimum(successorNode.right)
                        : (AVLNode) successorNode;
                recomputeHeight(minimum);
                rebalance((AVLNode) minimum);
            } else { // 并没有任何节点替代被删除节点的位置，被删除节点是孤零零被删除的
                recomputeHeight((AVLNode) deleteNode.parent);
                rebalance((AVLNode) deleteNode.parent);
            }
            return successorNode;
        }
        return null;
    }

    private void rebalance(AVLNode node) {
        while (node != null) {
            BinaryTreeNode parent = node.parent;
            // 空树  -1
            // 叶节点  0
            int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
            int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
            int nodeBalance = rightHeight - leftHeight;
            // rebalance (-2 means left subtree outgrow, 2 means right subtree outgrow)
            if (nodeBalance == 2) {
                if (node.right.right != null && ((AVLNode) node.right.right).height == leftHeight + 1) {
                    // RR型
                    node = (AVLNode) avlRotateLeft(node);
                } else {
                    // RL型
                    node = (AVLNode) doubleRotateRightLeft(node);
                }
            } else if (nodeBalance == -2) {
                if (node.left.left != null && ((AVLNode) node.left.left).height == rightHeight + 1) {
                    // LL型
                    node = (AVLNode) avlRotateRight(node);
                } else {
                    // LR型
                    node = (AVLNode) doubleRotateLeftRight(node);
                }
            } else {
                updateHeight(node);
            }
            node = (AVLNode) parent;
        }
    }

    /**
     * LR型
     */
    private BinaryTreeNode doubleRotateLeftRight(AVLNode node) {
        node.left = super.rotateLeft(node.left);
        return rotateRight(node);
    }

    /**
     * RL型
     */
    private BinaryTreeNode doubleRotateRightLeft(AVLNode node) {
        node.right = super.rotateRight(node.right);
        return rotateLeft(node);
    }

    private BinaryTreeNode avlRotateLeft(AVLNode node) {
        BinaryTreeNode tmp = super.rotateLeft(node);
        updateHeight((AVLNode) tmp.left);
        updateHeight((AVLNode) tmp);
        return tmp;
    }

    private BinaryTreeNode avlRotateRight(AVLNode node) {
        BinaryTreeNode tmp = super.rotateRight(node);
        updateHeight((AVLNode) tmp.right);
        updateHeight((AVLNode) tmp);
        return tmp;
    }

    private void recomputeHeight(AVLNode node) {
        while (node != null) {
            node.height = maxHeight((AVLNode) node.left, (AVLNode) node.right) + 1;
            node = (AVLNode) node.parent;
        }
    }

    private int maxHeight(AVLNode node1, AVLNode node2) {
        if (node1 != null && node2 != null) {
            return Math.max(node1.height, node2.height);
        } else if (node1 == null) {
            return node2 != null ? node2.height : -1;
        } else if (node2 == null) {
            return node1 != null ? node1.height : -1;
        }
        return -1;
    }

    private static void updateHeight(AVLNode node) {
        int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
        int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
        node.height = 1 + Math.max(leftHeight, rightHeight);
    }

}
