package com.junlong.netty.demo.nettyserver.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/7/29
 * @since 1.0.0
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        try {
            new EchoServer(8888).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void start() throws InterruptedException {
        EchoServerhandler echoServerhandler = new EchoServerhandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventExecutors).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoServerhandler);
                        }
                    });
            ChannelFuture f = serverBootstrap.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully().sync();
        }

    }

}
