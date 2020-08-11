package Question_01;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
//        long a = 1,b = 1;
//        int result = 300549;
//        // a小于1000
//        while (a < 1000) {
//            if (((a * (a + 1) / 2) - 2 * b - (2 * b - 1)) == 300549) {
//                System.out.println(a + "," + b);
//                a = a + 1;
//                b = 1;
//            } else if (a > b) {
//                b = b + 1;
//            } else {
//                a = a + 1;
//                b = 1;
//            }
//        }
        Test test = new Test();
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");
//            lock.lock();
            test.m();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 start");
//            lock.lock();
            test.m();
//            lock.unlock();
        });

        t1.start();
        t2.start();
        test.finalize();

    }

    public synchronized void m() {
        System.out.println("sync");
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
