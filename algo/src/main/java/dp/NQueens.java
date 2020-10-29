package dp;

public class NQueens {


    public static void main(String[] args) {
        int n = 15;

        long start = System.currentTimeMillis();
        System.out.println(brute(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(optimizeBrute(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }

    /**
     * 优化暴力:
     *  - 列limit
     *  - 左移limit
     *  - 右移limit
     */
    private static int optimizeBrute(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit先是1，向左位移13位 = 1后面跟13个0
        // 然后减1，变成后面13个1，前面全是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return doOptimizeBrute(limit, 0, 0, 0);
    }

    /**
     * @param limit
     * @param colLimit 列的限制，1的位置不能放皇后，0的位置可以
     * @param leftLimit 左斜线的限制，1的位置不能放皇后，0的位置可以
     * @param rightLimit 右斜线的限制，1的位置不能放皇后，0的位置可以
     * @return
     */
    private static int doOptimizeBrute(int limit, int colLimit, int leftLimit, int rightLimit) {
        if (colLimit == limit) { // base case，每个列上都已经有皇后了，完成
            return 1;
        }
        // 所有可以放皇后的位置，都在pos上
        // colLim | leftDiaLim | rightDiaLim   -> 总限制
        // limit & (~ (colLim | leftDiaLim | rightDiaLim)) -> 左侧的一坨0干扰，右侧每个1，可尝试
        int pos = limit & ( ~(colLimit | leftLimit | rightLimit) );
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 其取出pos中，最右侧的1来，剩下位置都是0
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += doOptimizeBrute(limit,
                    colLimit | mostRightOne,
                    (leftLimit | mostRightOne) << 1,
                    (rightLimit | mostRightOne) >>> 1);
        }
        return res;
    }

    /**
     * 暴力：每行每列逐个试
     */
    public static int brute(int n) {
        if (n < 1) {
            return 0;
        }
        // record[0] ?  record[1]  ?  record[2]
        int[] record = new int[n]; // record[i] -> i行的皇后，放在了第几列
        return doBrute(0, record, n);
    }

    /**
     * @param i 第i行
     * @param record record[0...i-1]表示之前的行，放了的皇后位置（第几列）
     * @param n 总行数
     */
    public static int doBrute(int i, int[] record, int n) {
        if (i == n) {
            // 已经越界了
            return 1;
        }
        int res = 0;
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += doBrute(i + 1, record, n);
                // 这里没有还原现场因为record[i]是被重复修改的，不需要还原
            }
        }
        return res;
    }


    /**
     * record[0..i-1]你需要看，record[i...]不需要看
     * 返回i行皇后，放在了j列，是否有效
     */
    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) { // 之前的某个k行的皇后
            // Math.abs(record[k] - j) == Math.abs(i - k) 测试是否是斜线
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }
}
