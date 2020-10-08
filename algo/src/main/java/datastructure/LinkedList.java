package datastructure;

public class LinkedList {

    public static void main(String[] args) {

        System.out.println("==========翻转单向链表===========");
        Node single = assembleSingle();
        printList(single);
        // 翻转单向链表
        Node singleResult = reverseSingly(single);
        printList(singleResult);

        System.out.println("==========单向链表删除指定的数===========");
        Node random = assembleSingleRandom();
        printList(random);
        int val = (int) (Math.random() * 10);
        System.out.println("删除" + val);
        Node randomResult = deleteFromSingle(random, val);
        printList(randomResult);
    }

    /**
     * 翻转单向链表
     * @param head
     * @return
     */
    public static Node reverseSingly(Node head) {
        Node prev = null;
        Node next = null;

        while (null != head) {
            next = head.next;

            head.next = prev;
            prev = head;

            head = next;
        }
        return prev;
    }

    /**
     * 翻转双向链表
     * @param head
     * @return
     */
    public static DoubleNode reverseDoubly(DoubleNode head) {
        DoubleNode prev = null;
        DoubleNode next = null;

        while (null != head) {
            next = head.next;

            head.next = prev;
            head.prev = next;
            prev = head;

            head = next;
        }
        return prev;
    }

    /**
     * 单向链表删除指定的数（递归）
     * @param head
     * @param val
     * @return
     */
    private static Node deleteFromSingle(Node head, int val) {
        if (null == head) {
            return head;
        }
        if (head.value == val) {
            head = head.next;
            return deleteFromSingle(head, val);
        } else {
            head.next = deleteFromSingle(head.next, val);
            return head;
        }
    }

    /**
     * 单向链表删除指定的数（while）
     * @param head
     * @param val
     * @return
     */
    private static Node deleteFromSingleV2(Node head, int val) {
        while (null != head) {
            if (head.value != val) {
                break;
            }
            head = head.next;
        }
        Node prev = head;
        Node current = head;
        while (null != current) {
            if (current.value == val) {
                prev.next = current.next;
            } else {
                prev = current;
            }
            current = current.next;
        }
        return head;
    }

    /**
     * 组装一个单向链表
     * @return
     */
    private static Node assembleSingle() {

        Node head = new Node(0);
        Node current = head;
        for (int i = 1; i < 10; i++) {
            Node node = new Node(i);
            current.next = node;
            current = node;
        }
        return head;
    }

    /**
     * 组装一个随机值的单向链表
     * @return
     */
    private static Node assembleSingleRandom() {

        Node head = new Node((int)(Math.random() * 10));
        Node current = head;
        for (int i = 1; i < 10; i++) {
            Node node = new Node((int)(Math.random() * 10));
            current.next = node;
            current = node;
        }
        return head;
    }

    /**
     * 打印单向链表
     * @param singleResult
     */
    private static void printList(Node singleResult) {
        Node start = singleResult;
        StringBuilder sb = new StringBuilder();
        while (start != null) {
            sb.append(start.value);
            start = start.next;
            if (start != null) {
                sb.append("->");
            }
        }
        System.out.println(sb.toString());
    }

    public static class Node {

        public int value;

        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class DoubleNode {

        public int value;

        public DoubleNode next;

        public DoubleNode prev;

        public DoubleNode(int value) {
            this.value = value;
        }
    }

}
