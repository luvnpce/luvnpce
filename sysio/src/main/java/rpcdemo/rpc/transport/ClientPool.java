package rpcdemo.rpc.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 连接池
 */
public class ClientPool {

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
