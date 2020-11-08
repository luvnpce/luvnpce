package datastructure.binarytree.morris;

import datastructure.binarytree.structure.BinaryTreeNode;

/**
 * Morris遍历
 * 和递归、非递归不一样
 * 时间复杂度：O(N)
 * 空间复杂度：O(1)
 */
public class MorrisTraversal {

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

    /**
     * 流程：
     * 当前节点current，来到root节点
     * 1. current无左树，currrent = current.right
     * 2. current有左树，找到左树最右节点，mostright
     *     1. 如果mostright的右指针指向null，mostright.right = current, current = current.left;
     *     2. 如果mostright的右指针指向current，mostright.right = null, current = current.right;
     * 3. 如果current == null，结束
     *==============================================================================
     * 规律：
     * 如果一个节点有左树，那么这个节点必定会被访问两次。没有左树的节点只会被访问到一次
     * ==============================================================================
     * Morris序：
     *                1
     *              /  \
     *             2    3
     *            / \   / \
     *           4   5  6  7
     *  1，2，4，2，5，1，3，6，3，7
     *  如果想要打印先序：那就在第一次访问每个节点的时候打印。
     *  如果想要打印中序：那就在第二次访问每个节点的时候打印（如果该节点没有左树，那么只会被访问一次，那就在第一次访问打印）
     */
    public static void morris(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
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
                    current = current.right;
                } else {
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
    }

    /**
     * 先序
     */
    public static void pre(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
                System.out.println(current.value);
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
                    current = current.right;
                } else {
                    System.out.println(current.value);
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
    }

    /**
     * 中序
     */
    public static void in(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
                System.out.println(current.value);
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
                    System.out.println(current.value);
                    current = current.right;
                } else {
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
    }

    /**
     * 后续
     */
    public static void pos(BinaryTreeNode head) {
        if (head == null) {
            return;
        }
        BinaryTreeNode current = head;
        BinaryTreeNode mostRight;
        while(null != current) {
            if (null == current.left) {
                // 没有左树
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
                    // 逆序打印
                    printEdge(current.left);
                    current = current.right;
                } else {
                    mostRight.right = current;
                    current = current.left;
                }
            }
        }
        printEdge(head);
    }

    private static void printEdge(BinaryTreeNode head) {
        BinaryTreeNode tail = reverse(head);
        BinaryTreeNode current = tail;
        while (null != current) {
            System.out.println(current.value);
            current = current.right;
        }
        reverse(tail);
    }

    private static BinaryTreeNode reverse(BinaryTreeNode head) {
        BinaryTreeNode next = null;
        BinaryTreeNode prev = null;
        while (null != head) {
            next = head.right;
            head.right = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
