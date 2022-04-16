package lesson.lesson1;

/**
 * 两个非负数组x和hp,长度都是N，再给定一个正数range
 * x有序，x[i]表示i号怪兽在x轴上的位置，hp[i]表示i号怪兽的血量
 * range表示法师如果站在x位置，用aoe技能打到的范围是[x-range, x+range]
 * 被打到的每只怪兽损失1点血量
 * 问，要把所有怪兽血量清空，至少释放多少AOE技能
 *
 * ie:
 *
 */
public class Lesson1_7 {

    /**
     * 贪心：
     * 1. 先处理左边边界的怪，血量为x，我们应该把他也放在aoe范围的边界，aoe至少释放x次
     * 2. 然后再寻找下一个边界的怪
     *
     * 释放aoe，如何变更数组里一个范围里的值？线段树（不会）
     */
    public static int greedy(int[] x, int[] hp, int range) {
        int N = x.length;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            if (hp[i] > 0) {
                // 算出以哪个index为中心释放aoe可以打到i位置上的怪兽
                int triggerPost = i;
                while (triggerPost < N && x[triggerPost] - x[i] <= range) {
                    triggerPost++;
                }
                ans += hp[i];
                aoe(x, hp, i, triggerPost - 1, range);
            }
        }
        return ans;
    }

    /**
     * @param trigger 释放aoe的中心位置
     */
    public static void aoe(int[] x, int[] hp, int L, int trigger, int range) {
        int N = x.length;
        int RPost = trigger;
        // 计算出以trigger位置释放aoe，能影响到到最右侧的怪兽
        while (RPost < N && x[RPost] - x[trigger] <= range) {
            RPost++;
        }
        int minus = hp[L];
        for (int i = L; i < RPost; i++) {
            hp[i] = Math.max(0, hp[i] - minus);
        }
    }
}
