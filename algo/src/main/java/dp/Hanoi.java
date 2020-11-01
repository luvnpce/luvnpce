package dp;

/**
 * 汉诺塔
 * 最优步数 = (2^N)-1
 */
public class Hanoi {

    public static void main(String[] args) {

    }

    /**
     * 原理，把hanoi1方法简化，通过入参规定from, to, other
     */
    public static void hanoi2(int n) {
        if (n > 0) {
            doHanoi2(n, "left", "right","mid");
        }
    }

    /**
     * @param n
     * @param from 起始
     * @param to 终点
     * @param other 临时点
     */
    private static void doHanoi2(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        } else {
            doHanoi2(n-1, from, other, to);
            System.out.println("Move " + n + " from " + from + " to " + to);
            doHanoi2(n-1, other, to, from);
        }
    }


    /**
     * 步骤
     * 1. 把第1~N-1的圆盘移到中间
     * 2. 把第N个圆盘移到右边
     * 3. 把第1~N-1的圆盘从中间移到右边
     */
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    private static void leftToRight(int n) {
        if (n == 1) {
            // 只有一个，直接移动到右边结束
            System.out.println("Move 1 from left to right");
            return;
        }
        leftToMid(n-1);
        System.out.println("Move " + n + " from left to right");
        midToLeft(n-1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n-1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n-1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n-1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n-1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n-1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n-1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n-1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n-1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n-1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n-1);
    }


}
