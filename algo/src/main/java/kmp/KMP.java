package kmp;

/**
 * 给定两个字符串a、b，并且a的长度大于或等于b
 * 判断a包不包含b
 * 这里如果包含，我们直接返回包含的起始位置
 */
public class KMP {

    public static void main(String[] args) {
        String a = "1";
        String b = "12";
        System.out.println(brute(a, b));
    }

    /**
     * KMP算法：O(N)
     */
    public static int kmp(String a, String b) {
        if (null == a || null == b || b.length() < 1 || a.length() < b.length()) {
            return -1;
        }
        char[] aChar = a.toCharArray();
        char[] bChar = b.toCharArray();
        int[] next = getNextArr(bChar);
        int x = 0;
        int y = 0;
        while (x < aChar.length && y < bChar.length) {
            if (aChar[x] == bChar[y]) {
                x++;
                y++;
            } else if (next[y] == -1) { // 等效于 y == 0
                x++;
            } else {
                y = next[y];
            }
        }
        return y == bChar.length ? x - y : -1;
    }

    /**
     * 计算PMT数组
     * PMT中的值是字符串的前缀集合与后缀集合的交集中最长元素的长度
     * 默认:
     *  arr[0] = -1
     *  arr[1] = 0
     * O(M)
     */
    private static int[] getNextArr(char[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[arr.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;

        /**
         * 在处理i时，查看next[i-1]的值cn，然后判断arr[i-1]是否和arr[cn]相等
         */
        while (i < next.length) {
            if (arr[i - 1] == arr[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }

        return next;
    }

    /**
     * 暴力：每个位置都尝试匹配一下 O(NM)
     */
    public static int brute(String a, String b) {
        if (a.length() < b.length()) {
            return -1;
        }
        char[] aChar = a.toCharArray();
        char[] bChar = b.toCharArray();

        for (int i = 0; i <= aChar.length - b.length(); i++) {
            int k = i;
            boolean flag = true;
            for (int j = 0; j < bChar.length; j++,k++) {
                if (aChar[k] != bChar[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }
}
