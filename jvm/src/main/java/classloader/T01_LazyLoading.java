package classloader;

public class T01_LazyLoading {

    public static void main(String[] args) {
        System.out.println(P.j);
    }

    public static class P {
        final static int i = 8;
        static int j = 9;
        static {
            System.out.println("P");
        }
    }

    public static class X extends P {
        static {
            System.out.println("X");
        }
    }
}
