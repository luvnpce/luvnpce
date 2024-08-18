滑动窗口规则，复杂度 O(N)
1. 有两个指针L、 R
2. 当R++，代表新数从右侧进入
3. 当L++，代表已知的一个数从左侧出去
4. 保证 L <= R


最长模版
===========
初始化left，right，result，best
while (右指针没有到结尾) {
    right往右扩大，加入right对应的元素，更新当前result
    while (result不满足要求) {
         窗口缩小，移除left对应的元素，left往右移
    }
    更新最优结果best
    right++
}
return best


最短模版
============
初始化left，right，result，best
while (右指针没有到结尾) {
    right往右扩大，加入right对应的元素，更新当前result
    while (result满足要求) {
        更新最优结果best
        窗口缩小，移除left对应的元素，left往右移
    }
    right++
}
return best

