package segmenttree;

import utils.ArrayUtils;

/**
 * 给定一个数组[0.....N]
 * 线段树会提供3个方法，分别是：
 *  - add(arr, L, R, V)：给数组上L到R位置上的数都加上V
 *  - update(arr, L, R, V)：把数组上L到R位置上的数都改成V
 *  - sum(arr, L, R)：求数组上L到R位置上数的总和
 * 线段树保证这3个方法的时间复杂度都是O(logN)
 * =======================================================
 * 结构：
 *  - 树的页节点是每一个数组里的数字，上面的父节点则代表着子树的区间范围
 *  - 假设原始数组长度是N，那么构建二叉树需要2N-1的空间
 *  - 线段树需要保证树的结构是满二叉树，如果数组的长度为2的次幂，那么线段树所需的空间就是2N-1
 *  - 如果原本数组长度不是2的次幂，那么我们需要通过补零来保证数组长度为2的次幂
 *  - 简单的做法就是，假设原数组长度为N，那么我们就直接申请4N的空间
 */
public class SegmentTree {

    // 最大长度
    private int maxN;
    // 原数组的信息从0开始，但是在arr中是从1开始
    private int[] arr;
    // 模拟线段树维护区间的和
    private int[] sum;
    // add懒惰标记
    private int[] lazy;
    // 记录update的值
    private int[] change;
    // update懒惰标记
    private boolean[] update;

    public SegmentTree(int[] origin) {
        this.maxN = origin.length + 1;
        // 初始化arr
        this.arr = new int[maxN];
        for (int i = 0; i < origin.length; i++) {
            arr[i+1] = origin[i];
        }
        // 初始化sum，sum的长度为4*maxN
        this.sum = new int[maxN << 2];
        // 初始化lazy
        this.lazy = new int[maxN << 2];
        // 初始化change
        this.change = new int[maxN << 2];
        // 初始化update
        this.update = new boolean[maxN << 2];
    }

    /**
     * 填充sum数组，在arr[L...R]范围上，去build 1~
     * @param left
     * @param right
     * @param rt sum数组的下标
     */
    public void build(int left, int right, int rt) {
        if (left == right) {
            sum[rt] = arr[left];
            return;
        }
        int mid = left + ((right - left) >> 1);
        // 因为我们是从1开始，所以左下标 = 2*i
        build(left, mid, rt << 1);
        // 右下标 = 2*i + 1
        build(mid+1, right, rt << 1 | 1);
        // 汇总
        pushUp(rt);
    }

    private void pushUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }

    /**
     * 把任务下推
     * @param rt
     * @param ln 左孩子的节点个数
     * @param rn 有孩子的节点个数
     */
    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            lazy[rt << 1] = 0;
            lazy[rt << 1 | 1] = 0;
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            sum[rt << 1] = change[rt] * ln;
            sum[rt << 1 | 1] = change[rt] * rn;
            update[rt] = false;
        }
        if (lazy[rt] != 0) {
            // 当前节点有"懒任务"需要下推
            // 下推给左孩子
            lazy[rt << 1] += lazy[rt];
            sum[rt << 1] += lazy[rt] * ln;
            // 下推给右孩子
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1 | 1] += lazy[rt] * rn;
            // 重置当前懒节点
            lazy[rt] = 0;
        }
    }

    /**
     * 给数组上L到R位置上的数都加上C
     * @param L 任务范围左标
     * @param R 任务范围右标
     * @param C 累加的值
     * @param left 表达的范围左标
     * @param right 表达的范围右标
     * @param rt 去哪个位置找[left...right]的信息
     */
    public void add(int L, int R, int C, int left, int right, int rt) {
        if (L <= left && right <= R) {
            // 任务范围[L...R]已经包容了当前rt这个位置[left...right]的范围
            // 直接更新sum数组rt位置的和
            sum[rt] += C * (right - left + 1);
            // 在这个位置上"懒执行"这个add任务（没有完整的去下面子节点修改）
            lazy[rt] += C;
            return;
        } else {
            // 任务范围没有包含当前rt位置的范围
            int mid = left + ((right - left) >> 1);
            // 把之前"懒执行"的任务下发
            pushDown(rt, mid - left + 1, right - mid);
            // 左孩子是否需要执行任务
            if (L <= mid) {
                add(L, R, C, left, mid, rt << 1);
            }
            // 右孩子是否需要执行任务
            if (R > mid) {
                add(L, R, C, mid+1, right, rt << 1 | 1);
            }
            // 汇总
            pushUp(rt);
        }
    }

    public void update(int L, int R, int C, int left, int right, int rt) {
        if (L <= left && right <= R) {
            // 在这个位置上"懒执行"这个update
            update[rt] = true;
            // 因为更新了值，所以要重新计算这个位置的和
            sum[rt] = C * (right - left + 1);
            // 因为更新了值，所以之前这个位置上的"懒执行"add任务作废
            lazy[rt] = 0;
            //
            change[rt] = C;
            return;
        }
        int mid = left + ((right - left) >> 1);
        pushDown(rt, mid - left + 1, right - mid);
        if (L <= mid) {
            update(L, R, C, left, mid, rt << 1);
        }
        if (R > mid) {
            update(L, R, C, mid+1, right, rt << 1 | 1);
        }
        pushUp(rt);
    }

    public long query(int L, int R, int left, int right, int rt) {
        if (L <= left && right <= R) {
            return sum[rt];
        }
        int mid = left + ((right - left) >> 1);
        pushDown(rt, mid - left + 1, right - mid);
        long ans = 0;
        if (L <= mid) {
            ans += query(L, R, left, mid, rt << 1);
        }
        if (R > mid) {
            ans += query(L, R, mid+1, right, rt << 1 | 1);
        }
        return ans;
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }


    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = ArrayUtils.generate(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}
