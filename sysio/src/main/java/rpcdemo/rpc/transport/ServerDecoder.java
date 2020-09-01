package rpcdemo.rpc.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rpcdemo.rpc.protocol.MyContent;
import rpcdemo.rpc.protocol.MyHeader;
import rpcdemo.rpc.protocol.Package;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * 解码器：为了处理并发时buffer里面进来多个数据包
 * - 因为有多个数据包同时进来，可能导致一个buffer里面的数据不完整
 * - 需要保留这个buffer尾部残缺的数据和下一个buffer里面的头部数据做拼接
 */
public class ServerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 这里的85是从MyHeader里面计算出来的
        // 先读header
        while (in.readableBytes() > 102) {
            byte[] bytes = new byte[102];
            // getBytes可以读取，但是不会移动指针
            in.getBytes(in.readerIndex(), bytes);
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(bin);
            MyHeader header = (MyHeader) oin.readObject();

            // 再读content
            if (in.readableBytes() >= header.getDataLength()) {
                // 移动指针（之前getBytes没有移动）
                in.readBytes(102);
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
