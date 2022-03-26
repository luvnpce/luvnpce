package test;

import java.util.HashMap;

public class MyTrie {

    private TrieNode root;

    public MyTrie() {
        this.root = new TrieNode();
    }


    public void insert(String word) {
        if (null == word) {
            return;
        }
        TrieNode current = this.root;
        current.pass++;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = (int) chars[i];
            if (!current.path.containsKey(index)) {
                current.path.put(index, new TrieNode());
            }
            current = current.path.get(index);
            current.pass++;
        }
        current.end++;
    }

    public boolean search(String word) {
        if (null == word) {
            return false;
        }
        TrieNode current = this.root;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = (int) chars[i];
            if (!current.path.containsKey(index)) {
                return false;
            }
            current = current.path.get(index);
        }
        return current.end > 0;
    }

    public boolean prefix(String prefix) {
        if (null == prefix) {
            return false;
        }
        TrieNode current = this.root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = (int) chars[i];
            if (!current.path.containsKey(index)) {
                return false;
            }
            current = current.path.get(index);
        }
        return current.pass > 0;
    }

    public void remove(String word) {
        if (!search(word)) {
            return;
        }
        TrieNode current = this.root;
        current.pass--;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            TrieNode next = current.path.get(index);
            if (--next.pass == 0) {
                current.path.remove(index);
                return;
            } else {
                current = next;
            }
        }
        current.end--;
    }

    class TrieNode {
        // 经过的次数
        public int pass;
        // 结束的次数
        public int end;
        // 路径
        public HashMap<Integer, TrieNode> path;

        public TrieNode() {
            this.pass = 0;
            this.end = 0;
            this.path = new HashMap<>();
        }
    }
}
