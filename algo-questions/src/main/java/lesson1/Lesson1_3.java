package lesson1;

/**
 * 给定一个非负整数num,返回离num最近的,2的某次方
 * ie:
 * num = 7, res = 8
 * num = 1, res = 0
 * HashMap源码tableSizeFor
 */
public class Lesson1_3 {

    public static void main(String[] args) {
        int num = 120;
        System.out.println(solution(num));
    }

    public static int solution(int num) {
        num--; // 为什么要-1? 因为要兼顾如果num刚好是2的某次方

        num |= num >>> 1; // >>：带符号右移（拿符号位补）, >>>：不带符号右移（不拿符号位补）
        num |= num >>> 2; // 这段右移代码目的就是把num二进制的格式 都填满成1
        num |= num >>> 4; // ie: num = 6 = 0110
        num |= num >>> 8; // 减一，再右移完后 = 0111
        num |= num >>> 16; // 最后再加一 = 1000，答案等于8
        return num < 0 ? 1 : num + 1;
    }
}
