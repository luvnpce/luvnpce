package ThreadPool;

import java.util.LinkedList;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ser = Executors.newCachedThreadPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.put("a");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));

        LinkedList<String> list = new LinkedList<>();
        list.peekLast();
    }
}
