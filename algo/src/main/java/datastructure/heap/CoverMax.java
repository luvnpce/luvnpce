package datastructure.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 给定一组线段，每个线段有起始（start）和结束（end）位置
 * 返回最多线段之间重叠的次数
 */
public class CoverMax {

    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

    /**
     * 暴力解
     * 遍历出最小起始值和最大结束值
     * 从[min...max]这个范围每个点去找该点上重叠的线段有多少，返回最大值
     */
    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    /**
     * 使用最小堆
     * =======================================
     * 流程：
     *  1. 先把所有线段按照各自的开始时间，从小到大排序
     *  2. 开始遍历每个线段
     *      - 假设来到第i个，判断最小堆里，是否有结束时间<= 线段i的起始时间的，如果有则从堆里弹出
     *         - 因为我们是从最早的线段开始遍历，如果之前的线段的结束时间<= i的起始时间，那么这两个线段必定不重叠
     *      - 此时堆里的数量则是重叠的数量，更新最大值max
     */
    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines, ((o1, o2) -> o1.start - o2.start));
        PriorityQueue<Line> heap = new PriorityQueue<>(((o1, o2) -> o1.end - o2.end));
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static class Line {
        int start;
        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
