package datastructure.linkedlist;

import datastructure.linkedlist.structure.Node;
import utils.LinkedListUtils;

import java.util.Stack;

/**
 * 判断链表是不是回文数结构
 * aba
 * abcba
 * abccba
 */
public class Palindrome {

    public static void main(String[] args) {
        Node head;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        System.out.println(isPalindromeV2(head));
        LinkedListUtils.printList(head);
    }

    /**
     * 使用stack，空间复杂度O(N)
     * @param head
     * @return
     */
    public static boolean isPalindrome(Node head) {
        Stack<Node> stack = new Stack<>();
        Node current = head;

        while (null != current) {
            stack.push(current);
            current = current.next;
        }

        while (null != head) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 1. 遍历链表，把从中间到后半段的链表翻转
     * 2. 然后从头、尾同时开始遍历，如果值都一样则代表是palindrome
     * 3. 记得把链表恢复原样
     * @param head
     * @return
     */
    public static boolean isPalindromeV2(Node head) {
        if (null == head || null == head.next) {
            return true;
        }
        // 遍历到中间节点
        Node mid = head;
        Node fast = head;
        while (null != fast.next && null != fast.next.next) {
            mid = mid.next;
            fast = fast.next.next;
        }
        // mid已经到达中间节点，开始把后半截翻转
        Node prev = null;
        Node next = null;
        Node current = mid;
        while (null != current) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        // prev已经在最后一个节点
        Node last = prev;
        // 现在head和prev同时遍历，如果他们的值不一样则不是palindrome
        boolean res = true;
        while (null != head && null != prev) {
            if (head.value != prev.value) {
                res = false;
                break;
            }
            head = head.next;
            prev = prev.next;
        }
        // 最后把链表恢复
        prev = null;
        next = null;
        while (null != last) {
            next = last.next;
            last.next = prev;
            prev = last;
            last = next;
        }
        return res;
    }
}
