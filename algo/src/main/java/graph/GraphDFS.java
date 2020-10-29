package graph;

import graph.structure.Node;

import java.util.HashSet;
import java.util.Stack;

public class GraphDFS {

    public static void dfs(Node node) {
        if (null == node) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        // dfs在进栈时处理（打印 == 处理）
        System.out.println(node.value);
        while(!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                // 找到还没有去过的节点X，停止遍历当前节点，先去遍历节点X的路 == DFS
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }

    }
}
