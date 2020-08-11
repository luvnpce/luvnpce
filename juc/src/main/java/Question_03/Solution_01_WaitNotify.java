package Question_03;

import java.util.LinkedList;
import java.util.List;

/**
 * 这个解法的缺陷，NotifyAll唤醒所有线程，对CPU消耗过大，有可能出现队列满后，唤醒并抢到锁的仍然是生产者线程
 */
public class Solution_01_WaitNotify {
    final private int MAX_SIZE = 10;
    private List<Object> list = new LinkedList<>();
    private int size = 0;

    public synchronized void put(Object o) {
        while (size == MAX_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(o);
        size++;
        this.notifyAll();
    }

    public synchronized Object get() {
        while (size == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object o = list.remove(0);
        size--;
        this.notifyAll();
        return o;
    }

    public static void main(String[] args) {
        Solution_01_WaitNotify solution = new Solution_01_WaitNotify();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    solution.put(j);
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while(true) {
                    Object o = solution.get();
                    System.out.println(o.toString());
                }
            }).start();
        }
    }
}
