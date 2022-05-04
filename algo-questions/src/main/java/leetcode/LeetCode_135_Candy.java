package leetcode;

/**
 * https://leetcode.com/problems/candy/
 * difficulty: hard
 *
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * You are giving candies to these children subjected to the following requirements:
 *
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 *
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 *
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 *
 */
public class LeetCode_135_Candy {

    /**
     * 贪心解法，分三个步骤
     * 1、从左向右，i位置的孩子跟i-1位置的比较，如果i位置rating较高，那么i位置分到的糖就比i-1位置的多一颗，否则就给一颗
     * 0位置的默认给一颗
     * 2、从右向做，i位置的孩子跟i+1位置的比较，如果i位置rating较高，那么i位置分到的糖就比i+1位置的多一颗，否则就给一颗
     * N-1位置的默认给一颗
     * 3、每个位置上取max
     */
    public static int greedy(int[] ratings) {
        int N = ratings.length;
        int[] l = new int[N];
        l[0] = 1;
        for (int i = 1; i < N; i++) {
            l[i] = ratings[i] > ratings[i - 1] ? l[i - 1] + 1 : 1;
        }

        int[] r = new int[N];
        r[N - 1] = 1;
        for (int i = N - 2; i >= 0; i--) {
            r[i] = ratings[i] > ratings[i + 1] ? r[i + 1] + 1 : 1;
        }

        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += Math.max(l[i], r[i]);
        }
        return sum;
    }
}
