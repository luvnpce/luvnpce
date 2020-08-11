package Question_03;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的condition本质就是不同的等待队列
 * 可以让线程进入指定的等待队列，也可以指定唤醒某个等待队列里面的线程
 */
public class Solution_02_ReentrantLock {
    final private int MAX_SIZE = 10;
    private List<Object> list = new LinkedList<>();
    private int size = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(Object o) {
        try {
            lock.lock();
            if (size == MAX_SIZE) {
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(o);
            size++;
            consumer.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        try {
            lock.lock();
            if (size == 0) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object o = list.remove(0);
            size--;
            producer.signalAll();
            return o;
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Solution_02_ReentrantLock solution = new Solution_02_ReentrantLock();
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
