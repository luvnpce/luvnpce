package leetcode;

/**
 * https://leetcode.com/problems/binary-tree-cameras/
 * difficulty: hard
 *
 * You are given the root of a binary tree. We install cameras on the tree nodes where each camera
 * at a node can monitor its parent, itself, and its immediate children.
 *
 * Return the minimum number of cameras needed to monitor all nodes of the tree.
 */
public class LeetCode_968_BinaryTreeCameras {

    /**
     * O(N)，相比solution减少了常数时间
     * 把三种情况下的解合并成一个，通过status来区分这个节点对应的是哪种情况
     */
    public static int greedy(TreeNode root) {
        Data data = doGreedy(root);
        return (int) data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
    }

    public static Data doGreedy(TreeNode node) {
        // base case
        if (null == node) {
            // 空节点，那么默认是已覆盖的，且不需要相机
            return new Data(Status.COVERED_NO_CAMERA, 0);
        }
        Data left = doGreedy(node.left);
        Data right = doGreedy(node.right);
        long cameras = left.cameras + right.cameras;
        if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
            // 左孩子或右孩子其中有一个节点没有被覆盖，那么自己这个节点必须要有相机
            return new Data(Status.COVERED_HAS_CAMERA, cameras + 1);
        }
        if (left.status == Status.COVERED_HAS_CAMERA || right.status == Status.COVERED_HAS_CAMERA) {
            // 左孩子和右孩子之间有一个有相机，那么自己就不需要有相机且仍会被覆盖
            return new Data(Status.COVERED_NO_CAMERA, cameras);
        }
        // 左右孩子都被覆盖，且都没有相机，那么自己可以不需要有相机，但是不会被覆盖
        return new Data(Status.UNCOVERED, cameras);
    }

    /**
     * 把三种情况下的解合并成一个，通过status来区分这个节点对应的是哪种情况
     */
    public static class Data {
        public Status status;
        public long cameras;

        public Data(Status status, long cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }

    public static enum Status {
        UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
    }

    /**
     * O(N)
     * 每个节点返回3种信息，分别代表这个节点可能存在的3种情况
     * 1. x节点没有相机，x已经被覆盖，x下方所有节点也已经被覆盖
     * 2. x节点有相机，x已经被覆盖，x下方所有节点也已经被覆盖
     * 3. x节点没有相机，x没有被覆盖，x下方所有节点已经被覆盖
     */
    public static int solution(TreeNode root) {
        Info data = doSolution(root);
        return (int) Math.min(data.uncovered + 1, Math.min(data.coveredHasCamera, data.coveredNoCamera));
    }

    private static Info doSolution(TreeNode node) {
        // base case
        if (null == node) {
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }
        Info left = doSolution(node.left);
        Info right = doSolution(node.right);

        // 情况1，自己被覆盖，但是自己节点没有相机，自己下方所有节点都被覆盖
        long p1 = left.coveredHasCamera + right.coveredHasCamera;   // 左边覆盖有相机 + 右边覆盖有相机
        long p2 = left.coveredNoCamera + right.coveredHasCamera;    // 左边覆盖没相机 + 右边覆盖有相机
        long p3 = left.coveredHasCamera + right.coveredNoCamera;    // 左边覆盖有相机 + 右边覆盖没相机
        long coveredNoCamera = Math.min(p1, Math.min(p2, p3));
        // 情况2，自己被覆盖，自己节点有相机，自己下方所有节点都被覆盖
        long coveredHasCamera = 1   // 自己节点有相机，所以要+1
                + Math.min(left.uncovered, Math.min(left.coveredHasCamera, left.coveredNoCamera))       // 找左边最小值
                + Math.min(right.uncovered, Math.min(right.coveredHasCamera, right.coveredNoCamera));   // 找右边最小值
        // 情况3，自己没有被覆盖，但是自己下方所有节点都被覆盖
        long uncovered = left.coveredNoCamera + right.coveredNoCamera;
        return new Info(uncovered, coveredNoCamera, coveredHasCamera);
    }

    /**
     * 节点x，3种情况
     * 1. x节点没有相机，x已经被覆盖，x下方所有节点也已经被覆盖
     * 2. x节点有相机，x已经被覆盖，x下方所有节点也已经被覆盖
     * 3. x节点没有相机，x没有被覆盖，x下方所有节点已经被覆盖
     */
    public static class Info {
        public long uncovered; // x没有被覆盖，x为头的树至少需要几个相机
        public long coveredNoCamera; // x被覆盖，但是x节点上没有相机，x为头的树至少需要几个相机
        public long coveredHasCamera; // x被覆盖，x节点上有相机，x为头的树至少需要几个相机

        public Info(long uncovered, long coveredNoCamera, long coveredHasCamera) {
            this.uncovered = uncovered;
            this.coveredNoCamera = coveredNoCamera;
            this.coveredHasCamera = coveredHasCamera;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

}
