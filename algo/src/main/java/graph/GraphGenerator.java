package graph;

import graph.structure.Edge;
import graph.structure.Graph;
import graph.structure.Node;

public class GraphGenerator {

    /**
     * [weight, from, to]
     */
    public static Graph generate(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            Integer weight = matrix[i][0];
            Integer from = matrix[i][1];
            Integer to = matrix[i][2];

            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fNode = graph.nodes.get(from);
            Node tNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fNode, tNode);
            fNode.nexts.add(tNode);
            fNode.out++;
            tNode.in++;
            fNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
