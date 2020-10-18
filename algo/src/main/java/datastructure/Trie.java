package datastructure;

import java.util.HashMap;

/**
 * 前缀树
 * 1. 单个字符中，字符从前到后的加到一个多叉树上
 * 2. 字符放在path，节点上有专属的数据项
 *      - pass：通过次数
 *      - end：结尾次数
 * 3. 所有样本都这样添加，如果没有路就新建，有路就复用
 * 4. 沿途节点的pass值增加1，每个字符串结束时来到的节点end值加1
 */
public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        if (null == word) {
            return;
        }
        TrieNode current = root;
        current.pass++;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int key = (int) chars[i];
            if (!current.path.containsKey(key)) {
                current.path.put(key, new TrieNode());
            }
            current = current.path.get(key);
            current.pass++;
        }
        current.end++;
    }

    public int search(String word) {
        if (null == word) {
            return 0;
        }
        TrieNode current = root;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int key = (int) chars[i];
            if (!current.path.containsKey(key)) {
                return 0;
            }
            current = current.path.get(key);
        }
        return current.end;
    }

    public int prefix(String prefix) {
        if (null == prefix) {
            return 0;
        }
        TrieNode current = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int key = (int) chars[i];
            if (!current.path.containsKey(key)) {
                return 0;
            }
            current = current.path.get(key);
        }
        return current.pass;
    }

    public void delete(String word) {
        if (search(word) > 0) {
            TrieNode current = root;
            current.pass--;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int key = (int) chars[i];
                if (--current.path.get(key).pass == 0) {
                    current.path.remove(key);
                } else {
                    current = current.path.get(key);
                }
            }
            current.end--;
        }
    }


    public class TrieNode {

        private int pass;
        private int end;
        private HashMap<Integer, TrieNode> path;

        public TrieNode() {
            this.pass = 0;
            this.end = 0;
            path = new HashMap<>();
        }

    }

}
