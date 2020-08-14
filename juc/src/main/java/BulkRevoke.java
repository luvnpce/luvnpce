import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

public class BulkRevoke {

    public static void main(String[] args) throws InterruptedException {
        List<SyncDemo> list = new ArrayList<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                SyncDemo demo = new SyncDemo();
                synchronized (demo) {
                    list.add(demo);
                }
            }
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread.sleep(3000);

        System.out.println("打印T1线程第20个对象");
        System.out.println(ClassLayout.parseInstance(list.get(20)).toPrintable());

        System.out.println("打印T1线程第26个对象");
        System.out.println(ClassLayout.parseInstance(list.get(26)).toPrintable());

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                SyncDemo demo = list.get(i);
                synchronized (demo) {
                    if (i == 18 || i == 19 || i == 25) {
                        System.out.println("打印T2线程第" + (i+1) + "个对象");
                        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
                    }
                }
            }
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        Thread.sleep(3000);

        Thread t3 = new Thread(() -> {
            for (int i = 20; i < 40; i++) {
                SyncDemo demo = list.get(i);
                synchronized (demo) {
                    if (i == 20 || i == 22) {
                        System.out.println("打印T3线程第" + (i+1) + "个对象");
                        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
                    }
                }
            }
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t3.start();

        Thread.sleep(3000);
        SyncDemo syncDemo = list.get(51);
        synchronized (syncDemo) {
            System.out.println(ClassLayout.parseInstance(syncDemo).toPrintable());
        }
    }
}
