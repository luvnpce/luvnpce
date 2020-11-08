package datastructure.binarytree;

import datastructure.binarytree.structure.BinaryTreeNode;
import utils.BinaryTreeUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 找出二叉树中哪一层最宽
 *               1
 *             /   \
 *            2     3
 *           / \   / \
 *          4   5  6  7
 *         /     \
 *        8       9
 */
public class TreeMaxWidth {

    /**
     * 使用map来记录每个节点的层级
     */
    public static int maxWidthUseMap(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        // 当前统计的层
        int curLevel = 1;
        // 当前统计的层的宽度
        int curLevelNodes = 0;
        // 返回值：最大的宽度
        int max = 0;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        // key = 节点， value = 节点在第几层
        Map<BinaryTreeNode, Integer> map = new HashMap<>();
        queue.add(head);
        map.put(head, 1);
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            // 找出当前节点在第几层
            int level = map.get(node);
            // 找出当前层的小孩，并放入queue和map中
            if (null != node.left) {
                map.put(node.left, level + 1);
                queue.add(node.left);
            }
            if (null != node.right) {
                map.put(node.right, level + 1);
                queue.add(node.right);
            }

            if (level == curLevel) {
                curLevelNodes++;
            } else {
                curLevel++;
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 1;
            }
        }
        // 最后还要统计一次，因为whileloop出来前是不会进到else里面进行统计的
        max = Math.max(max, curLevelNodes);
        return max;
    }

    public static int maxWidthNoMap(BinaryTreeNode head) {
        if (null == head) {
            return 0;
        }
        // 当前层的最右节点
        BinaryTreeNode curEnd = head;
        // 下一层的最右节点
        BinaryTreeNode nextEnd = null;
        // 当前统计层的最大宽度
        int curLevelNodes = 0;
        // 返回值：最大宽度
        int max = 0;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            if (null != node.left) {
                queue.add(node.left);
                // 这里我们不需要关心node.left是不是下一层的最右节点，因为如果不是，nextEnd会被后面的更新掉
                nextEnd = node.left;
            }
            if (null != node.right) {
                queue.add(node.right);
                // 这里就等于把方面赋值的nextEnd更新了，但是我们也不需要关心node.right是不是最右
                // 如果不是那么就意味着这一层还没结束，那么下一个node会把自己的child赋值给nextEnd
                nextEnd = node.right;
            }

            curLevelNodes++;
            if (curEnd == node) {
                max = Math.max(max, curLevelNodes);
                curEnd = nextEnd;
                curLevelNodes = 0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = BinaryTreeUtils.generateRandomBT(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }

}
