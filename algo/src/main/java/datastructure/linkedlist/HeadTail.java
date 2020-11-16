package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;
import utils.LinkedListUtils;

/**
 * 给定一个单项链表
 * arr = 1->2->3->4->5
 * 将其重新排列后变为
 * arr = 1->5->2->4->3
 * 首尾按顺序拼接
 */
public class HeadTail {

    public static void main(String[] args) {
        Node head;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        LinkedListUtils.printList(head);
        process(head);
        LinkedListUtils.printList(head);
    }

    public static Node process(Node head) {
        if (null == head) {
            return null;
        }
        // 找到链表的中间节点
        Node slow = head;
        Node fast = head;
        while (null != fast.next && null != fast.next.next) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 从中间节点开始，把下半部分的链表翻转
        Node prev = null;
        Node next = null;
        while (null != slow) {
            next = slow.next;
            slow.next = prev;
            prev = slow;
            slow = next;
        }
        // 此时下半部分的链表已经翻转，prev指向链表的尾部
        // 开始拼接
        Node tail = prev;
        Node front = head;
        Node frontNext = null;
        Node tailNext = null;
        while (null != front && null != tail) {
            frontNext = front.next;
            tailNext = tail.next;
            front.next = tail;
            tail.next = frontNext;
            front = frontNext;
            tail = tailNext;
        }
        return head;
    }
}
