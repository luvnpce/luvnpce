package ThreadPool;

public class Demo implements Runnable{

    @Override
    public void run() {
        // 在线程体中对线程的中断标志位进行判断，若线程中断，则不再执行
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Thread is running");

            try {
                System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().isInterrupted());
                Thread.sleep(100);

            }
            /**
             * 需要注意的是，当方法体中的代码抛出InterruptedException异常时，线程的中断标志位会复位成
             * false，若不处理，外部中断线程时，内部也无法停止，所以在catch代码中手动处理，将线程中断
             */
            catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
                // 发生InterruptedException异常时，在catch中处理，中断线程
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Demo());
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
