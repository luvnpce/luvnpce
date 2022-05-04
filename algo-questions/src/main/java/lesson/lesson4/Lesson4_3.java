package lesson.lesson4;

/**
 * 返回一个二维数组中，子矩阵最大累加和
 */
public class Lesson4_3 {

    /**
     * 两个遍历，i从0->N，j从i->N
     * 求当前i行到j行的最大累加和
     *
     * i=0,j=0
     * 也就是第一行的最大累加和
     * i=0,j=1
     * 也就是第一行和第二行的最大累加和，怎么求？
     * 把第一行和第二行合并，相同列位置上的数相加
     * 然后就是单行就最大累加和
     * ===================================
     * 这个题还有一个优化，就是如果行数明显比列数大的话，可以将行列先转换，然后在做
     */
    public static int maxSum(int[][] m) {
        int N = m.length;
        int M = m[0].length;

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int[] temp = new int[M];
            for (int j = i; j < N; j++) {
                int cur = 0;
                for (int k = 0; k < M; k++) {
                    // 行合并
                    temp[k] = temp[k] + m[j][k];
                    // 单行最大累加和计算
                    cur += temp[k];
                    ans = Math.max(ans, cur);
                    cur = Math.max(cur, 0);
                }
            }
        }
        return ans;
    }

    /**
     * https://leetcode-cn.com/problems/max-submatrix-lcci/
     * 和上面相同，但是要返回子矩阵的左上角坐标和右下角坐标
     */
    public static int[] maxSubMatrix(int[][] m) {
        int N = m.length;
        int M = m[0].length;

        int ans = Integer.MIN_VALUE;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (int i = 0; i < N; i++) {
            int[] temp = new int[M];
            for (int j = i; j < N; j++) {
                int cur = 0;
                int begin = 0;
                for (int k = 0; k < M; k++) {
                    // 行合并
                    temp[k] = temp[k] + m[j][k];
                    // 单行最大累加和计算
                    cur += temp[k];
                    if (cur > ans) {
                        ans = cur;
                        a = i;
                        b = begin;
                        c = j;
                        d = k;
                    }
                    if (cur < 0){
                        begin = k + 1;
                        cur = 0;
                    }
                }
            }
        }
        return new int[] {a, b, c, d};
    }
}
