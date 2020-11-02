package probability;

import java.util.Random;

/**
 * 给定一个黑盒函数f()，它能够等概率的返回1~7其中的一个数字
 * 请用这个函数f()，来等概率生成1~10里面的一个数字
 * ===============================================
 * 解法：
 *  - 把f()转换成等概率生成0，1。当f()返回1~3 = 0，4~6 = 1，返回7就重新调用
 *  - 然后把1~10替换成二进制，在每个位置上使用f()来判断该位置是0或1，如果最后结果大于10，那就重头开始
 */
public class GenerateOneToTen {

    public static void main(String[] args) {
        GenerateOneToTen main = new GenerateOneToTen();
        for (int i = 0; i < 100; i++) {
            System.out.println(main.fun10());
        }
    }

    public int fun10() {
        int x = 10;
        while (x >= 10) {
            int newNum = 0;
            // 10 至少需要4位二进制
            for (int i = 0; i < 4; i++) {
                if (fun2() == 1) {
                    newNum += Math.pow(2, i);
                }
            }
            x = newNum;
        }
        return x + 1;
    }

    public int fun2() {
        int x = 7;
        while (x == 7) {
            x = fun7();
        }
        return x <= 3 ? 0 : 1;
    }

    public int fun7() {
        return new Random().nextInt(7) + 1;
    }
}
