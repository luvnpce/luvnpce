package leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/the-skyline-problem/
 * difficulty: hard
 *
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance.
 * Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.
 *
 * The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti, heighti]:
 *
 * lefti is the x coordinate of the left edge of the ith building.
 * righti is the x coordinate of the right edge of the ith building.
 * heighti is the height of the ith building.
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 *
 * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x1,y1],[x2,y2],...].
 * Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.
 *
 * Note: There must be no consecutive horizontal lines of equal height in the output skyline.
 * For instance, [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5
 * should be merged into one in the final output as such: [...,[2 3],[4 5],[12 7],...]
 */
public class LeetCode_218_SkylineProblem {

    /**
     * 把一栋高楼转变成两个Node，假设高楼a = [2,9,10]
     * 那么两个node分别是
     * Node{x=2, true, 10}
     * Node{x=9, false, 10}
     * 意思分别是，高楼a在位置2上面 + 10， 在位置9上面 - 10
     */
    public static class Node {
        private int x;
        private boolean isAdd;
        private int height;

        public Node(int x, boolean isAdd, int height) {
            this.x = x;
            this.isAdd = isAdd;
            this.height = height;
        }
    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.x - o2.x;
        }
    }

    /**
     * 解题思路：
     * 1. 把所有高楼转换成Node对象，然后按照x位置从小到大排序
     * 2. 遍历Node数组：
     * 2.1 通过有序表mapHeightTimes，记录每个高度出现的次数
     * 如果Node.isAdd = true， mapHeightTimes(Node.height, times++)
     * 如果Node.isAdd = false， mapHeightTimes(Node.height, times--)，times为0时直接删除该entry
     * 2.2 通过有序表mapXHeight，记录每个x位置的最高高度
     * 因为我们有mapHeightTimes，所以我们可以知道当我们来到位置x时，该位置的最高高度是多少mapHeightTimes.lastKey()
     * 3. 遍历每个x位置，此时我们已经知道每个位置的高度了
     */
    public static List<List<Integer>> solution(int[][] m) {
        // 步骤1
        Node[] nodes = new Node[m.length * 2];
        for (int i = 0; i < m.length; i++) {
            nodes[i * 2] = new Node(m[i][0], true, m[i][2]);
            nodes[i * 2 + 1] = new Node(m[i][1], false, m[i][2]);
        }
        Arrays.sort(nodes, new NodeComparator());

        // 步骤2
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();
        for (Node node : nodes) {
            // 组装mapHeightTimes
            if (node.isAdd) {
                if (!mapHeightTimes.containsKey(node.height)) {
                    mapHeightTimes.put(node.height, 1);
                } else {
                    mapHeightTimes.put(node.height, mapHeightTimes.get(node.height) + 1);
                }
            } else {
                if (mapHeightTimes.get(node.height) == 1) {
                    mapHeightTimes.remove(node.height);
                } else {
                    mapHeightTimes.put(node.height, mapHeightTimes.get(node.height) - 1);
                }
            }
            // 组装mapXHeight
            if (mapHeightTimes.isEmpty()) {
                // 兜底case
                mapXHeight.put(node.x, 0);
            } else {
                mapXHeight.put(node.x, mapHeightTimes.lastKey());
            }
        }

        // 步骤3
        List<List<Integer>> ans = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            Integer xPos = entry.getKey();
            Integer xHeight = entry.getValue();
            if (ans.isEmpty()) {
                // 第一个x，直接插入
                ans.add(new ArrayList<>(Arrays.asList(xPos, xHeight)));
            } else if (ans.get(ans.size() - 1).get(1) != xHeight) {
                // 而前面位置的高度对比，不一致才插入
                ans.add(new ArrayList<>(Arrays.asList(xPos, xHeight)));
            }
        }
        return ans;
    }
}
