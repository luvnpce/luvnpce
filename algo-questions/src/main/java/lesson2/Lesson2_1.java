package lesson2;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * 给定数组hard和money，长度都为N
 * hard[i]表示i号的难度，money[i]表示i号工作的收入
 * 给定数组ability，长度为M，ability[j]表示j号人的能力
 * 每一个工作，都可以提供无数的岗位，难度和收入都一样
 * 但是人的能力必须>=这份工作的难度，才能上班
 * 返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 */
public class Lesson2_1 {

    private static class Job {
        private int money;
        private int hard;

        public Job(int money, int hard) {
            this.money = money;
            this.hard = hard;
        }
    }

    private static int[] solution(int[] hard, int[] money, int[] ability) {
        // 1、把hard和money合并
        Job[] jobs = new Job[hard.length];
        for (int i = 0; i < hard.length; i++) {
            jobs[i] = new Job(money[i], hard[i]);
        }

        // 2、按照难度从低到高，收入从高到低排序
        Arrays.sort(jobs, (o1, o2) -> o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money));

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(jobs[0].hard, jobs[0].money);
        Job prev = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            Job current = jobs[i];
            /**
             * 此时jobs的排序是按难度从低到高，如果难度相同，则按收入从高到底
             * 所以我们只关心同一个难度下的第一个工作，剩余相同难度的都可以忽略
             */
            if (current.hard != prev.hard && current.money > prev.money) {
                treeMap.put(current.hard, current.money);
                prev = current;
            }
        }

        // 3、计算ans
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = treeMap.floorKey(ability[i]);
            // 这里要注意判空，因为floorKey可能不存在
            ans[i] = null == key ? 0 : treeMap.get(key);
        }
        return ans;
    }

}
