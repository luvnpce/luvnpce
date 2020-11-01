package slidingwindow;

import java.util.LinkedList;

/**
 * 假设一个固定大小为W的窗口，依次划过arr，返回每一次划出状况的最大值
 * 例如：arr = [4,3,5,4,3,3,6,7]; W =3；返回：[5,5,5,4,6,7]
 */
public class MaxArray {

    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        // 双向队列，只存数组的下标
        LinkedList<Integer> qmax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;

        for (int right = 0; right < arr.length; right++) {
            while(!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[right]) {
                // 从窗口右侧新进入的数在双向队列里只能放在比它大的数后面
                // 判断新加入窗口的数是否比双向队列的尾部数要大，是的话将尾部数依次弹出
                qmax.pollLast();
            }
            // 添加到双向队列
            qmax.addLast(right);
            if (qmax.peekFirst() == right - w) {
                // 如果窗口左侧移除的数是双向队列里的头部，将头部弹出
                qmax.pollFirst();
            }
            // 以上窗口更新做完了，下面开始组装res
            if (right >= w - 1) {
                // 当我们的窗口覆盖了[0...w-1]时候开始，记录每次移动时窗口的最大值
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }
}
