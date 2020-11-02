package trivia;

import java.util.HashMap;

public class LRU {

    public HashMap<Integer, Node> map;
    public int capacity;
    public int size;
    public Node head;
    public Node tail;

    public LRU(int n) {
        this.capacity = n;
        this.size = 0;
        this.map = new HashMap<>();
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        this.head.next = tail;
        this.head.prev = null;
        this.tail.prev = head;
        this.tail.next = null;


    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        deleteNode(node);
        addNodeToHead(node);
        return node.value;
    }

    public void add(int key, int value) {
        if (!map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            deleteNode(node);
            addNodeToHead(node);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            if (size < capacity) {
                size++;
            } else {
                map.remove(tail.prev.key);
                deleteNode(tail.prev);
            }
            addNodeToHead(node);
        }
    }

    private void addNodeToHead(Node node) {
        node.next = head.next;
        node.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    private void deleteNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    public class Node {
        public int key;
        public int value;
        public Node prev;
        public Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
