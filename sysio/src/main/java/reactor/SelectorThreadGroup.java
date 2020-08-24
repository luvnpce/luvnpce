package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {

    // SelectorThread数组
    SelectorThread[] threads;
    // 当前Group下面的WorkerGroup，默认没有下属WorkerGroup，所以指向自己
    SelectorThreadGroup workerGroup = this;
    // 服务端
    ServerSocketChannel server;
    // SelectorThread数组下标
    AtomicInteger index = new AtomicInteger(0);

    public SelectorThreadGroup(int num) {
        threads = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            threads[i] = new SelectorThread(this);

            // 直接开始跑
            new Thread(threads[i]).start();
        }
    }

    public void setWorkerGroup(SelectorThreadGroup group) {
        this.workerGroup = group;
        // 重新设置一下每个SelectorThread里面的WorkerGroup
        for (int i = 0; i < threads.length; i++) {
            threads[i].workerGroup = group;
        }
    }

    public void bind(int port) {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 注册到哪个selector上面去
            nextSelectorV3(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 混杂模式（SelectorThread里面可能有server，也可能有client）
     * @param channel
     */
    public void nextSelector(Channel channel) {
        int i = index.incrementAndGet() % threads.length;
        SelectorThread selectorThread = threads[i];

        // 通过队列传递数据
        selectorThread.queue.add(channel);
        // 通过打断阻塞，让对应的线程自取去注册selector
        selectorThread.selector.wakeup();
    }

    /**
     * 混杂模式（SelectorThreads[0]固定只处理server listen，其他的负责client
     * @param channel
     */
    public void nextSelectorV2(Channel channel) {
        if (channel instanceof ServerSocketChannel) {
            try {
                threads[0].queue.put(channel);
                threads[0].selector.wakeup();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            int i = index.incrementAndGet() % (threads.length-1);
            SelectorThread selectorThread = threads[i+1];

            // 通过队列传递数据
            selectorThread.queue.add(channel);
            // 通过打断阻塞，让对应的线程自取去注册selector
            selectorThread.selector.wakeup();
        }
    }

    /**
     * 主从模式（自己只负责处理server listen，client的交给下属的WorkerGroup去处理
     * @param channel
     */
    public void nextSelectorV3(Channel channel) {
        try {
            if (channel instanceof ServerSocketChannel) {
                // 因为是server listen，所以在当前组里面的SelectorThread里去执行
                int i = index.incrementAndGet() % threads.length;
                SelectorThread selectorThread = threads[i];
                selectorThread.queue.put(channel);
                selectorThread.selector.wakeup();
            } else {
                // 其他的交给下属WorkerGroup去执行
                int i = index.incrementAndGet() % workerGroup.threads.length;
                SelectorThread selectorThread = workerGroup.threads[i];

                // 通过队列传递数据
                selectorThread.queue.add(channel);
                // 通过打断阻塞，让对应的线程自取去注册selector
                selectorThread.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
