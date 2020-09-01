package rpcdemo.rpc.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpcdemo.rpc.ResponseCallbackMapping;
import rpcdemo.rpc.protocol.Package;

public class ClientResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Package pkg = (Package) msg;
        // 回调
        ResponseCallbackMapping.runCallback(pkg);
    }
}
