package kmp;

/**
 * 给定两个字符串a，b。判断两个字符串是不是互相的旋转词
 * 例子：123456 612345，两个是旋转词
 * ==========================================
 * 解法：
 *  让a拼接自己 = a'，然后通过kmp算法来判断a'是否包含b
 */
public class IsRotation {

    public static void main(String[] args) {
        String a = "123456";
        String b = "612345";
        System.out.println(isRotation(a, b));
    }

    public static boolean isRotation(String a, String b) {
        if (null == a || null == b || b.length() < 1 || a.length() < b.length()) {
            return false;
        }
        a += a; // 让a拼接自己，然后通过kmp算法
        System.out.println(a);
        return kmp(a, b) != -1;
    }

    public static int kmp(String a, String b) {
        char[] aChar = a.toCharArray();
        char[] bChar = b.toCharArray();
        int[] next = generateNext(bChar);
        int x = 0;
        int y = 0;
        while (x < aChar.length && y < bChar.length) {
            if (aChar[x] == bChar[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == bChar.length ? x-y : -1;
    }

    private static int[] generateNext(char[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[arr.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0;
        int i = 2;

        while (i < next.length) {
            if (arr[i-1] == arr[cn]) {
                next[i] = cn+1;
                i++;
                cn++;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i] = 0;
                i++;
            }
        }
        return next;
    }

}
