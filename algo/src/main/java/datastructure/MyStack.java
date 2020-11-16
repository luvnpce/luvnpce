package datastructure;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 使用队列来实现栈
 */
public class MyStack<T> {

    private Queue<T> queue;
    private Queue<T> helper;

    public MyStack() {
        queue = new LinkedList<>();
        helper = new LinkedList<>();
    }

    public void push(T val) {
        queue.offer(val);
    }

    public T pop() {
        if (queue.isEmpty()) {
            throw new RuntimeException();
        }
        while (queue.size() > 1) {
            helper.offer(queue.poll());
        }
        T ans = queue.poll();
        Queue<T> tmp = helper;
        helper = queue;
        queue = tmp;
        return ans;
    }

    public T peek() {
        if (queue.isEmpty()) {
            throw new RuntimeException();
        }
        while (queue.size() > 1) {
            helper.offer(queue.poll());
        }
        T ans = queue.poll();
        helper.offer(ans);
        Queue<T> tmp = helper;
        helper = queue;
        queue = tmp;
        return ans;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("start");

        int times  = 10000;
        int max = 10000;
        MyStack<Integer> stack = new MyStack<>();
        Stack<Integer> test = new Stack<>();
        for (int i = 0; i < times; i++) {
            if (stack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                    return;
                }
                int num = (int) (Math.random() * max);
                stack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    stack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!stack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!stack.pop().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (stack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }
        System.out.println("done");
    }


}
