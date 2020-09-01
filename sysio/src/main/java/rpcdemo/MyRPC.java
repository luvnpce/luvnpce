package rpcdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import rpcdemo.proxy.MyProxy;
import rpcdemo.rpc.MyDispatcher;
import rpcdemo.rpc.transport.ServerDecoder;
import rpcdemo.rpc.transport.ServerRequestHandler;
import rpcdemo.service.Bike;
import rpcdemo.service.Car;
import rpcdemo.service.MyBike;
import rpcdemo.service.MyCar;

import java.io.IOException;
import java.net.InetSocketAddress;

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
//        new Thread(() -> {
//            startServer();
//        }).start();
//
//        System.out.println("Server started......");

        int size = 10;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Car car = MyProxy.doProxy(Car.class);
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
}
