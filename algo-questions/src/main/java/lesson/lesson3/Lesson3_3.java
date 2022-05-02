package lesson.lesson3;

import java.util.HashSet;

/**
 * 只由小写字母a~z组成的一批字符串
 * 都放在字符类型的数组String[] arr当中
 * 如果其中某两个字符串所含有的字符种类完全一样
 * 就将两个字符串算成一类
 * ie：baacbba和abc就算作一类
 * 返回arr中有多少类
 */
public class Lesson3_3 {

    /**
     * 基于暴力解法的优化
     * 去掉排序和去重的步骤，直接用一个二进制代表一个字符串的摘要
     * 比如
     * 二进制第0位代表字符a，0代表没出现，1代表出现过
     * 二进制第1位代表字符b，第2位代表c，以此类推
     * ie："abc" == 111 == 7
     */
    public static int optimal(String[] arr) {
        HashSet<Integer> ans = new HashSet<>();
        for (String str : arr) {
            char[] chars = str.toCharArray();
            int num = 0;
            for (int i = 0; i < chars.length; i++) {
                num |= (1 << chars[i] - 'a');
            }
            ans.add(num);
        }
        return ans.size();
    }

    /**
     * 暴力解法：
     * 把数组里的每个字符串所用的字符从a->z排序，再去把重复的字符去重
     * 然后每个加到hashset里面，最终这个set的长度就等于有多少类
     */
    public static int brute(String[] arr) {
        return 0;
    }


}
