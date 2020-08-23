import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SocketMultiplexingSingleThread {

    private ServerSocketChannel server = null;
    /**
     * Selector：Java对多路复用器的封装
     */
    private Selector selector = null;
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            /**
             * select、poll、epoll，可以通过参数修改返回的是哪个类型，默认返回epoll
             */
            selector = Selector.open();
            /**
             * 如果是
             * select、poll：在jvm里面开辟一个数组，把server的fd放进去
             * epoll：epoll_ctl，把server的fd注册到epoll开辟的空间里面去并指定对其监听的事件（accept）
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println(keys.size()+"   size");
                /**
                 * 这里的select()代表的是
                 * select、poll：内核的select(fd)、poll(fd)
                 * epoll：内核的epoll_wait
                 * 参数可以不传，或者传0：阻塞
                 */
                while (selector.select(500) > 0) {
                    /**
                     * selectionKeys：返回的有状态的fd集合，这里只是返回fd
                     */
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    /**
                     * 挨个去处理这些fd里的数据读写
                     * Socket有分监听（listen）和通信（读写），需要拿到SelectionKey来判断是哪一个
                     */
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            /**
                             * 因为这里是要去接受一个数据传输的链接，所以方法里面要对这个链接的fd进行处理
                             * select、poll：添加到Jvm的数组里面去
                             * epoll：通过epoll_ctl把这个fd注册到epoll_create开辟的内核空间
                             */
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            /**
                             * 如果这里需要处理的数据特别大，那么单线程处理会比较吃力
                             * redis、tomcat 8,9就使用了IO Threads，把IO和数据读写处理做了解耦
                             */
                            key.cancel();
                            readHandler(key);
                        } else if (key.isWritable()) {
                            /**
                             * key.isWritable校验的是内核里面的Send-Queue有没有空间，如果有空间就会一直返回true
                             * 所以我们不能依靠这个校验来判断是否要进行写的处理，我们想写是情况只有是：
                             * 1. 当客户端有数据传输过来了，我们想要写回给客户端
                             * 2. Send-Queue有足够空间时
                             * 只有满足这两项我们才能进行写的处理，所以，只有在读到数据（readHandler）里面我们才注册写的事件
                             * client.register(key.selector(),SelectionKey.OP_WRITE,buffer);
                             */
                            key.cancel();
                            writeHandler(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        new Thread(()->{
            System.out.println("read handler.....");
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            int read = 0;
            try {
                while (true) {
                    read = client.read(buffer);
                    System.out.println(Thread.currentThread().getName()+ " " + read);
                    if (read > 0) {
                        key.interestOps(  SelectionKey.OP_READ);

                        client.register(key.selector(),SelectionKey.OP_WRITE,buffer);
                    } else if (read == 0) {

                        break;
                    } else {
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }


    public void writeHandler(SelectionKey key) {
        new Thread(()->{
            System.out.println("write handler...");
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.flip();
            while (buffer.hasRemaining()) {
                try {

                    client.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer.clear();
//            key.cancel();

//            try {
////                client.shutdownOutput();
//
////                client.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }).start();

    }


    public static void main(String[] args) {
        SocketMultiplexingSingleThread service = new SocketMultiplexingSingleThread();
        service.start();
    }
}
