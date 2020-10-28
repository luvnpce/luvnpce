package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * 给一组由字符串组成的数组：[az,bc,dad,aeq,a]
 * 找出自然序最小的一个拼接组合
 */
public class LowestLexicography {

    public static void main(String[] args) {
        String[] strs = {"az", "bc", "dad", "aeq", "a"};
        String lowest = findByAllPermutation(strs);
        String lowest2 = findByComparator(strs);
        System.out.println(lowest.equals(lowest2));
    }

    /**
     * 使用排序
     */
    private static String findByComparator(String[] strs) {
        if (null == strs || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, (o1, o2) -> (o1+o2).compareTo(o2+o1));
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            res.append(strs[i]);
        }
        return res.toString();
    }

    /**
     * 先把所有可能都找出来，然后一个个比较，找出自然序最小的
     */
    private static String findByAllPermutation(String[] strs) {
        ArrayList<String> all = new ArrayList<>();
        AllPermutation.allPermutation(strs, new HashSet<Integer>(), "", all);

        String lowest = all.get(0);
        for (String s : all) {
            if (s.compareTo(lowest) < 1) {
                lowest = s;
            }
        }
        return lowest;
    }
}
