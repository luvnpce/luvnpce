package dp;

import java.util.HashMap;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr。
 * arr里的每一个字符串，代表一张贴纸，你可以吧单个字符剪开使用，目的是拼出str来。
 * 返回需要至少多少张贴纸可以完成这个任务
 * 例子：str = “babac”，arr = {“ba”, “c”, “abcd“}
 * 至少需要两张贴纸（”ba”和”abcd“}，因为使用这两张贴纸，把每个字符单独剪开，含有2个a，2个b，1个c。是可以拼出str。所以返回2.
 */
public class Stickers {

    public static void main(String[] args) {
        String target = "babac";
        String[] stickers = {"ba", "c", "abcd"};
        System.out.println(brute(target, stickers));
    }

    /**
     * 暴力：每个贴纸都试一试，然后使用消除后的target字符串再进行遍历
     * 1. target = "babac"，stickers = {“ba”, “c”, “abcd“}
     * 2. 使用第一个贴纸"ba"，消除后target为 = "bac"，再进行遍历
     */
    public static int brute(String target, String[] stickers) {
        int n = stickers.length;
        int[][] map = new int[n][26];

        for (int i = 0; i < n; i++) {
            String sticker = stickers[i];
            char[] chars = sticker.toCharArray();
            for (char c : chars) {
                map[i][c - 'a']++;
            }
        }

        HashMap<String, Integer> dp = new HashMap<>();
        // base case：如果目标字符串是空字符串，返回0
        dp.put("", 0);
        return doBrute(dp, map, target);

    }

    /**
     * @param dp 缓存
     * @param map 贴纸map
     * @param target 目标字符串
     * @return -1 代表无解
     */
    private static int doBrute(HashMap<String, Integer> dp, int[][] map, String target) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        int res = Integer.MAX_VALUE;
        int n = map.length;
        char[] targetArr = target.toCharArray();
        int[] targetMap = new int[26];
        for (char c : targetArr) {
            targetMap[c - 'a']++;
        }
        for (int i = 0; i < n; i++) {
            // 遍历每一张贴纸
            if (map[i][targetArr[0] - 'a'] == 0) {
                // 优化，先只针对target的按自然序的首个字母来
                // target = "horn"，那么就先针对字母h来试，没有字母h的贴纸先跳过
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                // 假设使用该贴纸，计算出使用该贴纸后还剩那些字母
                if (targetMap[j] > 0) {
                    for (int k = 0; k < Math.max(0, targetMap[j] - map[i][j]); k++) {
                        // 把剩余的字母转换成str
                        sb.append((char) ('a' + j));
                    }
                }
            }
            String rest = sb.toString();
            System.out.println(rest);
            // 递归剩余的字母
            int tmp = doBrute(dp, map, rest);
            if (tmp != -1) {
                res = Math.min(res, 1 + tmp);
            }
        }
        dp.put(target, res == Integer.MAX_VALUE ? -1 : res);
        return dp.get(target);
    }
}
