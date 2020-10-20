package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交则返回Null。
 *
 */
public class FindFirstIntersectNode {


    /**
     * 解法：
     *  - 需要使用FindLoopNode里面的算法，找入环的第一个节点
     *
     *  首先两个链表可能有环可能无环，会出现以下几种情况
     *  1. 一个有环一个无环：那么这两个链表必定不相交
     *  2. 两个链表都无环：如果两个链表的尾节点相同，那么必定相交。
     *     直接查两个节点的长度之差，长的一方先走长出的X个节点，等两个链表同样长度后，再一起走，相交的必是第一个节点
     *  3. 两个链表都有环：那么可以通过两边的入环节点判断
     *     如果两个链表的入环节点相同，那么
     *          3.1 两个节点是在入环前相交的，可以省略掉换，直接通过方法2的方式查找
     *     如果两个链表的入环节点不相同，那么
     *          3.2.1 两个链表不相交
     *          3.2.2 两个链表相交，但是是在环里相交，那么随意返回哪个入环节点都行
     */
    public static Node find(Node head1, Node head2) {
        if (null == head1 || null == head2) {
            return null;
        }

        Node loop1 = FindLoopNode.getLoopNode(head1);
        Node loop2 = FindLoopNode.getLoopNode(head2);

        if (null == loop1 && null == loop2) {
            // 第二种情况
            return noLoop(head1, head2);
        }
        if (null != loop1 && null != loop2) {
            // 第三种情况
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 第一种情况
        return null;
    }

    /**
     * 两个链表都有环，判断是否有相交
     */
    private static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2) {
            // 3.1
            cur1 = head1;
            cur2 = head2;
            int diff = 0;
            while (loop1 != cur1) {
                diff++;
                cur1 = cur1.next;
            }
            while (loop2 != cur2) {
                diff--;
                cur2 = cur2.next;
            }
            cur1 = diff > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            diff = Math.abs(diff);
            while (diff > 0) {
                cur1 = cur1.next;
                diff--;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            // 3.2.1或者3.2.2
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    /**
     * 两个链表都无环，判断是否有相交
     */
    private static Node noLoop(Node head1, Node head2) {
        int diff = 0;
        Node cur1 = head1;
        Node cur2 = head2;
        while (null != cur1.next) {
            diff++;
            cur1 = cur1.next;

        }
        while (null != cur2.next) {
            diff--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        // 如果diff > 0，代表链表1更长
        // 如果diff < 0, 代表链表2更长
        // 如果diff == 0, 代表一样长
        cur1 = diff > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        diff = Math.abs(diff);
        while (diff > 0) {
            cur1 = cur1.next;
            diff--;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(find(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(find(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(find(head1, head2).value);

    }
}
