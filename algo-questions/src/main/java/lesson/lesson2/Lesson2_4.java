package lesson.lesson2;

/**
 * 贩卖机支持硬币支付，且收退都只支持10，50，100这三种面额
 * 一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则
 * 需要购买的可乐数量是m
 * 其中手头拥有的10，50，100的数量分别为a, b, c
 * 可乐的价格是x（x是10的倍数）
 * 请计算出需要投入的硬币次数
 */
public class Lesson2_4 {

    /**
     * 根据面额来遍历计算
     * @param m 要买的可乐数量
     * @param a 100元有a张
     * @param b 50元有b张
     * @param c 10元有c张
     * @param x 可乐单价
     * @return
     */
    public static int optimize(int m, int a, int b, int c, int x) {
        int[] moneyValue = { 100, 50, 10 };
        int[] moneyCount = { a, b, c };
        // 答案，总共要投多少次币
        int puts = 0;
        // 之前的面额剩余的总金额
        int prevTotalMoneyValue = 0;
        // 之前的面额剩余的总张数
        int prevTotalMoneyCount = 0;

        for (int i = 0; i < 3 && m != 0; i++) {
            /**
             * 1、计算买第一张时比较特殊
             * 要把之前面额剩余的总金额prevTotalMoneyValue加到一起计算
             * 然后计算出包含了之前面额剩余的金额后，当天面额还需要补多少张
             * 需要补多少张 = ceiling[(单价 - 剩余金额) / 当前面额] = floor[(单价 - 剩余金额 + (当前面额 - 1)) / 当前面额]
             */
            int moneyCountForFirstCola = (x - prevTotalMoneyValue + moneyValue[i] - 1) / moneyValue[i];
            if (moneyCount[i] >= moneyCountForFirstCola) {
                // 当前面额够买第一瓶可乐
                findChange(moneyValue, moneyCount, i + 1, (prevTotalMoneyValue + moneyValue[i] * moneyCountForFirstCola) - x, 1);
                puts += moneyCountForFirstCola + prevTotalMoneyCount;
                moneyCount[i] -= moneyCountForFirstCola;
                m--;
            } else {
                // 当前面额不够买第一瓶可乐
                prevTotalMoneyValue += moneyValue[i] * moneyCount[i];
                prevTotalMoneyCount += moneyCount[i];
                continue;
            }
            /**
             * 2、计算当前面额还能买多少瓶可乐
             */
            // 只用当前面额，买一瓶可乐要多少张
            int curMoneyCountForOneCola = (x + moneyValue[i] - 1) / moneyValue[i];
            // 当前面额剩余张数，可以买多少瓶可乐
            int buyColaCount = Math.min(moneyCount[i] / curMoneyCountForOneCola, m);
            // 找零
            int oneTimeRest = moneyValue[i] * curMoneyCountForOneCola - x;
            findChange(moneyValue, moneyCount, i + 1, oneTimeRest, buyColaCount);
            // 更新总投币次数
            puts += curMoneyCountForOneCola * buyColaCount;
            // 更新剩余要买的可乐数量
            m -= buyColaCount;
            // 当前面额的剩余张数
            moneyCount[i] -= curMoneyCountForOneCola * buyColaCount;
            // 余张
            prevTotalMoneyCount = moneyCount[i];
            // 余额
            prevTotalMoneyValue = moneyValue[i] * moneyCount[i];
        }
        return m == 0 ? puts : -1;
    }

    /**
     * 找零
     * @param moneyValue 钱面额
     * @param moneyCount 钱数量
     * @param i
     * @param oneTimeRest 购买一次的找零
     * @param times 购买次数
     */
    public static void findChange(int[] moneyValue, int[] moneyCount, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            moneyCount[i] += (oneTimeRest / moneyValue[i]) * times;
            oneTimeRest %= moneyValue[i];
        }
    }

    public static int brute(int m, int a, int b, int c, int x) {
        int[] moneyValue = { 100, 50, 10 };
        int[] moneyCount = { a, b, c };
        int puts = 0;
        while (m != 0) {
            int cur = doBrute(moneyValue, moneyCount, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    public static int doBrute(int[] moneyValue, int[] moneyCount, int rest) {
        int first = -1;
        for (int i = 0; i < 3; i++) {
            if (moneyCount[i] != 0) {
                first = i;
                break;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (moneyValue[first] >= rest) {
            moneyCount[first]--;
            findChange(moneyValue, moneyCount, first + 1, moneyValue[first] - rest, 1);
            return 1;
        } else {
            moneyCount[first]--;
            int next = doBrute(moneyValue, moneyCount, rest - moneyValue[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;
        }
    }

    public static void main(String[] args) {
        int testTime = 1000;
        int moneyCountMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("如果错误会打印错误数据，否则就是正确");
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * colaMax);
            int a = (int) (Math.random() * moneyCountMax);
            int b = (int) (Math.random() * moneyCountMax);
            int c = (int) (Math.random() * moneyCountMax);
            int x = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = optimize(m, a, b, c, x);
            int ans2 = brute(m, a, b, c, x);
            if (ans1 != ans2) {
                System.out.println("int m = " + m + ";");
                System.out.println("int a = " + a + ";");
                System.out.println("int b = " + b + ";");
                System.out.println("int c = " + c + ";");
                System.out.println("int x = " + x + ";");
                System.out.println("optimize = " + ans1);
                System.out.println("brute = " + ans2);
                break;
            }
        }
        System.out.println("test end");
    }

}
