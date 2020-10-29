package graph;

import graph.structure.Graph;
import graph.structure.Node;

import java.util.*;

/**
 * 拓扑排序算法（必须单向无环图）
 * 1. 在图中找到所有入度为0的Node输出
 * 2. 把所有入度为0的Node在图中删掉，继续找入度为0的Node输出，周而复始
 * 3. 图的所有Node都被删除后，依次输出的顺序就是拓扑顺序
 *
 */
public class TopologySort {

    public static List<Node> sort(Graph graph) {
        // key：某一个node
        // value：剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInQueue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }

        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, next.in - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
