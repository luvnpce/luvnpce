package utils;

import datastructure.linkedlist.structure.Node;

public class LinkedListUtils {

    /**
     * 组装一个单向链表
     * @return
     */
    public static Node assembleSingle() {
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
    public static Node assembleSingleRandom() {

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
    public static void printList(Node singleResult) {
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
}
