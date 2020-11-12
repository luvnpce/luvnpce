package trivia;

/**
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如：
 * 5 = 2+3，5就是这样的数
 * 12 = 3+4+5，12就是这样的数
 * 1不是这样的数，应为要求数量大于1个、连续正数和
 * 2 = 1+1，2也不是，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 */
public class MSumToN {

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            System.out.println(i + ": " + brute(i) + " : " + optimize(i));
        }
    }

    public static boolean optimize(int n) {
        if (n < 3) {
            return false;
        }
        // n 等于 2的次幂就返回false
        // 100 & 011 = 0
        // 1000 & 0111 = 0
        return (n & (n-1)) != 0;
    }

    public static boolean brute(int n) {
        for (int i = 1; i < n; i++) {
            int sum = 0;
            int tmp = i;
            while (sum < n) {
                sum += tmp;
                tmp++;
            }
            if (sum == n) {
                return true;
            }
        }
        return false;
    }
}
