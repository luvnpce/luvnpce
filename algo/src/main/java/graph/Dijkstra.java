package graph;

import graph.structure.Edge;
import graph.structure.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 最小生成树算法之Dijkstra
 *  - 得保证没有负的权值
 *  - 给定一个出发点，求出从出发点到所有节点的最短距离是多少
 *  - 如果无法到达某个节点，那么和那个节点的最短距离就是正无穷
 *
 * 算法流程
 *  - 选一个出发节点A，准备一张表，用来记录A到其他所有节点的距离
 *      - 默认A到A的距离为0
 *      - 默认A到其他节点的距离为正无穷
 *  - 从表里选出距离最小的那个节点，查看这个节点所能到达的其他节点，
 *  计算从A到自己，再到那些节点距离，如果比表里记录的要短，则更新表。
 *  - 再选下一个距离最短的节点，然后重复上一步。
 */
public class Dijkstra {

    /**
     * 使用HashMap，每次从map里取未处理的最短距离节点需要O(N)
     */
    public static HashMap<Node, Integer> dijkstra1(Node root) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(root, 0);
        HashSet<Node> selected = new HashSet<>();
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selected);
        while (minNode != null) {
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                int dist = distance + edge.weight;
                if (!distanceMap.containsKey(edge.to)) {
                    distanceMap.put(edge.to, dist);
                } else {
                    if (distanceMap.get(edge.to) > dist) {
                        distanceMap.put(edge.to, dist);
                    }
                }
            }
            selected.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selected);
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selected) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selected.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    /**
     * 改进后的dijkstra算法，使用自定义的堆，每次取未处理的最短距离节点只需要O(logN)
     *  从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
     */
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Node[] nodes; // 实际的堆结构
        // key 某一个node， value 上面堆中的位置
        private HashMap<Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index])
                    < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest])
                        < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }
}
