package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;

/**
 * 寻找中间节点
 */
public class FindMidNode {

    /**
     * 遍历一个链表，如果长度为奇数返回中节点，如果长度为偶数返回上中节点
     * @param head
     * @return
     */
    public static Node midOrUpMidNode(Node head) {
        if (null == head || null == head.next || null == head.next.next) {
            return head;
        }
        // 链表长度 >= 3
        Node slow = head.next;
        Node fast = head.next.next;
        while (null != fast.next && null != fast.next.next) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 遍历一个链表，如果长度为奇数返回中节点，如果长度为偶数返回下中节点
     * @param head
     * @return
     */
    public static Node midOrDownMidNode(Node head) {
        if (null == head || null == head.next) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next;
        while (null!= fast.next && null != fast.next.next) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 遍历一个链表，如果长度为奇数返回中节点前一个，如果长度为偶数返回上中节点前一个
     * @param head
     * @return
     */
    public static Node midOrUpMidPreviousNode(Node head) {
        if (null == head || null == head.next || null == head.next.next) {
            return null;
        }
        // 链表长度 >= 3
        Node slow = head;
        Node fast = head.next.next;
        while (null != fast.next && null != fast.next.next) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 遍历一个链表，如果长度为奇数返回中节点前一个，如果长度为偶数返回上中节点前一个
     * @param head
     * @return
     */
    public static Node midOrDownMidPreviousNode(Node head) {
        if (null == head || null == head.next) {
            return null;
        }
        if (null == head.next.next) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (null!= fast.next && null != fast.next.next) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
