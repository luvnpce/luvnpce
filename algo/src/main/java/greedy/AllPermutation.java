package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AllPermutation {

    public static void main(String[] args) {
        String[] strs = {"aa", "bb", "cc", "dd"};
        ArrayList<String> res = new ArrayList<>();
        allPermutation(strs, new HashSet<Integer>(), "", res);
        System.out.println(Arrays.toString(res.toArray()));
        System.out.println(res.size());
    }

    /**
     * DFS O(N!)
     * @param strs 原数组
     * @param used 已经使用过的数组下标
     * @param path 目前拼接的字符串
     * @param res 所有的拼接结果
     */
    public static void allPermutation(String[] strs, HashSet<Integer> used, String path, ArrayList<String> res) {
        if (used.size() == strs.length) {
            // 已经使用过数组里所有的字符串了
            res.add(path);
            return;
        }

        for (int i = 0; i < strs.length; i++) {
            if (!used.contains(i)) {
                used.add(i);
                // 先走使用i位置的分支
                allPermutation(strs, used, path+strs[i], res);
                // 撤回刚才的操作，再试其他的分支
                used.remove(i);
            }
        }
    }
}
