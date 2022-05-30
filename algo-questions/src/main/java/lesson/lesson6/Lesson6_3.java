package lesson.lesson6;

/**
 * 尼姆博弈
 * 给定一个数组arr，长度为N，代表了N堆物品
 * arr[i]代表第i堆一共有多少个数量
 *
 * 两名玩家轮流选择从一堆物品里拿走任意数量的物品，不能不拿
 * 拿到最后一个物品的玩家获胜
 */
public class Lesson6_3 {

    /**
     * 解法：所有数的异或和为1，先手必赢，异或和为0，后手必赢
     * =======================
     * 胜利条件是拿走最后一件物品，也就是当轮到对手时已经没有物品了
     * 没有物品等于数组里每个数都是0，那这个数组的异或和也就是0
     * ***每当一个人拿走不管多少个物品时，数组的异或和都会在0与非0之间变换
     * ***所以玩家的策略就是让对面每次在选择时，数组的异或和都是0
     */
    public static void solution(int[] arr) {
        int xor = 0;
        for (int num : arr) {
            xor ^= num;
        }
        if (xor == 0) {
            System.out.println("后手赢");
        } else {
            System.out.println("先手赢");
        }
    }

}
