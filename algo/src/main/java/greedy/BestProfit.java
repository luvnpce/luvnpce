package greedy;

import java.util.PriorityQueue;

/**
 * 输入：正整数组costs、正整数组profits、正数k、正数m
 * costs[i]表示项目i的花费
 * profits[i]表示项目i的利润
 * k表示你只能做k个项目
 * m表示初始资金
 * 输出：你最后获得的最大钱数
 */
public class BestProfit {

    public static int greedy(int[] costs, int[] profits, int k, int m) {
        // 剩余的项目，按花费从小到大排序
        PriorityQueue<Program> minCost = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        // 可以承担花费的项目，按利润从大到小排序
        PriorityQueue<Program> maxProfit = new PriorityQueue<>((o1, o2) -> o2.profit - o1.profit);

        for (int i = 0; i < costs.length; i++) {
            // 未开始选择，所有项目加入剩余项目队列
            minCost.add(new Program(costs[i], profits[i]));
        }

        int totalProfits = 0;
        for (int i = 0; i < k; i++) {
            // 把可以承担花费的项目，添加到可以承担花费的项目队列
            while (!minCost.isEmpty() && minCost.peek().cost <= m) {
                maxProfit.add(minCost.poll());
            }
            if (maxProfit.isEmpty()) {
                // 没有任何项目可以做，结束
                return totalProfits;
            }
            // 选利润最大的
            totalProfits += maxProfit.poll().profit;
        }
        return totalProfits;
    }

    public static class Program {
        public int cost;
        public int profit;

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }
}
