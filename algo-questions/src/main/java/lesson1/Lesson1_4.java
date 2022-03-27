package lesson1;

/**
 * 数组只有两种字符g,b,所有g放在左侧,b放在右侧,或所有g放在右侧,所有b放在左侧返回至少交换几次
 */
public class Lesson1_4 {

    public static void main(String[] args) {

    }

    /**
     * 假设g在左边，我们有两个指针：
     * gIndex: 是g应该调换到的位置
     * i: 是当前遍历的位置，从0到arr.length-1
     * 要从i交换到gIndex的位置 = i - gIndex
     * =======================
     * gIndex从0开始，代表第一个看见的g应该在0位置上，遇到第一个g后，gIndex++，代表下一个g应该在gIndex++后的位置上
     */
    public static int solution(char[] arr) {
        int gIndex = 0;
        int bIndex = 0;
        int gRes = 0;
        int bRes = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'g') {
                gRes += i - gIndex;
                gIndex++;
            } else {
                bRes += i - bIndex;
                bIndex++;
            }
        }
        return Math.min(gRes, bRes);
    }
}
