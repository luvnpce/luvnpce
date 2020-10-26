package referencetype;

import java.io.IOException;

public class T01_NormalReference {

    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc(); // Disable Explicit GC

        System.in.read(); // 防止Main线程退出
    }
}
