package greedy;

import java.util.HashSet;

/**
 * 给定一个字符串str, 由X和.两个字符组成
 * X代表墙，不可以放灯，也不能被点亮
 * .代表房子，可以放灯，也可以被点亮
 * 如果在i位置上放灯，那么位置i-1, i, i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要多少盏灯
 */
public class LightLamp {

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String str = randomString(len);
            int ans1 = brute(str);
            int ans2 = greey(str);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

    /**
     * 贪心：根据当前位置判断
     *      - 当前位置i是X，移动到i+1
     *      - 当前位置i是.，判断
     *          - i+1位置是X，那么必须在i放灯，移动到i+2
     *          - i+1位置是.，那么去i+1位置放灯，移动到i+3。因为：
     *              - i+2位置是X，在i+1放灯，移动到i+3
     *              - i+2位置是.，在i+1放灯，移动到i+3
     */
    private static int greey(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }
        int index = 0;
        int res = 0;
        char[] arr = str.toCharArray();
        while (index < arr.length) {
            if (arr[index] == 'X') {
                // 当前位置i是X，移动到i+1
                index++;
            } else {
                // 当前位置i是. 肯定需要放灯，先放灯
                res++;
                // 再来决定走到哪一格
                if (index + 1 == arr.length) {
                    // 已经到最后一格了，结束
                    break;
                } else {
                    if (arr[index + 1] == 'X') {
                        // i+1位置是X，移动到i+2
                        index += 2;
                    } else {
                        // i+1位置是.，移动到i+3
                        index += 3;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 暴力：在每个地方尝试
     * - 放灯
     * - 不放灯
     */
    private static int brute(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }
        return doBrute(str.toCharArray(), 0, new HashSet<>());
    }

    /**
     * @param arr 整个街道
     * @param index 我们当前在的位置，[0....index-1]的位置都已经处理完毕
     * @param lights 存储那些位置放了灯
     * @return
     */
    private static int doBrute(char[] arr, int index, HashSet<Integer> lights) {
        if (index == arr.length) {
            // 已经全部处理完
            for (int i = 0; i < arr.length; i++) {
                // 遍历一遍街道，看看有没有全部被点亮
                if (arr[i] != 'X') {
                    // 当前位置不是墙，检验该位置有没有被点亮
                    if (!lights.contains(i-1) && !lights.contains(i) && !lights.contains(i+1)) {
                        // 前中后都没有放灯，失败
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        }
        //
        int no = doBrute(arr, index+1, lights);
        int yes = Integer.MAX_VALUE;
        if (arr[index] == '.') {
            lights.add(index);
            yes = doBrute(arr, index+1, lights);
            // 这里需要恢复现场，因为我们递归始终都用的同一个lights
            lights.remove(index);
        }
        return Math.min(no, yes);
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }
}
