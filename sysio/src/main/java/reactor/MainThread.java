package reactor;

public class MainThread {

    public static void main(String[] args) {
        // 创建IO Thread
        SelectorThreadGroup boss = new SelectorThreadGroup(3);
        SelectorThreadGroup worker = new SelectorThreadGroup(3);

        /**
         * 流程：
         * 1. 在boss中选择一个线程去注册Listen监听，触发bind，这个线程得拥有Boss下面的WorkerGroup的引用
         * 2. 当这个线程收到accept获得client之后，得把这个client分配给下面的WorkerGroup里的一个线程去处理
         */
        boss.setWorkerGroup(worker);
        // 把监听的server注册到某一个selector上
        boss.bind(9090);
        boss.bind(9080);
        boss.bind(9070);
        boss.bind(9060);
    }
}
