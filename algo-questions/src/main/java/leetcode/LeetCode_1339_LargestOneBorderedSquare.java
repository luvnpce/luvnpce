package leetcode;

/**
 * https://leetcode.com/problems/largest-1-bordered-square/
 * difficulty: medium
 *
 * Given a 2D grid of 0s and 1s,
 * return the number of elements in the largest square subgrid that has all 1s on its border,
 * or 0 if such a subgrid doesn't exist in the grid.
 *
 * ie:
 * Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
 * Output: 9
 *
 * Input: grid = [[1,1,0,0]]
 * Output: 1
 */
public class LeetCode_1339_LargestOneBorderedSquare {

    public static void main(String[] args) {
        int[][] m = new int[8][3];
        m[0] = new int[]{1, 1, 0};
        m[1] = new int[]{1, 0, 1};
        m[2] = new int[]{1, 1, 1};
        m[3] = new int[]{1, 1, 1};
        m[4] = new int[]{1, 1, 1};
        m[5] = new int[]{1, 1, 0};
        m[6] = new int[]{1, 1, 1};
        m[7] = new int[]{1, 1, 0};

        System.out.println(solution(m));
    }

    /**
     * 首先理解一个逻辑，在一个N*N的正方形里
     * 1、一共有N^2个点
     * 2、在这个正方形里面，找出所有长方形的可能 = O(N^4)
     * 因为先随便找一个点，O(N^2)，作为长方形的左上角
     * 再随便找一个点，O(N^2)，作为长方形的右下角，这两个点就可以组成一个长方形
     * 所以O(N^2) * O(N^2) = O(N^4)
     * 3、在这个正方形里，找出所有正方形的可能 = O(N^3）
     * 因为先随便找一个点，O(N^2)，作为正方形的左上角
     * 再从0~N里设置这个正方形的边长
     * 所以O(N^2) * N = O(N^3)
     */

    public static int solution(int[][] m) {
        int rows = m.length;
        int cols = m[0].length;

        // 生成预处理数组
        int[][] right = new int[rows][cols];
        int[][] down = new int[rows][cols];
        fill(m, right, down);

        // 遍历每一个点
        return checkV2(rows, cols, right, down);
    }

    /**
     * 优化版遍历
     * 按照边长遍历，从大到小
     */
    private static int checkV2(int rows, int cols, int[][] right, int[][] down) {
        for (int size = Math.min(rows, cols); size > 0; size--) {
            for (int i = 0; i < rows - size + 1; i++) {
                for (int j = 0; j < cols - size + 1; j++) {
                    if (right[i][j] >= size && down[i][j] >= size
                            && right[i + size -1][j] >= size && down[i][j + size - 1] >= size) {
                        return size * size;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 遍历每一个点，挨个校验当前点作为一个正方形的左上角
     */
    private static int check(int rows, int cols, int[][] right, int[][] down) {
        int ans = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 找出(i,j)这个点为正方形左上角，可以延伸出的最长的边长
                // 注：(i,j)和(i,j)自己，边长等于1
                int maxBorder = Math.min(rows - i, cols - j);
                // 遍历每一种边长的可能性
                for (int border = 1; border <= maxBorder; border++) {
                    /**
                     * m(i,j)代表该正方形左上角的点，border为正方形边长
                     * 需要验证：
                     * 1、m(i,j)该点往右的是否都是1
                     * 2、m(i,j)该点往下的是否都是1
                     * 3、m(i,j + border - 1)该点往下的是否都是1
                     * 4、m(i + border - 1,j)该点往右的是否都是1
                     */
                    if (right[i][j] >= border && down[i][j] >= border
                            && right[i + border -1][j] >= border && down[i][j + border - 1] >= border) {
                        ans = Math.max(ans, border * border);
                    }
                }
            }
        }
        return ans;
    }


    /**
     * 生成预处理数组
     * @param m 原始二位数组
     * @param right 记录每个点往【右】有多少个连续的1
     * @param down 记录每个点往【下】有多少个连续的1
     */
    private static void fill(int[][] m, int[][] right, int[][] down) {
        int rows = m.length;
        int cols = m[0].length;

        //base case，填充右下角的点
        if (m[rows - 1][cols - 1] == 1) {
            right[rows - 1][cols - 1] = 1;
            down[rows - 1][cols - 1] = 1;
        }
        // 遍历最右一列，从下向上
        for (int i = rows - 2; i >= 0; i--) {
            if (m[i][cols - 1] == 1) {
                right[i][cols - 1] = 1;
                down[i][cols - 1] = down[i + 1][cols - 1] + 1;
            }
        }
        // 遍历最下一行，从右到左
        for (int i = cols - 2; i >= 0; i--) {
            if (m[rows - 1][i] == 1) {
                right[rows - 1][i] = right[rows - 1][i + 1] + 1;
                down[rows - 1][i] = 1;
            }
        }

        // 遍历剩余的
        for (int i = rows - 2; i >= 0 ; i--) {
            for (int j = cols - 2; j >= 0 ; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }

        }
    }
}
