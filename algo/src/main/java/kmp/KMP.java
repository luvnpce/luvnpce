package kmp;

/**
 * 给定两个字符串a、b，并且a的长度大于或等于b
 * 判断a包不包含b
 * 这里如果包含，我们直接返回包含的起始位置
 */
public class KMP {

    public static void main(String[] args) {
        String a = "612346123456";
        String b = "612345";
        System.out.println(kmp(a, b));
    }

    /**
     * KMP算法：O(N)
     * 算是一种加速算法
     * 假设a = "aaaaaab"，b = "aaab"
     * 当我们从a[0]开始，比对到i = 3的时候，a[3] != b[3]，此时我们不需要回到a[1]为起点重头开始比
     * 而是可以通过next数组，停留在a[3]，然后和b[2]开始比对，因为next[3] = 2。
     * 这个2代表的是，b的后缀2位字符 是和 b的前缀2位字符是相等的
     * 那么我们可以省略掉
     *      a[1] == b[0]
     *      a[2] == b[1]
     * 的比较，直接从a[3] == b[2]开始，从而得到加速
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
     * 计算next数组
     * next[i]的含义就是在i之前的字符串中[0...i-1]，以0位置开头且不包含i-1位置的<前缀子字符串>
     * 和以i-1位置结尾且不包含0开头位置的<后缀子字符串>它们之间的最大匹配长度是多少
     * 默认:
     *  arr[0] = -1
     *  arr[1] = 0
     * 列子：
     *  str = "aaaab"，next[4] = 3，因为str[0...2] = "aaa"，str[1...3] = "aaa"
     * 时间复杂度：O(M)
     */
    private static int[] getNextArr(char[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[arr.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0; // 默认为next[1]的值
        int i = 2;

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
