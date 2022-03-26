package ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    static class MyThreadPool extends ThreadPoolExecutor {

        public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
//            System.out.println(t.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        MyThreadPool pool = new MyThreadPool(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        pool.submit(()-> {
            for (;;) {
                Thread.sleep(100);
                System.out.println("---");
            }
        });

        Thread.sleep(1000);
        pool.shutdownNow();
    }

}
