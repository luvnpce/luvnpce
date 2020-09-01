package rpcdemo.rpc.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpcdemo.util.SerializeUtil;
import rpcdemo.rpc.MyDispatcher;
import rpcdemo.rpc.protocol.MyContent;
import rpcdemo.rpc.protocol.MyHeader;
import rpcdemo.rpc.protocol.Package;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

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
