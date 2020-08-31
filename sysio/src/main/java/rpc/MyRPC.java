package rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 写一个RPC
 * 1. 来回通信，连接数量，拆包
 * 2. 动态代理，序列化，协议封装
 * 3. 连接池
 */
public class MyRPC {


    @Test
    public void startServer() {
        MyCar myCar = new MyCar();
        MyBike myBike = new MyBike();
        MyDispatcher dispatcher = new MyDispatcher();
        dispatcher.register(Car.class.getName(), myCar);
        dispatcher.register(Bike.class.getName(), myBike);


        NioEventLoopGroup boss = new NioEventLoopGroup(20);
        NioEventLoopGroup worker = boss;

        ChannelFuture bind = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client port: " + ch.remoteAddress().getPort());
                        ch.pipeline()
                                .addLast(new ServerDecoder())
                                .addLast(new ServerRequestHandler(dispatcher));
                    }
                })
                .bind(new InetSocketAddress("localhost", 9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startClient() {
        new Thread(() -> {
            startServer();
        }).start();

        System.out.println("Server started......");

        int size = 10;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Car car = doProxy(Car.class);
                String res = car.foo(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " return value：" + res);
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 泛型方法的基本介绍
     * @param clazz 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public static <T> T doProxy(Class<T> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] methodInfo = {clazz};

        return (T) Proxy.newProxyInstance(classLoader, methodInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 如何设计consumer对provider调用的过程

                // 1. 调用服务，方法，参数 -> 最后封装成message
                String name = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                MyContent content = new MyContent();
                content.setArgs(args);
                content.setName(name);
                content.setMethodName(methodName);
                content.setParameterTypes(parameterTypes);

                // 把MyContent转换成字节数组
                byte[] msgBody = SerializeUtil.doSerialize(content);

                // 2. requestId + message，本地缓存
                // 协议结构：header + msgBody
                MyHeader header = assembleHeader(msgBody);
                byte[] msgHeader = SerializeUtil.doSerialize(header);

                // 3. 连接池（取得链接）
                // 获取连接过程中：通过ip和端口号去hashmap里面找连接池，如果为空，则创建
                // 获取到连接池后再用随机算法去获取一个连接，如果为空或者已失效，则去创建一个新的连接
                ClientFactory factory = ClientFactory.getFactory();
                NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090));

                // 4. 发送（IO out）
//                CountDownLatch latch = new CountDownLatch(1);
                long requestId = header.getRequestId();
                CompletableFuture<String> res = new CompletableFuture<>();
                ResponseCallbackMapping.addCallback(requestId, res);
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
                byteBuf.writeBytes(msgHeader).writeBytes(msgBody);
                ChannelFuture future = clientChannel.writeAndFlush(byteBuf);
                future.sync(); // 这里的sync仅代表的out，并不代表响应

                // 阻塞，等待响应回来
                // 5.处理返回的数据
                return res.get();
            }
        });
    }

    private static MyHeader assembleHeader(byte[] msgBody) {
        MyHeader header = new MyHeader();
        header.setFlag(0x14141414);
        header.setRequestId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        header.setDataLength(msgBody.length);
        return header;
    }
}

class ClientFactory {

    // 链接池里的链接数量
    private static final int poolSize = 1;

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

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = outboxes.get(address);
        if (null == clientPool) {
            outboxes.putIfAbsent(address, new ClientPool(poolSize));
            clientPool = outboxes.get(address);
        }

        int index = random.nextInt(poolSize);
        NioSocketChannel channel = clientPool.clients[index];
        if (null != channel && channel.isActive()) {
            System.out.println("repeated use of channel");
            return channel;
        }
        Object lock = clientPool.lock[index];
        synchronized (lock) {
            return clientPool.clients[index] = create(address);
        }
    }

    private NioSocketChannel create(InetSocketAddress address) {
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

class ResponseCallbackMapping {

    private static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallback(long requestId, CompletableFuture cf) {
        mapping.putIfAbsent(requestId, cf);
    }

    public static void runCallback(Package pkg) {
        long requestId = pkg.getHeader().getRequestId();
        CompletableFuture cf = mapping.get(requestId);
        if (null != cf) {
            cf.complete(pkg.getContent().getResult());
            removeCallback(requestId);
        }
    }

    public static void removeCallback(long requestId) {
        mapping.remove(requestId);
    }

}

class ClientResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Package pkg = (Package) msg;
        // 回调
        ResponseCallbackMapping.runCallback(pkg);
    }
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private MyDispatcher dispatcher;

    public ServerRequestHandler(MyDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理io
        Package pkg = (Package) msg;
        System.out.println(pkg.getContent().getArgs()[0]);

        // 给客户端返回数据，一共有几种做法
        // 1. Netty，通过自己的EventLoop来处理业务及返回
        //      - ctx.executor().execute(new Runnable()
        //          - 串行（一个一个请求串行执行，处理IO，处理业务，再到下一个请求）
        //          - 先批量处理完IO，把业务部分封装成task，再同一处理
        // 2. Netty，自己的EventLoop只处理IO，把task交给其他EventLoop
        //      - ctx.executor().parent().next().execute()
        //      - 这个的好处就是，当一个链接发送了大量的请求，那么这个EventLoop就会一直在运作，用这个方法可以把处理分散开
        // 3. 自己创建线程池
        String ioThreadName = Thread.currentThread().getName();
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                // 通过反射找到相应的对象
                // dubbo使用的是javassist，效率更高
                String serviceName = pkg.getContent().getName();
                String methodName = pkg.getContent().getMethodName();
                Object o = dispatcher.get(serviceName);
                Class<?> clazz = o.getClass();
                Object result = null;
                try {
                    Method method = clazz.getMethod(methodName, pkg.getContent().getParameterTypes());
                    result = method.invoke(o, pkg.getContent().getArgs());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                String executorThreadName = Thread.currentThread().getName();
                MyContent content = new MyContent();
                content.setResult((String) result);
                byte[] contentBytes = SerializeUtil.doSerialize(content);

                MyHeader header = new MyHeader();
                header.setRequestId(pkg.getHeader().getRequestId());
                header.setFlag(0x14141424);
                header.setDataLength(contentBytes.length);
                byte[] headerBytes = SerializeUtil.doSerialize(header);

                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
                byteBuf.writeBytes(headerBytes);
                byteBuf.writeBytes(contentBytes);
                ctx.writeAndFlush(byteBuf);
            }
        });
    }
}

/**
 * 解码器：为了处理并发时buffer里面进来多个数据包
 * - 因为有多个数据包同时进来，可能导致一个buffer里面的数据不完整
 * - 需要保留这个buffer尾部残缺的数据和下一个buffer里面的头部数据做拼接
 */
class ServerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
// 这里的85是从MyHeader里面计算出来的
        // 先读header
        while (in.readableBytes() > 85) {
            byte[] bytes = new byte[85];
            // getBytes可以读取，但是不会移动指针
            in.getBytes(in.readerIndex(), bytes);
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(bin);
            MyHeader header = (MyHeader) oin.readObject();

            // 再读content
            if (in.readableBytes() >= header.getDataLength()) {
                // 移动指针（之前getBytes没有移动）
                in.readBytes(85);
                byte[] data = new byte[(int) header.getDataLength()];
                in.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);

                // 通信协议，判断是服务端接收请求数据还是客户端接收响应数据
                if (header.getFlag() == 0x14141414) {
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Package(header, content));
                } else if (header.getFlag() == 0x14141424) {
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Package(header, content));
                }

            } else {
                break;
            }
        }
    }
}

/**
 * 连接池
 */
class ClientPool {
    NioSocketChannel[] clients;
    Object[] lock;

    ClientPool(int size) {
        clients = new NioSocketChannel[size];
        lock = new Object[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }
}

/**
 * 通信的数据包
 * - 包含了MyHeader和MyContent
 */
class Package {

    private MyHeader header;

    private MyContent content;

    public Package(MyHeader header, MyContent content) {
        this.header = header;
        this.content = content;
    }

    public MyHeader getHeader() {
        return header;
    }

    public void setHeader(MyHeader header) {
        this.header = header;
    }

    public MyContent getContent() {
        return content;
    }

    public void setContent(MyContent content) {
        this.content = content;
    }
}

/**
 * 通信的头部协议
 * 1. 标志位
 * 2. request id
 * 3. data length
 */
class MyHeader implements Serializable {

    int flag;
    long requestId;
    long dataLength;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }
}

/**
 * 通信的内容
 */
class MyContent implements Serializable {

    private String name;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
