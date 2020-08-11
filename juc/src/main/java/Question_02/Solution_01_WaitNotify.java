package Question_02;

public class Solution_01_WaitNotify {

    public static void main(String[] args) {

        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i <= 26; i++) {
                    System.out.print((char) (96 + i));
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i <= 26; i++) {
                    System.out.print(i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        });

        t1.start();
        t2.start();
    }
}
