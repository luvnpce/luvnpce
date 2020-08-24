package reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 每线程对应一个selector
 * 每个客户端只绑定到一个selector上
 * 多线程情况下，该主机，该程序的并发客户端被分配到多个selector上
 */
public class SelectorThread implements Runnable {

    Selector selector = null;
    SelectorThreadGroup workerGroup;
    LinkedBlockingQueue<Channel> queue = new LinkedBlockingQueue<>();

    public SelectorThread(SelectorThreadGroup group) {
        try {
            this.selector = Selector.open();
            this.workerGroup = group;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            int num = 0;
            try {
                // 1. select
//                System.out.println(Thread.currentThread().getName() + ": before select......." + selector.keys().size());
                num = selector.select(); // 这里会阻塞
//                System.out.println(Thread.currentThread().getName() + ": after select......." + selector.keys().size());
                if (num > 0) {
                    // 2. selectKeys
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {

                        }
                    }
                }
                // 3. 从队列去取channel出来注册
                if (!queue.isEmpty()) {
                    Channel channel = queue.take();
                    if (channel instanceof ServerSocketChannel) {
                        // 注册listen
                        ServerSocketChannel server = (ServerSocketChannel) channel;
                        server.register(selector, SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName()+" register listen");
                    } else if (channel instanceof SocketChannel) {
                        // 注册client
                        SocketChannel client = (SocketChannel) channel;
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
                        client.register(selector, SelectionKey.OP_READ, byteBuffer);
                        System.out.println(Thread.currentThread().getName()+" register client: " + client.getRemoteAddress());
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHandler(SelectionKey key) {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();

        while (true) {
            try {
                int num = client.read(buffer);
                if (num > 0) {
                    // 将读到的内容翻转，然后直接写出
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (num == 0) {
                    break;
                } else if (num < 0) {
                    // 客户端断开
                    System.out.println("client: " + client.getRemoteAddress() + " closed.....");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println("acceptHandler......");
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            // 选择一个selector去注册
            this.workerGroup.nextSelector(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
