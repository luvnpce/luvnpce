package datastructure;

import java.util.Stack;

/**
 * 使用栈来实现队列
 */
public class MyQueue {

    private Stack<Integer> push;
    private Stack<Integer> pop;

    public MyQueue() {
        push = new Stack<>();
        pop = new Stack<>();
    }

    public void offer(int num) {
        push.push(num);
        fillPop();
    }

    public int poll() {
        if (pop.isEmpty() && push.isEmpty()) {
            throw new RuntimeException();
        }
        fillPop();
        return pop.pop();
    }

    public int peak() {
        if (pop.isEmpty() && push.isEmpty()) {
            throw new RuntimeException();
        }
        fillPop();
        return pop.peek();
    }

    private void fillPop() {
        if (pop.isEmpty()) {
            while(!push.isEmpty()) {
                pop.push(push.pop());
            }
        }
    }
}
