package Question_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Solution_01_ReentrantLock {
    private List list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();

    }

    public static void main(String[] args) {
        Solution_01_ReentrantLock test = new Solution_01_ReentrantLock();

        ReentrantLock lock = new ReentrantLock(true);
        Thread t2 = new Thread(() -> {
           while (true) {
               int size;
               try {
                   lock.lock();
                   size = test.size();
                   if (5 == size) {
                       System.out.println("Stop at 5");
                       break;
                   }
               } finally {
                   lock.unlock();
               }
           }
        });

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(i);
                    lock.lock();
                    test.add(i);
                } finally {
                    lock.unlock();
                }

            }
        });
        t2.start();
        t1.start();
    }
}
