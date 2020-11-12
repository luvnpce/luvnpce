package trivia;

/**
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量
 * 1. 能装下6个苹果的袋子
 * 2. 能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
 * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
 */
public class AppleMinBags {

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            System.out.println(i + ": " + brute(i) + " : " + optimize(i));
        }
    }

    /**
     * 打表找规律 O(1)
     * 通过暴力解法我们发现，从18开始每八个数，答案+1
     * 18 - 25：偶数 = 3， 奇数 = -1
     * 26 - 33：偶数 = 4， 奇数 = -1
     * 所以我们可以直接通过这个规律，把代码写死，达到O(1)
     */
    public static int optimize(int n) {
        if ((n & 1) != 0) {
            // 奇数
            return -1;
        }
        if (n < 18) {
            return n == 0 ? 0 : (n == 6 || n == 8) ? 1
                    : (n == 12 || n == 14 || n == 16) ? 2 : - 1;
        }
        return (n - 18) / 8 + 3;
    }

    public static int brute(int n) {
        return div8(n);
    }

    private static int div8(int n) {
        int times = n / 8;
        if (8 * times == n) {
            return times;
        }

        while (times >= 0) {
            int remainder = n - 8 * times;
            remainder = div6(remainder);
            if (remainder > 0) {
                return times + remainder;
            }
            times--;
        }
        return -1;
    }

    private static int div6(int n) {
        return n % 6 == 0 ? n / 6 : -1;
    }
}
