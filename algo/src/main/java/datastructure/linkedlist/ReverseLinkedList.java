package datastructure.linkedlist;

import datastructure.linkedlist.structure.DoubleNode;
import datastructure.linkedlist.structure.Node;

import static utils.LinkedListUtils.*;

/**
 * 翻转链表
 */
public class ReverseLinkedList {

    public static void main(String[] args) {

        System.out.println("==========翻转单向链表===========");
        Node single = assembleSingle();
        printList(single);
        // 翻转单向链表
        Node singleResult = reverseSingly(single);
        printList(singleResult);
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

}
