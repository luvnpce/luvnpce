package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;

import static utils.LinkedListUtils.generateSingleByRandom;
import static utils.LinkedListUtils.printList;

/**
 * 删除指定的值的节点
 */
public class DeleteNode {

    public static void main(String[] args) {
        System.out.println("==========单向链表删除指定的数===========");
        Node random = generateSingleByRandom();
        printList(random);
        int val = (int) (Math.random() * 10);
        System.out.println("删除" + val);
        Node randomResult = deleteFromSingle(random, val);
        printList(randomResult);
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
}
