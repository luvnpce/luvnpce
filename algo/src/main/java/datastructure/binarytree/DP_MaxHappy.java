package datastructure.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 找寻派对的最大快乐值
 * 公司需要开派对，你可以决定哪些员工来，哪些员工不来。
 * 1. 如果员工X来了，那么X的所有直接下级都不能来
 * 2. 派对的整体快乐值 = 所有到场员工的累加
 * 3. 让整体快乐值最大化
 *=================================================
 * 可能性：
 * 1. 员工X来：那么下级都不能来
 * 2. 员工X不来：那么下级可以来可以不来
 */
public class DP_MaxHappy {

    public static Info process(Employee e) {
        if (e.nexts.isEmpty()) {
            return new Info(e.happy, 0);
        }
        int yes = e.happy;
        int no = 0;
        for (Employee next : e.nexts) {
            Info nextInfo = process(next);
            yes += nextInfo.no;
            no += Math.max(nextInfo.yes, nextInfo.no);
        }
        return new Info(yes, no);
    }

    public static class Info {
        // 来的时候最大快乐值
        public int yes;
        // 不来的时候最大快乐值
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy) {
            this.happy = happy;
            this.nexts = new ArrayList<>();
        }

    }
}
