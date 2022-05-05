package lesson.lesson4;

/**
 * 达标：对于任意的i<k<j，满足arr[i]+arr[j] != arr[k]*2
 * 给定一个正数size，返回长度为size的达标数组
 */
public class Lesson4_5 {

    /**
     * 假设我们有一个数组[a,b,c]且a+c != 2b
     * 那么可以得出[2a,2b,2c]也是2a+2c != 2*2b
     * 也可以说[2a+1,2b+1,2c+1]也是2a+1 + 2c+1 != 2*(2b+1)
     *
     * 最后我们可以说
     * [2a,2b,2c,2a+1,2b+1,2c+1]这个数组肯定也达标
     * 因为前三个是偶数且他们本身就达标，后三个是奇数且本身也达标，
     * 合并在一起后，一个偶数+奇数不可能是一个数的两倍（偶数），所以也达标
     */
    public static int[] solution(int size) {
        if (size == 1) {
            return new int[] {1};
        }

        /**
         * 二分递归，一直分到size = 1，然后从小到大build
         */
        int half = (size + 1) / 2;
        int[] base = solution(half);
        int[] ans = new int[size];

        int index = 0;
        for (; index < half; index++) {
            ans[index] = base[index] * 2;
        }
        for (int i = 0; i < half; index++, i++) {
            ans[index] = base[index] * 2 + 1;
        }
        return ans;
    }

    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = solution(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

}
