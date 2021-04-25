package ThreadPool;

public class Test implements Runnable{

    public void run() {
        System.out.println("hello");
    }

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService ser = Executors.newCachedThreadPool();
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//
//        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
//        queue.put("a");
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));
//
//        LinkedList<String> list = new LinkedList<>();
//        list.peekLast();

        new Thread(new Test(), "t1").start();
    }
}
