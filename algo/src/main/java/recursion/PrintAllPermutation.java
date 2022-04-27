package recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排列
 */
public class PrintAllPermutation {

    public static void main(String[] args) {
        String s = "12345";
        System.out.println(printPermutation(s));
        System.out.println("====================");
        System.out.println(printPermutation2(s));
    }

    /**
     * 使用交换的方式
     * 在每个位置上，尝试和每个数都交换一边
     */
    public static List<String> printPermutation2(String s) {
        char[] chars = s.toCharArray();
        List<String> ans = new ArrayList<>();
        doPermutationRecursion2(chars, 0, ans);
        return ans;
    }

    private static void doPermutationRecursion2(char[] chars, int index, List<String> ans) {
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            doPermutationRecursion2(chars, index + 1, ans);
            // 恢复现场
            swap(chars, index, i);
        }
    }

    /**
     * 暴力递归
     * 从每个位置上选择是否使用当前位置的字符
     * 有一个boolean[]来记录每个位置的字符是否被使用过
     */
    public static List<String> printPermutation(String s) {
        char[] chars = s.toCharArray();
        boolean[] used = new boolean[chars.length];
        List<String> ans = new ArrayList<>();
        String path = "";
        doPermutationRecursion(chars, used, 0, path, ans);
        return ans;
    }

    private static void doPermutationRecursion(char[] chars, boolean[] used, int index, String path, List<String> ans) {
        if (index == chars.length) {
            ans.add(path);
            return;
        }
        for (int i = 0; i < chars.length; i++) {
            if (!used[i]) {
                used[i] = true;
                doPermutationRecursion(chars, used, index + 1, path + chars[i], ans);
                // 恢复现场
                used[i] = false;
            }
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
