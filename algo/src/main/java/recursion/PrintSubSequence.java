package recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印一个字符串的全部子序列
 * ie: "123"
 * ========
 * ""
 * "1"
 * "2"
 * "3"
 * "12"
 * "23"
 * "13"
 * "123"
 */
public class PrintSubSequence {

    public static void main(String[] args) {
        String s = "123";
        List<String> strings = printSubSequence(s);
        System.out.println(strings);
    }

    private static List<String> printSubSequence(String s) {
        char[] chars = s.toCharArray();
        List<String> ans = new ArrayList<>();
        String path = "";
        doSubSequenceRecursion(chars, 0, path, ans);
        return ans;
    }

    private static void doSubSequenceRecursion(char[] chars, int index, String path, List<String> ans) {
        if (index == chars.length) {
            ans.add(path);
            return;
        }
        doSubSequenceRecursion(chars, index + 1, path, ans);
        doSubSequenceRecursion(chars, index + 1, path + chars[index], ans);
    }
}
