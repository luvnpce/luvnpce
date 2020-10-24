package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    public static void bfs(BinaryTreeNode head) {
        if (null == head) {
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            System.out.println(node.value);
            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }
        }
    }
}
