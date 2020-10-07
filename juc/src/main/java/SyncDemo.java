import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

public class SyncDemo {

    public static void main(String[] args) throws InterruptedException {
        SyncDemo demo = new SyncDemo();
//        System.out.println("befor lock");
//        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        synchronized (demo){
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        }
//        System.out.println("after lock");
//        System.out.println(ClassLayout.parseInstance(demo).toPrintable());

        Thread t1 = new Thread(() -> {
            synchronized (demo) {
                System.out.println("lock ing");
                System.out.println(ClassLayout.parseInstance(demo).toPrintable());
            }
        });
        t1.start();
        t1.join();
        System.out.println(ClassLayout.parseInstance(demo).toPrintable());

    }
}
