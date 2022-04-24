package lesson.lesson3;

import java.util.*;

/**
 * 给定三个参数：
 * 二叉树的头节点head
 * 树上某个节点target
 * 正数k
 * 从target开始，可以向上走或者向下走
 * 返回与target的距离是k的所有节点
 */
public class Lesson3_1 {

    public static List<Node> kDistanceNodes(Node root, Node target, int k) {
        // 创建父map，因为Node里没有parent的引用
        Map<Node, Node> parentMap = new HashMap<>();
        parentMap.put(root, null);
        fillParentMap(root, parentMap);

        // bfs
        Set<Node> visited = new HashSet<>();
        Queue<Node> todo = new LinkedList<>();
        List<Node> ans = new ArrayList<>();
        todo.offer(target);
        visited.add(target);
        int curLevel = 0;
        while (!todo.isEmpty()) {
            /**
             * 核心：利用size来标记这一层要处理多少个node
             */
            int size = todo.size();
            while (size-- > 0) {
                Node current = todo.poll();
                // 是否已经走了k步
                if (curLevel == k) {
                    ans.add(current);
                }
                // 处理parent
                if (null != parentMap.get(current) && !visited.contains(parentMap.get(current))) {
                    todo.offer(parentMap.get(current));
                    visited.add(parentMap.get(current));
                }
                // 处理children
                if (null != current.left && !visited.contains(current.left)) {
                    todo.offer(current.left);
                    visited.add(current.left);
                }
                if (null != current.right && !visited.contains(current.right)) {
                    todo.offer(current.right);
                    visited.add(current.right);
                }
            }
            curLevel++;
            if (curLevel > k) {
                break;
            }
        }
        return ans;
    }

    private static void fillParentMap(Node root, Map<Node, Node> parentMap) {
        if (null == root) {
            return;
        }
        if (null != root.left) {
            parentMap.put(root.left, root);
            fillParentMap(root.left, parentMap);
        }
        if (null != root.right) {
            parentMap.put(root.right, root);
            fillParentMap(root.right, parentMap);
        }
    }

    private class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }
}
