package datastructure.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * 给定一些矩形，求最大重叠次数
 */
public class CoverMaxRectangle {

    public static class Rectangle {
        // 矩形的最上边界
        public int up;
        // 矩形的最下边界
        public int down;
        // 矩形的最左边界
        public int left;
        // 矩形的最右边界
        public int right;

        public Rectangle(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 算法流程：
     * 1. 先把所有矩形根据自己的down来排序
     * 2. 遍历每个矩形i
     *      - 把这个矩形i加入到一个有序列表（leftOrdered）里（按照左边界的顺序从小到大）
     *      - 以该矩形i的底线为界限，清除掉那些顶在该矩形底下面的，因为他们不可能重叠
     *      - 现在leftOrdered里面只存有包含这个底线的矩形，那么就变成了线段重合的问题
     * ==================================================================
     * 时间复杂度：
     * 矩形数量是N
     *    O(N*LogN) 排序
     *     +
     *    O(N) * [ O(N) + O(N *LogN) ]
     *    =
     *    O(N^2 * LogN)
     */
    public static int maxCover(Rectangle[] recs) {
        if (recs == null || recs.length == 0) {
            return 0;
        }
        // 根据down（底）从小到大排序
        Arrays.sort(recs, ((o1, o2) -> o1.down - o2.down));
        // 可能会对当前底边的公共局域，产生影响的矩形
        // list -> treeSet(有序表表达)
        TreeSet<Rectangle> leftOrdered = new TreeSet<>((o1, o2) -> o1.left - o2.left);
        int ans = 0;
        // O(N)
        for (int i = 0; i < recs.length;) { // 依次考察每一个矩形的底边
            // 加速，把有同样底边的矩形加进来一并处理
            do {
                leftOrdered.add(recs[i++]);
            } while (i < recs.length && recs[i].down == recs[i - 1].down);
            // 清除顶<=当前底的矩形
            removeLowerOnCurDown(leftOrdered, recs[i - 1].down);
            // 维持了右边界排序的容器
            TreeSet<Rectangle> rightOrdered = new TreeSet<>((o1, o2) -> o1.right - o2.right);
            for (Rectangle rec : leftOrdered) { // O(N)
                removeLeftOnCurLeft(rightOrdered, rec.left);
                rightOrdered.add(rec);// O(logN)
                ans = Math.max(ans, rightOrdered.size());
            }
        }
        return ans;
    }

    public static void removeLowerOnCurDown(TreeSet<Rectangle> set, int curDown) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : set) {
            if (rec.up <= curDown) {
                removes.add(rec);
            }
        }
        for (Rectangle rec : removes) {
            set.remove(rec);
        }
    }

    public static void removeLeftOnCurLeft(TreeSet<Rectangle> rightOrdered, int curLeft) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : rightOrdered) {
            if (rec.right > curLeft) {
                break;
            }
            removes.add(rec);
        }
        for (Rectangle rec : removes) {
            rightOrdered.remove(rec);
        }
    }
}
