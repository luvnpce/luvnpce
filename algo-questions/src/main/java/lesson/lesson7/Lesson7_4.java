package lesson.lesson7;

import java.util.Set;

/**
 * 数组arr是去空，去重单词表 可以使用任意次 返回组成str字符串的方法数
 * ie: str = "aaab", arr = [a, aa, aaa, ab, b]，求能够组成str有多少种方法
 */
public class Lesson7_4 {

    /**
     * 解法1：从左往右遍历
     * index从0开始，到size结束，看从index开始的没一个前缀字符串是否在字典当中
     * 如果在字典中，再看后续字符串是否在字典中
     * 如果不在则跳过
     * =================
     * 时间复杂度：
     * 1.首先是用index遍历，O(N)
     * 2.然后组装前缀字符串，O(N)
     * 3.如果当前前缀字符串匹配了，要去匹配后续的，O(N)
     * O(N^3)，主要是多了2)组装的时间消耗
     */
    public static int brute(String str, int index, Set<String> arr) {
        if (index == str.length()) {
            return 1;
        }
        int result = 0;
        for (int end = index; end < str.length(); end++) {
            // str[index...end]的字符串是否在字典中
            String temp = str.substring(index, end - 1);
            if (arr.contains(temp)) {
                // 在字典中，那么去计算从str[end + 1...size]后续的字符串
                result += brute(str, end + 1, arr);
            }
        }
        return result;
    }

    public static class TrieNode {
        public boolean end;
        public TrieNode[] next;

        public TrieNode() {
            end = false;
            next = new TrieNode[26];
        }
    }

    /**
     * 解法2：前缀树
     * 省去上面组装前缀字符串的时间消耗，直接在前缀树上面移动
     */
    public static int optimize(String str, String[] arr) {
        if (null == str || str.length() == 0 || null == arr || arr.length == 0) {
            return 0;
        }
        // 构建前缀树
        TrieNode root = new TrieNode();
        for (String s : arr) {
            char[] chars = s.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (node.next[index] == null) {
                    node.next[index] = new TrieNode();
                }
                node = node.next[index];
            }
            node.end = true;
        }
        return doOptimize(str.toCharArray(), root, 0);
    }

    private static int doOptimize(char[] str, TrieNode root, int index) {
        if (index == str.length) {
            return 1;
        }
        int result = 0;
        TrieNode node = root;
        for (int end = index; end < str.length; end++) {
            int path = str[end] - 'a';
            if (node.next[path] == null) {
                break;
            }
            node = node.next[path];
            if (node.end) {
                // 字典里有当前前缀字符串
                result += doOptimize(str, node, end + 1);
            }
        }
        return result;
    }
}
