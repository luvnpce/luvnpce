package trivia;

import java.util.Stack;

/**
 * 只用递归来翻转一个栈
 */
public class ReverseStack {

    public static void reverse(Stack stack) {
        if (stack.isEmpty()) {
            return;
        }
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    /**
     * 把stack里最底下的取出来并返回
     */
    public static int f(Stack<Integer> stack) {
        Integer result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

}
