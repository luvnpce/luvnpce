package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;
import utils.LinkedListUtils;

/**
 * 把链表根据给的数值x区分为[小于x，等于x，大于x]的顺序
 * 4->2->3->5->6->1->3->0->4, x = 4
 * 2->3->1->0->3->4->4->5->6
 */
public class SmallerEqualBigger {

    public static void main(String[] args) {
        Node head = LinkedListUtils.generateSingleByRandom();
        LinkedListUtils.printList(head);
        int val = 5;
        head = partition2(head, val);
        System.out.println("val = " + val);
        LinkedListUtils.printList(head);
    }

    /**
     * 使用6个引用，分别代表
     * - 小于区的头和尾
     * - 等于区的头和尾
     * - 大于区的头和尾
     * 遍历链表，把节点连接到对应的区域
     * 最后将三个区域拼装后返回
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static Node partition2(Node head, int val) {
        Node sHead = null;
        Node sTail = null;
        Node eHead = null;
        Node eTail = null;
        Node bHead = null;
        Node bTail = null;

        while (null != head) {
            if (head.value > val) {
                if (null == bHead) {
                    bHead = head;
                    bTail = head;
                } else {
                    bTail.next = head;
                    bTail = bTail.next;
                }
            } else if (head.value < val) {
                if (null == sHead) {
                    sHead = head;
                    sTail = head;
                } else {
                    sTail.next = head;
                    sTail = sTail.next;
                }
            } else {
                if (null == eHead) {
                    eHead = head;
                    eTail = head;
                } else {
                    eTail.next = head;
                    eTail = eTail.next;
                }
            }
            Node tmp = head.next;
            head.next = null;
            head = tmp;
        }

        head = bHead;
        if (null != eHead) {
            eTail.next = bHead;
            head = eHead;
        }
        if (null != sHead) {
            sTail.next = null != eHead ? eHead : bHead;
            head = sHead;
        }

        return head;
    }

    /**
     * 利用数组，把节点的值都放进数组，然后进行荷兰国旗partition，最后重新给链表赋值
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    private static Node partition(Node head, int val) {
        Node current = head;
        int size = 0;
        while (null != current) {
            size++;
            current = current.next;
        }

        Node[] arr = new Node[size];
        current = head;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = current;
            current = current.next;
        }
        helper(arr, val);
        for (int i = 1; i < arr.length; i++) {
            arr[i-1].next = arr[i];
        }
        arr[arr.length - 1].next = null;
        return arr[0];
    }

    /**
     *      [0, 1, 2, 3, 4]
     * less                 more
     * @param arr
     * @param val
     */
    private static void helper(Node[] arr, int val) {
        int less = -1;
        int more = arr.length;

        int i = 0;
        while (i < more) {
            if (arr[i].value < val) {
                swap(arr, ++less, i++);
            } else if (arr[i].value > val) {
                swap(arr, --more, i);
            } else {
                i++;
            }
        }
    }

    private static void swap(Node[] arr, int i, int j) {
        Node tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
