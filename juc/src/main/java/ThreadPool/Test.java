package ThreadPool;

import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ser = Executors.newCachedThreadPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.put("a");

    }
}
