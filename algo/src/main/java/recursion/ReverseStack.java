package recursion;

import java.util.Stack;

/**
 * 逆序一个栈
 * 不能申请额外的数据结构
 * 只能使用递归函数
 */
public class ReverseStack {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        reverse(stack);
        System.out.println(stack);
    }

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int bottom = popBottom(stack);
        reverse(stack);
        stack.push(bottom);
    }

    public static int popBottom(Stack<Integer> stack) {
        Integer top = stack.pop();
        if (stack.isEmpty()) {
            return top;
        } else {
            int bottom = popBottom(stack);
            stack.push(top);
            return bottom;
        }
    }
}
