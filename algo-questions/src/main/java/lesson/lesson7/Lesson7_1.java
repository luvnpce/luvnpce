package lesson.lesson7;

/**
 * 给定一个非负数组成的数组，长度一定大于1
 * 想知道数组中哪两个数&（与）的结果最大
 * 返回这个最大的结果
 */
public class Lesson7_1 {

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     * ==========================
     * 二进制从左往右（30->0）开始遍历每个数，保留那些二进制位上是1的数
     * 当每一轮遍历完，如果这个位置上有1的数
     * 1. 大于2，那么删掉不符合的数，继续遍历下一个位置
     * 2. 等于2，那么答案就是这两个数的&
     * 3. 小于2，那么直接遍历下一个位置
     */
    public static int optimize(int[] arr) {
        int end = arr.length; // 垃圾区的起始位置，arr[0...end-1]等于有用区，arr[end...]等于垃圾区
        int ans = 0;
        for (int bit = 30; bit >= 0; bit--) {
            int index = 0;
            // 记录当前垃圾区的位置
            int temp = end;
            while (index < end) {
                if ((arr[index] & (1 << bit)) == 0) {
                    // 这个数在bit位置上不是1，扔到垃圾区
                    swap(arr, index, --end);
                } else {
                    index++;
                }
            }
            if (end == 2) {
                // 等于两个
                return arr[0] & arr[1];
            }
            if (end < 2) {
                // bit位置上等于1的数小于两个，重置垃圾区，继续遍历下一个位置
                end = temp;
            } else {
                // bit位置上等于1的数大于两个，那么最终答案这个位置肯定是1
                ans |= (1 << bit);
            }
        }
        return ans;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
