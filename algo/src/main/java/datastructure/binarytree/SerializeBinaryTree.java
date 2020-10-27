package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.*;

/**
 * 序列化和反序列化二叉树
 *               1
 *              /   \
 *             2     3
 *            / \   / \
 *           4     6   7
 *  先序：[1, 2, 4, null, null, null, 3, 6, null, null, 7, null, null]
 *  中序：[2, 4, null, null, null, 1, 3, 6, null, null, 7, null, null]
 *  后序：[2, 4, null, null, null, 3, 6, null, null, 7, null, null, 1]
 *  bfs：[1, 2, 3, 4, null, 6, 7, null, null, null, null, null, null]
 */
public class SerializeBinaryTree {

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(1);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(3);
        head.left.left = new BinaryTreeNode(4);
        head.right.left = new BinaryTreeNode(6);
        head.right.right = new BinaryTreeNode(7);

        Queue<String> pre = serial(head, 1);
        System.out.println("先序序列化：" + Arrays.toString(pre.toArray()));
        System.out.println("========");
        Queue<String> in = serial(head, 2);
        System.out.println("中序序列化：" + Arrays.toString(in.toArray()));
        System.out.println("========");
        Queue<String> pos = serial(head, 3);
        System.out.println("后序序列化：" + Arrays.toString(pos.toArray()));
        System.out.println("========");
        Queue<String> bfs = serial(head, 4);
        System.out.println("bfs序列化：" + Arrays.toString(bfs.toArray()));
    }

    /**
     * type: 1 = 先序， 2 = 中序， 3 = 后序
     */
    public static Queue<String> serial(BinaryTreeNode head, int type) {
        Queue<String> queue = new LinkedList<>();
        switch (type) {
            case 1:
                doPre(head, queue);
                break;
            case 2:
                doIn(head, queue);
                break;
            case 3:
                doPos(head, queue);
                break;
            case 4:
                doLevel(head, queue);
                break;
            default:
        }
        return queue;
    }

    /**
     * 先序序列化
     */
    private static void doPre(BinaryTreeNode node, Queue<String> queue) {
        if (node == null) {
            queue.add(null);
        } else {
            queue.add(String.valueOf(node.value));
            doPre(node.left, queue);
            doPre(node.right, queue);
        }
    }

    /**
     * 中序序列化
     */
    private static void doIn(BinaryTreeNode node, Queue<String> queue) {
        if (node == null) {
            queue.add(null);
        } else {
            doPre(node.left, queue);
            queue.add(String.valueOf(node.value));
            doPre(node.right, queue);
        }
    }

    /**
     * 后序序列化
     */
    private static void doPos(BinaryTreeNode node, Queue<String> queue) {
        if (node == null) {
            queue.add(null);
        } else {
            doPre(node.left, queue);
            doPre(node.right, queue);
            queue.add(String.valueOf(node.value));
        }
    }

    /**
     * bfs序列化
     */
    private static void doLevel(BinaryTreeNode head, Queue<String> queue) {
        if (null == head) {
            queue.add(null);
        }
        Queue<BinaryTreeNode> q = new LinkedList<>();
        q.add(head);
        queue.add(String.valueOf(head.value));

        while (!q.isEmpty()) {
            BinaryTreeNode node = q.poll();
            if (null != node.left) {
                q.add(node.left);
                queue.add(String.valueOf(node.left.value));
            } else {
                queue.add(null);
            }
            if (null != node.right) {
                q.add(node.right);
                queue.add(String.valueOf(node.right.value));
            } else {
                queue.add(null);
            }
        }
    }

    /**
     * 根据先序队列来反序列化
     */
    private static BinaryTreeNode buildByPreQueue(Queue<String> queue) {
        if (null == queue || queue.isEmpty()) {
            return null;
        }
        return buildByPre(queue);
    }

    private static BinaryTreeNode buildByPre(Queue<String> queue) {
        String val = queue.poll();
        if (null == val) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode(Integer.valueOf(val));
        head.left = buildByPre(queue);
        head.right = buildByPre(queue);
        return head;
    }

    /**
     * 根据后序队列来反序列化
     */
    private static BinaryTreeNode buildByPosQueue(Queue<String> queue) {
        if (null == queue || queue.isEmpty()) {
            return null;
        }
        // 后序列 = 左右中， 翻转过来就是中右左
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return buildByPos(stack);
    }

    private static BinaryTreeNode buildByPos(Stack<String> stack) {
        String val = stack.pop();
        if (null == val) {
            return null;
        }
        BinaryTreeNode head = new BinaryTreeNode(Integer.valueOf(val));
        head.right = buildByPos(stack);
        head.left = buildByPos(stack);
        return head;
    }

    /**
     * 根据bfs序队列来反序列化
     */
    private static BinaryTreeNode buildByLevelQueue(Queue<String> queue) {
        if (null == queue || queue.isEmpty()) {
            return null;
        }
        Queue<BinaryTreeNode> nodeQueue = new LinkedList<>();

        BinaryTreeNode head = levelNode(queue.poll());
        if (null != head) {
            nodeQueue.add(head);
        }

        BinaryTreeNode node = null;
        while (!nodeQueue.isEmpty()) {
            node = nodeQueue.poll();
            // left
            node.left = levelNode(queue.poll());
            if (null != node.left) {
                nodeQueue.add(node.left);
            }
            // right
            node.right = levelNode(queue.poll());
            if (null != node.right) {
                nodeQueue.add(node.right);
            }
        }
        return head;
    }

    private static BinaryTreeNode levelNode(String val) {
        if (null == val) {
            return null;
        }
        return new BinaryTreeNode(Integer.valueOf(val));
    }


}
