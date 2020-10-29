package unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UnionFind {

    public static class UnionSet<V> {
        // value对应节点
        public HashMap<V, Node<V>> nodes;
        // 父节点map
        public HashMap<Node<V>, Node<V>> parents;
        // 大小
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V val : values) {
                Node<V> node = new Node<>(val);
                nodes.put(val, node);
                // 自己的父节点就是自己
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node<V> findParent(Node<V> node) {
            Stack<Node<V>> stack = new Stack<>();
            // 找出node的头节点
            while (node != parents.get(node)) {
                stack.push(node);
                node = parents.get(node);
            }
            // 优化，把这一路上访问的到节点，都指向头节点
            while(!stack.isEmpty()) {
                parents.put(stack.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(V n1, V n2) {
            if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
                return false;
            }
            Node<V> parent1 = findParent(nodes.get(n1));
            Node<V> parent2 = findParent(nodes.get(n2));
            return parent1 == parent2;
        }

        public void union(V n1, V n2) {
            if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
                return;
            }
            Node<V> parent1 = findParent(nodes.get(n1));
            Node<V> parent2 = findParent(nodes.get(n2));
            if (parent1 != parent2) {
                Integer size1 = sizeMap.get(parent1);
                Integer size2 = sizeMap.get(parent2);
                if (size1 > size2) {
                    // n1集比n2集要大，所以n2加入n1
                    parents.put(parent2, parent1);
                    sizeMap.put(parent1, size1 + size2);
                    sizeMap.remove(parent2);
                } else {
                    // n2集比n1集要大，所以n1加入n2
                    parents.put(parent1, parent2);
                    sizeMap.put(parent2, size1 + size2);
                    sizeMap.remove(parent1);
                }
            }
        }



    }

    public static class Node<V> {
        public V value;

        public Node(V val) {
            this.value = val;
        }
    }
}
