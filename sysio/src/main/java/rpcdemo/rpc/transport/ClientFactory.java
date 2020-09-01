package rpcdemo.rpc.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpcdemo.util.SerializeUtil;
import rpcdemo.rpc.ResponseCallbackMapping;
import rpcdemo.rpc.protocol.MyContent;
import rpcdemo.rpc.protocol.MyHeader;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ClientFactory {

    // 链接池里的链接数量
    private static final int poolSize = 5;

    // 一个consumer可以链接很多个provider，每有一个provider都有自己的pool
    private ConcurrentHashMap<InetSocketAddress, ClientPool> outboxes = new ConcurrentHashMap<>();

    private Random random = new Random();

    private static final ClientFactory factory;

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    public static CompletableFuture<?> transport(MyContent content) {
        // 把MyContent转换成字节数组
        byte[] msgBody = SerializeUtil.doSerialize(content);

        // todo header协议和content解耦
        // 2. requestId + message，本地缓存
        // 协议结构：header + msgBody
        MyHeader header = MyHeader.assembleHeader(msgBody);
        byte[] msgHeader = SerializeUtil.doSerialize(header);

        // 3. 连接池（取得链接）
        // 获取连接过程中：通过ip和端口号去hashmap里面找连接池，如果为空，则创建
        // 获取到连接池后再用随机算法去获取一个连接，如果为空或者已失效，则去创建一个新的连接
        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090));

        // 4. 发送（IO out）
        long requestId = header.getRequestId();
        CompletableFuture<String> res = new CompletableFuture<>();
        ResponseCallbackMapping.addCallback(requestId, res);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        byteBuf.writeBytes(msgHeader).writeBytes(msgBody);
        ChannelFuture future = clientChannel.writeAndFlush(byteBuf);
//        try {
//            future.sync(); // 这里的sync仅代表的out，并不代表响应
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return res;
    }

    public NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = outboxes.get(address);
        // 这里使用的DCL
        if (null == clientPool) {
            synchronized (outboxes) {
                if (null == clientPool) {
                    outboxes.putIfAbsent(address, new ClientPool(poolSize));
                    clientPool = outboxes.get(address);
                }
            }
        }

        int index = random.nextInt(poolSize);
        if (null != clientPool.clients[index] && clientPool.clients[index].isActive()) {
            System.out.println("repeated use of channel");
            return clientPool.clients[index];
        } else {
            Object lock = clientPool.lock[index];
            synchronized (lock) {
                if (null == clientPool.clients[index] || !clientPool.clients[index].isActive()) {
                    clientPool.clients[index] = create(address);
                }
                return clientPool.clients[index];
            }
        }
    }

    private NioSocketChannel create(InetSocketAddress address) {
        System.out.println("create");
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ChannelFuture connect = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerDecoder()).addLast(new ClientResponseHandler());
                    }
                }).connect(address);
        try {
            NioSocketChannel client = (NioSocketChannel) connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
