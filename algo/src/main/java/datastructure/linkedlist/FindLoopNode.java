package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;

/**
 * 给出一个环形链表，找出环形链表里第一个入环节点
 * 1>2>3>4>5>6>7
 *     ^       v
 *     <<<<<<<<
 * 从3开始就是一个环，那么3就是入环的第一个节点
 */
public class FindLoopNode {

    public static Node getLoopNode(Node head) {
        if (null == head || null == head.next || null == head.next.next) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;

        // 先用快慢指针，快的走两步，慢的走一步，如果有环，必定相遇
        while (slow != fast) {
            if (null == fast.next || null == fast.next.next) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        // 相遇之后，fast重新回到起点，然后改为一次只走一步。最后和slow相遇的地方就是第一个入环节点
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
