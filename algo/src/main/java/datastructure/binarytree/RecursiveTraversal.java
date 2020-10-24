package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * 递归序
 * 假设我们有一个二叉树：
 *               1
 *             /  \
 *            2    3
 *           / \   / \
 *          4   5  6  7
 * 我们来看一下以递归的方式去访问每个节点的顺序
 * 1->2->4->4->4->2->5->5->5->2->1->3->6->6->6->3->7->7->7->3->1
 * 为什么叶子节点也会出现3次？因为在叶子节点我们也会递归调用他们的左右孩子（虽然是null，马上就会被返回）
 *
 * 那么通过递归序我们再来看二叉树的先序、中序、后序
 * 先序：中->左->右
 * 在每个节点出现的第一次进行打印（对这个节点的操作）== 1->2->4->5->3->6->7
 *
 * 中序：左->中->右
 * 在每个节点出现的第二次进行打印（对这个节点的操作）== 4->2->5->1->6->3->7
 *
 * 后序：左->右->中
 * 在每个节点出现的第三次进行打印（对这个节点的操作）== 4->5->2->6->7->3->1
 */
public class RecursiveTraversal {

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(1);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(3);
        head.left.left = new BinaryTreeNode(4);
        head.left.right = new BinaryTreeNode(5);
        head.right.left = new BinaryTreeNode(6);
        head.right.right = new BinaryTreeNode(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");
    }

    public static void pre(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    public static void in(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }


    public static void pos(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }
}
