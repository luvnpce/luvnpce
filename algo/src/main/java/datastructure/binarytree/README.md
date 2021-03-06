###二叉树的递归套路
1. 假设以X节点为头，假设可以向X的左树和X的右树要任何信息
2. 在上一步的假设下，讨论以X为头节点的树，得到答案的可能性（重要）
    - 和X有关
    - 和X无关
3. 列出所有可能性后，确定到底需要向左树和右树要什么样的信息
4. 把左树信息和右树信息求全集，就是任何一颗子树都需要返回的信息S
5. 递归函数都返回S，每一棵子树都这么要求
6. 写代码，在代码中考虑如何把左树的信息和右树的信息整合出整棵树的信息


###有序表区别
* AVL数：在任何节点上，他的左子树和右子树高度差不能 > 1
    * node违规的情况：
        * LL：node右旋一次解决
        * LR：node.left左旋（改成LL），然后node右旋
        * RR：node左旋一次解决
        * RL：node.right右旋（改成RR），然后node左旋
* Size Balanced树：每棵子树的大小不小于其兄弟的子树大小
    * node违规情况：
        * LL：node.right大小 < node.left.left，node右旋解决
        * LR：node.right大小 < node.left.right，node.left左旋（改成LL），然后node右旋
        * RR：node.left大小 < node.right.right，node左旋解决
        * RL：node.left大小 < node.right.left，node.right右旋（改成RR），然后node左旋
    * 平衡性没有AVL严格，所以效率更高
* 红黑树：树的最长路径不大于最短路径长度的两倍