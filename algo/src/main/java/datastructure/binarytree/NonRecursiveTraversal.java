package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.Stack;
//50.55

/**
 *  先序：中->左->右
 *  先处理头部，压栈右child，再压栈左child
 *
 *  中序：左->中->右
 *  1. 先无脑压栈左child
 *  2. 当左child == null时，从栈弹出一个节点，打印
 *  3. 然后压栈右child，然后重复1
 *
 *  后序：左->右->中
 *  假设我们调整一下先序，先处理头部，压栈左chiLd，再压栈右child。那么顺序就是中->右->左。
 *  最后翻转一下就是后序的左->右->中
 */
public class NonRecursiveTraversal {

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
        pos2(head);
    }

    public static void pre(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.empty()) {
            BinaryTreeNode node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 1. 先无脑压栈左child
     * 2. 当左child == null时，从栈弹出一个节点，打印
     * 3. 然后压栈右child，然后重复1
     */
    public static void in(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                // 此时，我们可以认为head左边的子树已经处理完了
                System.out.println(head.value);
                head = head.right;
            }
        }
    }

    /**
     * 使用了2个stack
     */
    public static void pos(BinaryTreeNode head) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        Stack<BinaryTreeNode> help = new Stack<>();
        stack.push(head);
        while (!stack.empty()) {
            BinaryTreeNode node = stack.pop();
            help.push(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!help.empty()) {
            BinaryTreeNode node = help.pop();
            System.out.println(node.value);
        }
    }

    /**
     * 使用一个stack
     */
    public static void pos2(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        BinaryTreeNode c = null;
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            c = stack.peek();
            if (c.left != null && c.left != head && c.right != head) {
                stack.push(c.left);
            } else if (c.right != null && c.right != head) {
                stack.push(c.right);
            } else {
                System.out.println(stack.pop().value);
                head = c;
            }
        }
    }
}
