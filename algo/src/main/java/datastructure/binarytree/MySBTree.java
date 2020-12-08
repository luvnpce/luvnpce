package datastructure.binarytree;

public class MySBTree {

    private SBTNode root;

    private SBTNode rotateLeft(SBTNode cur) {
        SBTNode rightNode = cur.right;
        cur.right = rightNode.left;
        rightNode.left = cur;
        rightNode.size = cur.size;
        cur.size = (null != cur.left ? cur.left.size : 0) + (null != cur.right ? cur.right.size : 0);
        return rightNode;
    }

    private SBTNode rotateRight(SBTNode cur) {
        SBTNode leftNode = cur.left;
        cur.left = leftNode.right;
        leftNode.right = cur;
        leftNode.size = cur.size;
        cur.size = (null != cur.left ? cur.left.size : 0) + (null != cur.right ? cur.right.size : 0);
        return leftNode;
    }

    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public SBTNode<K, V> left;
        public SBTNode<K, V> right;
        public int size;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }


}
