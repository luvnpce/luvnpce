package trivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * XOR: 无进位相加
 * 0010 ^ 0011 = 0001
 *
 * 要点1：0 ^ N = N
 * 要点2：N ^ N = 0
 */
public class XOR {

    public static void main(String[] args) {
        // 有两个变量a和b，不适用额外变量，把a和b的值互换
        int a = 99;
        int b = 66;

        a = a ^ b; // tmp
        b = a ^ b; // b = a ^ b ^ b = a ^ 0 = a
        a = a ^ b; // a = a ^ b ^ a = b ^ 0 = b

        System.out.println(a);
        System.out.println(b);

        // 所以，数组的swap也可以用这个操作
        // 注意，不能自己和自己swap，不然会变成0
        int[] arr = {1, 2, 3};
        arr[0] = arr[0] ^ arr[2];
        arr[2] = arr[0] ^ arr[2];
        arr[0] = arr[0] ^ arr[2];
        System.out.println(arr[0] + ", " + arr[2]);

        System.out.println("================================================================================");
        int[] arr2 = {1,1,3,3,3,4,4,4,4,5,5,5,5};
        findOddTimesNum(arr2);

        System.out.println("================================================================================");
        int[] arr3 = {1,1,3,3,3,4,4,4,4,5,5,5,5,7,7,7,7,7};
        findOddTimesNum2(arr3);

        System.out.println("================================================================================");
        bitOneCount(29);
    }

    /**
     * 有一个数组，其中只有一个数出现了奇数次，其他的数都出现了偶数次: {1,1,3,3,3,4,4,4,4,5,5,5,5}
     */
    public static void findOddTimesNum(int[] arr) {
        int xor = 0;
        for (int i : arr) {
            xor ^= i;
        }
        System.out.println("有一个数组，其中只有一个数出现了奇数次，其他的数都出现了偶数次: " + Arrays.toString(arr));
        System.out.println(xor);
    }

    /**
     * 有一个数组，其中只有两个数出现了奇数次，其他的数都出现了偶数次: {1,1,3,3,3,4,4,4,4,5,5,5,5,7,7,7,7,7}
     */
    public static void findOddTimesNum2(int[] arr) {
        int xor = 0;
        // 假设a，b是两个出现了奇数次的数，先把所有数逐个xor一遍，那么剩下的xor = a ^ b
        for (int i : arr) {
            xor ^= i;
        }
        // xor = a^b
        // xor != 0，因为a和b不相等
        // xor必然有一个位置上是1，因为xor在这个位置上是1，那么a和b在这个位置上肯定一个是1，一个是0
        // 提取出xor最右侧的1：011001000 => 000001000
        int rightOne = xor & (~xor + 1);

        // 重新遍历一遍，把同样在该位置上是1的数再xor一遍，其他的数都出现了偶数次，都会对冲掉，只剩下奇数次的其中一个
        // xor_就是a或b其中一个
        int xor_ = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) != 0) {
             xor_ ^= arr[i];
            }
        }
        // 已知xor = a ^ b
        // 已知xor_是a或b其中一个
        // 那么xor ^ xor_就等于另外一个
        xor = xor_ ^ xor;
        System.out.println("有一个数组，其中只有两个数出现了奇数次，其他的数都出现了偶数次: " + Arrays.toString(arr));
        System.out.println(xor);
        System.out.println(xor_);
    }

    /**
     * 统计一个数的二进制位有多少个1
     */
    public static void bitOneCount(int i) {
        System.out.println(Integer.toBinaryString(i));
        int count = 0;
        while (i != 0) {
            // 先找出最右侧的一个1
            int rightOne = i & (~i + 1);
            // 通过XOR把这个1抹掉
            // 11101000
            // 00001000 XOR
            // 11100000
            i ^= rightOne;
            // 计数+1
            count++;
        }
        System.out.println(count);
    }
}
