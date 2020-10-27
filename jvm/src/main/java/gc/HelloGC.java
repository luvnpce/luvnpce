package gc;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HelloGC {
    public static void main(String[] args) {
//        System.out.println("HelloGC!");
//        List list = new LinkedList();
//        for(;;) {
//            byte[] b = new byte[1024*1024];
//            list.add(b);
//        }

        ThreadLocal<String> t1 = new ThreadLocal<>();
        t1.set("1");
        System.out.println(t1.get());
        t1.set("2");
        System.out.println(t1.get());



        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}

