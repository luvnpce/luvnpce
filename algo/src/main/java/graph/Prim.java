package graph;

import graph.structure.Edge;
import graph.structure.Graph;
import graph.structure.Node;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树算法之Prim
 * 1. 随便指定一个起始Node A，解锁这个Node A的所有Edge
 * 2. 选择权值最小的那个Edge x
 * 3. 判断这个x的两侧有没有未解锁的Node：
 *      - 如果有，选择这个Edge，同时解锁这个Node以及它的所有Edge
 *      - 重复2~3
 */
public class Prim {

    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);

        HashSet<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            // 随机选择一个Node作为起始
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {
                    pq.add(edge);
                }
            }

            while (!pq.isEmpty()) {
                Edge edge = pq.poll();
                Node to = edge.to;
                if (!nodeSet.contains(to)) {
                    nodeSet.add(to);
                    result.add(edge);
                    for (Edge toEdge : to.edges) {
                        pq.add(toEdge);
                    }
                }
            }
        }
        return result;
    }
}
