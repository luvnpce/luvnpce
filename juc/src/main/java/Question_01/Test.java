package Question_01;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws Throwable {
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
