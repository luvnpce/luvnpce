package segmenttree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 * 下面是这个游戏的简化版
 * 1. 只会下落正方形积木
 * 2. [a,b] 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3. 认为整个X轴都可能接住积木，也就是说简化版游戏室没有整体的左右边界的
 * 4. 没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满
 * 给定一个N*2的二维数组matrix，可以代表N个积木一次掉落
 * 返回每一次掉落之后的最大高度
 */
public class FallingSquares {

    public class SegmentTree {

        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size) {
            int N = size + 1;
            max = new int[N << 2];
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        public void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        public void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }

        public void update(int L, int R, int C, int left, int right, int rt) {
            if (L <= left && right <= R) {
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                return;
            }
            int mid = left + ((right - left) >> 1);
            if (L <= mid) {
                update(L, R, C, left, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid+1, right, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int left, int right, int rt) {
            if (L <= left && right <= R) {
                return max[rt];
            }
            int mid = (left + right) >> 1;
            pushDown(rt, mid - left + 1, right - mid);
            int lmax = 0;
            int rmax = 0;
            if (L <= mid) {
                lmax = query(L, R, left, mid, rt << 1);
            }
            if (R > mid) {
                rmax = query(L, R, mid + 1, right, rt << 1 | 1);
            }
            return Math.max(lmax, rmax);
        }
    }

    /**
     * 离散化
     * 把所有的方块都取出来，根据他们在X轴上的下面做离散化
     * 因为题目没有说明X的边界，所以不可能创建一个无边界的数组
     * 把所有方块的X位置取出来，从小到大，给与1....K的序号
     */
    public HashMap<Integer, Integer> index(int[][] blocks) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] block : blocks) {
            pos.add(block[0]);
            pos.add(block[0] + block[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] blocks) {
        HashMap<Integer, Integer> map = index(blocks);
        // 100   -> 1    306 ->   2   403 -> 3
        // [100,403]   1~3
        int N = map.size(); // 1 ~ 	N
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : blocks) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }
}
