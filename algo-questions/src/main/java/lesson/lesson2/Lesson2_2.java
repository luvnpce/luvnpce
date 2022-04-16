package lesson.lesson2;

/**
 * 给定一个数组arr，只能对arr中的一个子数组排序
 * 但是想让arr整体都有序
 * 返回满足这一个设定的子数组中，最短的是多长
 * ex:
 * arr = [7,6,2,1,0,8,9]，N = 7
 * 正常排序是从位置0到位置6排序
 * 但是对于这个arr，位置5和6已经是排好序的，所以我们只需要对位置0至4排序
 * 所以ans = 5 = 4-0+1
 * ============
 * 时间复杂度 O(N)
 * 空间复杂度 O(1)
 */
public class Lesson2_2 {

    /**
     * 1、从左到右，找出数组最右侧已经排好序的部分
     * 2、从右到左，找出数组最左侧已经排好序的部分
     * 3、这两个之间就是需要排序的
     */
    public static int solution(int[] arr) {
        // base case
        if (arr.length <= 1) {
            return 0;
        }
        // 1、从左到右，rightIndex再往右都是不需要排序的，因为之后的数都是一个比一个大
        int leftMax = arr[0];
        int rightIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < leftMax) {
                // 当前位置的数arr[i]小于它左侧最大的数
                rightIndex = i;
            }
            leftMax = Math.max(leftMax, arr[i]);
        }

        // 2、从右到左，leftIndex再往左都是不需要排序的，因为最后的数都是一个比一个小
        int rightMin = arr[arr.length - 1];
        int leftIndex = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > rightMin) {
                leftIndex = i;
            }
            rightMin = Math.min(rightMin, arr[i]);
        }

        return rightIndex - leftIndex + 1;
    }
}
