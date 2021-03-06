package dp;

/**
 * 给定一个整型数组arr,代表数值不同的纸牌排成一条线，
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定A先拿，B后拿
 * A和B都绝顶聪明，请返回最后获胜者的分数
 */
public class CardsInLine {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        // 求获胜者的分数
        int res = Math.max(bruteFirst(arr, 0, arr.length-1), bruteSecond(arr, 0, arr.length-1));
        int res2 = dp(arr);
        System.out.println(res);
        System.out.println(res2);
    }

    public static int dp(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] first = new int[N][N];
        int[][] second = new int[N][N];
        // 初始化
        for (int i = 0; i < N; i++) {
            first[i][i] = arr[i];
            second[i][i] = 0;
        }

        for (int i = 1; i < N; i++) {
            int row = 0;
            int col = i;
            while (row < N && col < N) {
                first[row][col] = Math.max(arr[row] + second[row+1][col], arr[col] + second[row][col-1]);
                second[row][col] = Math.min(first[row+1][col], first[row][col-1]);
                row++;
                col++;
            }
        }

        return Math.max(first[0][N-1], second[0][N-1]);
    }

    /**
     * 先手时最好的情况
     */
    public static int bruteFirst(int[] arr, int left, int right) {
        if (left == right) {
            // 只剩一张拍，只能拿走这张
            return arr[left];
        }
        // 比较，先拿left的情况 和先拿right的情况
        return Math.max(arr[left] + bruteSecond(arr, left+1, right) , arr[right] + bruteSecond(arr, left, right-1));
    }

    /**
     * 后手时最好的情况
     */
    public static int bruteSecond(int[] arr, int left, int right) {
        if (left == right) {
            // 只剩一张拍，肯定被别人拿走
            return 0;
        }
        // 比较：别人拿left vs 别人拿right。因为对面聪明所以肯定给我们的是最差的结果
        return Math.min(bruteFirst(arr, left+1, right), bruteFirst(arr, left, right-1));
    }
}
