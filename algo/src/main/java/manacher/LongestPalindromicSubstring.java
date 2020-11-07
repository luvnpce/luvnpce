package manacher;

import utils.StringUtils;

/**
 * 求一个字符串的最大回文子串的长度
 * abcba
 * #a#b#c#b#a#
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = StringUtils.generateRandomString(possibilities, strSize);
            if (manacher(str) != brute(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    /**
     * Manacher算法：O(N)
     * - 回文半径数组（pArr）
     * - 回文最右边界（R）
     * - 中心（C）
     * ===========================
     * 1. 遍历每个位置i
     *   - i在R外
     *   - i在R内：
     *       - 那么肯定 C <= i <= R，可以通过这样找到i'和L，然后根据i'判断
     *           - i'的回文区域在[L....R]内部，那么i的回文区域 == i'的回文区域
     *           - i'的回文区域在[L....R]之外，那么可以忽略
     *           - i'的回文区域左侧刚好和L位置相同，那么需要比对R+1位置上的数
     */
    private static int manacher(String str) {
        // 和brute同样，先加工原有字符串
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (char c : str.toCharArray()) {
            sb.append(c);
            sb.append('#');
        }

        String s = sb.toString();
        char[] chars = s.toCharArray();
        int[] pArr = new int[s.length()];
        // 讲述中：R代表最右的扩成功的位置。coding：最右的扩成功位置的，再下一个位置
        int r = -1;
        int c = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < chars.length; i++) {
            /**
             * i' = 2*c-i
             * 如果i >= r，那么需要暴力一个一个位置尝试，所以把pArr[i]设为1，并开始尝试（whileloop）
             * 如果i < r，那么pArr[i]先默认为不需要试的区域
             *   Math.min就是在三种情况中先选择i可以不需要试的区域
             *      - i'在L...R里面，那么肯定i'的区域会比i的区域小
             *      - i'左侧在L外面，那么肯定R-i更小
             *      - i'左侧刚好在L的位置，那么pArr[2*c-i]和r-i相同，随便取一个就行
             */
            pArr[i] = r > i ? Math.min(pArr[2*c-i], r-i) : 1;
            // 保证两侧不越界的情况下，再看看能不能扩展i的区域
            while (i + pArr[i] < chars.length && i - pArr[i] > -1) {
                if (chars[i+pArr[i]] == chars[i-pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            // 判断i位置的回文结束位置是否超过了r
            if (i + pArr[i] > r) {
                // 更新r和c
                r = i + pArr[i];
                c = i;
            }
            max = Math.max(max, pArr[i]);
        }

        return max - 1; // 因为pArr记录的是加工过后的字符串半径，所以最大回文字符子串长度 = 半径-1
    }

    /**
     * 暴力：O(N^2)，每个字符之间插入一个虚拟字符，然后逐个位置i来试
     */
    private static int brute(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (char c : str.toCharArray()) {
            sb.append(c);
            sb.append('#');
        }
        char[] chars = sb.toString().toCharArray();
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right < chars.length && chars[left] == chars[right]) {
                left--;
                right++;
            }
            max = Math.max(max, right - left - 1); // -1因为此时chars[left] != chars[right]
        }
        return max / 2;
    }
}
