package bigdata;

/**
 * 有一个包含100亿个URL的大文件，假设每个URL占用64B，
 * 请找出其中所有重复的URL
 * 解法：
 * 假设我们有100台机器，那么我们就用哈希函数去计算每个url的哈希值，然后用100取模，分配给每个机器去计算
 *  - 计算：机器里可以分很多个小文件，通过哈希函数2去判断url进入哪个小文件，如果小文件里已有该url，那么该url次数++
 * 如果我们下面的机器也没办法处理那么多的url，就再次进行拆分
 * ==========================================
 * 补充:
 * 某搜索公司一天的用户搜索词汇是海量的(百亿数据量)，
 * 请设计一种求出每天热门Top100词汇的可行办法
 * 解法：
 * 解法相同，在机器在merge时就是把每个小文件里面的top100汇总，选出最终的top100
 * 然后每个机器返回自己的top100，最终汇总出100亿里面的top100
 * ==========================================
 * 优化：
 * 使用MaxHeap
 * 每个小文件的内部结构使用MaxHeap，然后一个机器内的每个文件的heap头部再组成一个MaxHeap
 * 每次我们从机器的MaxHeap中pop一个最大的，那个就是整个机器最大的
 * 然后被pop的文件MaxHeap会调整，导致机器的MaxHeap也跟着调整
 */
public class Question_04 {
}