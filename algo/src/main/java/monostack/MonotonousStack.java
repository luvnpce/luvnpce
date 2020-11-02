package monostack;

import utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 单调栈
 */
public class MonotonousStack {

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generate(20, 100);
        System.out.println(Arrays.toString(arr));
        System.out.println(isEqual(getNearLessNoRepeat(arr), getNearLess(arr)));
    }

    /**
     * 给定一个数组，返回每个数
     * - 左边离它最近且比它小的一个数
     * - 右边离它最近且比它小的一个数
     * =====================================
     * 流程：
     * 1. 准备一个栈，栈里保证从小（底部）到大（顶部）的顺序
     * 2. 遍历数组，每到一个数 i 时，弹出栈里所有比他大的数
     * 3. 当每个数从栈里弹出时，记录：
     *      - 哪个数让它弹出的，那个数就是右边离它最近且比它小的数
     *      - 栈里在它下面的数就是左边理它最近且比它小的数
     * 4. 把i压入栈中，继续遍历
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (null == arr || arr.length == 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && stack.peek() > arr[i]) {
                Integer pop = stack.pop();
                res[pop][0] = stack.isEmpty() ? -1 : stack.peek();
                res[pop][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            res[pop][0] = stack.isEmpty() ? -1 : stack.peek();
            res[pop][1] = -1;
        }
        return res;
    }

    public static int[][] getNearLess(int[] arr) {
        if (null == arr || arr.length == 0) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && stack.peek().get(0) > arr[i]) {
                List<Integer> pop = stack.pop();
                int lessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popi : pop) {
                    res[popi][0] = stack.isEmpty() ? -1 : lessIndex;
                    res[popi][1] = i;
                }
            }
            // 相等的、比你小的
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> pop = stack.pop();
            int lessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popi : pop) {
                res[popi][0] = stack.isEmpty() ? -1 : lessIndex;
                res[popi][1] = -1;
            }
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }
}
