package lesson.lesson8;

import java.util.LinkedList;

/**
 * 给定一个字符串str，str表示一个公式
 * 公式里可能有整数，加减乘除符号和左右括号
 * 返回公式的计算结果，难点在于括号可能嵌套很多层
 *
 * 如果要表示负数，必须用括号括起来，ie: 4*(-3)，但是如果负数作为公式的开头或者括号部分的开头，则可以不用括号
 * ie: -3*4 或者 (-3*4)
 */
public class Lesson8_1 {

    public static int solution(String str) {
        return doSolution(str.toCharArray(), 0)[0];
    }

    /**
     * int[0] = 这一段计算出的结果
     * int[1] = 这一段计算到了哪个位置
     */
    public static int[] doSolution(char[] chars, int index) {
        LinkedList<String> list = new LinkedList<>();
        int[] bracket = null;   // 用来记录括号里的结果
        int cur = 0;            // 用来记录当前数字
        while (index < chars.length && chars[index] != ')') {
            if (chars[index] >= '1' && chars[index] <= '9') {
                // 当前字符是数字
                cur = cur * 10 + chars[index] - '0';
            } else if (chars[index] != '(') {
                // 当前字符是运算符号
                pushNum(list, cur);
                list.addLast(String.valueOf(chars[index]));
                cur = 0;        // 重置当前数字
            } else {
                // 当前字符是左括号'('
                bracket = doSolution(chars, index + 1);
                cur = bracket[0];
                index = bracket[1];
            }
            index++;
        }
        // 来到这代表数组已经遍历完了，或者遇到了一个有括号')'
        pushNum(list, cur);
        return new int[] {getNum(list), index};
    }

    private static void pushNum(LinkedList<String> list, int num) {
        if (!list.isEmpty()) {
            String top = list.pollLast();
            if (top.equals("+") || top.equals("-")) {
                // 顶部的符号是+或者-，那么不需要提前做运算，直接压回去
                list.addLast(top);
            } else {
                int prev = Integer.valueOf(list.pollLast());
                num = top.equals("*") ? (prev * num) : (prev / num);
            }
        }
        list.addLast(String.valueOf(num));
    }

    private static int getNum(LinkedList<String> list) {
        int res = 0;
        boolean isAdd = true;
        String cur = null;
        while (!list.isEmpty()) {
            cur = list.pollFirst();
            if (cur.equals("+")) {
                isAdd = true;
            } else if (cur.equals("-")) {
                isAdd = false;
            } else {
                int num = Integer.valueOf(cur);
                res += isAdd ? num : (-num);
            }
        }
        return res;
    }

}
