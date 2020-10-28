package greedy;

import java.util.Arrays;

/**
 * 有一个会议室，给定一组会议（开始、结束时间），保证没有会议冲突的情况下，想办法安排最多的会议
 */
public class BestArrangement {

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (brute(programs) != greedy(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    /**
     * 贪心：根据会议的结束时间排序（升序）
     */
    public static int greedy(Program[] programs) {
        Arrays.sort(programs, (o1, o2) -> o1.end - o2.end);
        int currentTime = 0;
        int done = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= currentTime) {
                done++;
                currentTime = programs[i].end;
            }
        }
        return done;
    }

    /**
     * 暴力
     */
    public static int brute(Program[] programs) {
        if (null == programs || programs.length == 0) {
            return 0;
        }
        return doBrute(programs, 0, 0);
    }

    /**
     * @param programs 只保存还剩的会议
     * @param done 已经安排了多少会议
     * @param currentTime 当前的时间点
     * @return
     */
    private static int doBrute(Program[] programs, int done, int currentTime) {
        if (programs.length == 0) {
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= currentTime) {
                Program[] copy = copyButExcept(programs, i);
                max = Math.max(max, doBrute(copy, done+1, programs[i].end));
                // 这里没有恢复现场因为我们每次都copy了一个新的programs
            }
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    /**
     * 会议
     */
    public static class Program {
        // 开始时间
        public int start;
        // 结束时间
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
