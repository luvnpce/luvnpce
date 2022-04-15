package lesson2;

import java.util.HashMap;

/**
 * 已知一个消息流会不断地吐出整数 1~N
 * 但不一定按照顺序一次吐出（视频数据流）
 * 如果上次打印的序号为i，那么当i+1出现时请打印i+i及之后接受过的并且连续的所有数
 * 直到1~N全部接收并打印完
 * 请设计这种接收并打印的结构
 */
public class Lesson2_3 {

    private static class Node {
        public String info;
        public Node next;

        public Node(String str) {
            this.info = str;
        }
    }

    /**
     * 为什么要使用这种数据结构？
     * 因为可以对无用的数据进行删除
     */
    public static class MessageBox {
        /**
         * headMap存放着每一个连续区间开头位置的node
         * tailMap存放着每一个连续区间结束位置的node
         */
        private HashMap<Integer, Node> headMap;
        private HashMap<Integer, Node> tailMap;
        private int waitPoint;

        public MessageBox() {
            headMap = new HashMap<>();
            tailMap = new HashMap<>();
            waitPoint = 1;
        }

        public void receive(int num, String info) {
            if (num < 1) {
                return;
            }
            Node current = new Node(info);
            /**
             * 建立出一个num->num的连续区间
             * current这个node即是一个连续区间开头的node，放到headMap里
             * current也是一个连续区间结尾的node，放到tailMap里
             */
            headMap.put(num, current);
            tailMap.put(num, current);
            /**
             * 查询是否有可以合并的连续区间
             * 假设tailMap里有一个 3->3的连续区间，此时4->4被加进来
             * 那么3->3和4->4合并为3->4
             */
            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = current;
                // 合并后，num-1的这个node不再是结尾的node，所以剔除
                tailMap.remove(num - 1);
                // 合并后，num的这个node不在是开头的node，所以剔除
                headMap.remove(num);
            }
            if (headMap.containsKey(num + 1)) {
                current.next = headMap.get(num + 1);
                // 合并后，num+1的这个node不再是开头的node，所以剔除
                headMap.remove(num + 1);
                // 合并后，num的这个node不再是结尾的node，所以剔除
                tailMap.remove(num);
            }
            if (num == waitPoint) {
                print();
            }
        }

        private void print() {
            Node head = headMap.get(waitPoint);
            int headPoint = waitPoint;
            while(head != null) {
                System.out.print(head.info + " ");
                head = head.next;
                waitPoint++;
            }
            System.out.println();
            /**
             * 这段连续区间打印完后
             * 之前的开头node和结尾node都可以删除
             */
            headMap.remove(headPoint);
            tailMap.remove(waitPoint - 1);
        }
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();

        box.receive(2, "B");

        box.receive(1, "A");

        box.receive(4, "D");
        box.receive(5, "E");
        box.receive(7, "G");
        box.receive(8, "H");
        box.receive(6, "F");
        box.receive(3, "C");

        box.receive(9, "I");

        box.receive(10, "J");

        box.receive(12, "L");
        box.receive(13, "M");
        box.receive(11, "K");
    }

}
