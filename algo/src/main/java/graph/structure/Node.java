package graph.structure;

import java.util.ArrayList;

public class Node {
    public int value;
    // 入度：进来的路的数量
    public int in;
    // 出度：出去的路的数量
    public int out;
    // 直接邻居：从这个Node，可以一步到达的Node的集合
    // nexts.size == out
    public ArrayList<Node> nexts;
    // 路：从这个Node出去的路的集合
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
