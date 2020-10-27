package datastructure.binarytree.structure;

public class BinaryTreeNode {

    public int value;

    public BinaryTreeNode left;

    public BinaryTreeNode right;

    // 特定问题用到
    public BinaryTreeNode parent;

    public BinaryTreeNode(int value) {
        this.value = value;
    }
}
