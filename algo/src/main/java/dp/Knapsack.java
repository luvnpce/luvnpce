package dp;

/**
 * 有一组货物，weights = 每个的重量，values = 每个的价值
 * 指定包裹的承受重量，找出价值最大值
 */
public class Knapsack {

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7 };
        int[] values = { 5, 6, 3, 19 };
        int bag = 1;
        System.out.println(brute(weights, values, 0, bag));
    }

    /**
     *
     * @param weights
     * @param values
     * @param index 当前位置
     * @param rest 剩余可承受重量
     * @return
     */
    public static int brute(int[] weights, int[] values, int index, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (index == weights.length) {
            return 0;
        }
        int no = brute(weights, values, index + 1, rest);
        int yes = brute(weights, values, index + 1, rest - weights[index]);
        if (yes != -1) {
            yes += values[index];
        }
        return Math.max(no, yes);
    }
}
