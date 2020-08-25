package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

public class MyNetty {

    @Test
    public void myByteBuffer() {
        // direct buffer
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(8, 20);

        // 非direct非池化buffer
//        ByteBuf buffer = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);

        // 非direct池化buffer
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);

        print(buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        print(buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        print(buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        print(buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        print(buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        print(buffer);

    }

    public static void print(ByteBuf buffer) {
        System.out.println("buffer.isReadable(): " + buffer.isReadable());
        System.out.println("buffer.readerIndex(): " + buffer.readerIndex());
        System.out.println("buffer.readableBytes():" + buffer.readableBytes());
        System.out.println("buffer.isWritable():" + buffer.isWritable());
        System.out.println("buffer.writerIndex():" + buffer.writerIndex());
        System.out.println("buffer.writableBytes():" + buffer.writableBytes());
        System.out.println("buffer.capacity():" + buffer.capacity());
        System.out.println("buffer.maxCapacity():" + buffer.maxCapacity());
        System.out.println("buffer.isDirect():" + buffer.isDirect());
        System.out.println("=========================================");
    }

    @Test
    public void clientMode() throws Exception {
        // netty封装的多路复用器
        NioEventLoopGroup group = new NioEventLoopGroup();

        NioSocketChannel clientChannel = new NioSocketChannel();
        group.register(clientChannel); // epol_ctl

        // 通过响应式的方式，添加一个读取事件的handler
        ChannelPipeline pipeline = clientChannel.pipeline();
        // 当有数据进来时，就会调用该handler
        pipeline.addLast(new MyReadHandler());

        // 链接（异步）
        ChannelFuture connect = clientChannel.connect(new InetSocketAddress("127.0.0.1", 9090));
        // 确认已经链接成功
        ChannelFuture sync = connect.sync();

        // 发送数据
        ByteBuf buff = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = clientChannel.writeAndFlush(buff);
        send.sync();


        // 监听连接是否关闭
        sync.channel().closeFuture().sync();
        System.out.println("client over");

    }

    @Test
    public void serverMode() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        NioServerSocketChannel server = new NioServerSocketChannel();

        group.register(server);
        // 不知道什么时候有数据过来，所以需要处理的handler
        ChannelPipeline pipeline = server.pipeline();
        // 接受客户端（并且要注册到Selector）
        pipeline.addLast(new MyAcceptHandler(group, new MyReadHandler()));
        ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 9090));

        // 同步----关闭----同步
        bind.sync().channel().closeFuture().sync();
        System.out.println("server closed");



    }

    @Test
    public void nettyClient() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyReadHandler());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 9090));

        Channel client = connect.sync().channel();
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world".getBytes());
        ChannelFuture send = client.writeAndFlush(byteBuf);
        send.sync();

        client.closeFuture().sync();
    }

    @Test
    public void nettyServer() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 注意，这个的group需要传2个group，一个是accept监听，一个是数据传输（可以传同一个group）
        ChannelFuture bind = bootstrap.group(group, group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyReadHandler());
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1", 9090));
        bind.sync().channel().closeFuture().sync();
    }
}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup group;

    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup group, ChannelHandler handler) {
        this.group = group;
        this.handler = handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server register");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 在accept时，读到的是SocketChannel
        // 这里netty框架会自己accept
        SocketChannel client = (SocketChannel) msg;
        // 1.注册
        group.register(client);
        // 2.响应式
        // 这里添加的是ChannelInit，但是在添加时会触发ChannelInit的channelRegistered方法，
        // 会在后面继续添加一个新的MyReadHandler
        client.pipeline().addLast(handler);
    }
}

/**
 * 这个只是作为一个过桥，当客户端被注册到多路复用器时，channelRegistered方法就会被调用
 * 然后通过重写channelRegistered方法，我们再去添加我们自定义的handler
 */
@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        client.pipeline().addLast(new MyReadHandler());
        // 最后需要把这个过桥从pipeline里面删除
        ctx.pipeline().remove(this);
    }
}

/**
 * 因为在MyAcceptHandler里面，每进来一个连接我们都要注册一个MyReadHandler去读取数据
 * - 所以这个handler必须得是@Sharable
 * - 还有一种方法就是通过一个过桥（ChannelInit），以响应式的方式来添加MyReadHandler
 */
class MyReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 收到的数据肯定是bytebuffer，先强转
        ByteBuf buf = (ByteBuf) msg;

        // read的会移动指针
        // CharSequence charSequence = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);

        // get不会移动，但是要指定区间
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(charSequence);
        ctx.writeAndFlush(buf);
    }
}
