import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

public class SyncDemo {

    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
            System.out.println("==========Sleep for 5 secs===========");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SyncDemo demo = new SyncDemo();
//        System.out.println("befor lock");
//        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        synchronized (demo){
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        }
//        System.out.println("after lock");
//        System.out.println(ClassLayout.parseInstance(demo).toPrintable());

        new Thread(()-> {
            synchronized (demo){
                System.out.println("lock ing");
                System.out.println(ClassLayout.parseInstance(demo).toPrintable());
            }
        }).start();
    }
}
