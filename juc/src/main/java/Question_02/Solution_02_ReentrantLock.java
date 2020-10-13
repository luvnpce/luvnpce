package Question_02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 存在Bug
 */
public class Solution_02_ReentrantLock {



    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Condition number = lock.newCondition();
        Condition alphabet = lock.newCondition();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 1; i <= 26; i++) {
                    System.out.print((char) (96 + i));
                    number.signal();
                    alphabet.await();
                }
                number.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 1; i <= 26; i++) {
                    System.out.print(i);
                    alphabet.signal();
                    number.await();
                }
                alphabet.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
    }

}
