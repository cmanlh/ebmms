package com.lifeonwalden.ebmms.register;

import com.lifeonwalden.ebmms.common.codec.KryoDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Server implements InitializingBean, DisposableBean {
    private final static Logger logger = LogManager.getLogger(Server.class);

    @Value(value = "${ebmms.register.boss.thread}")
    private int bossThreadCount;

    @Value(value = "${ebmms.register.worker.thread}")
    private int workerThreadCount;

    @Value(value = "${ebmms.register.host}")
    private String host;

    @Value(value = "${ebmms.register.port}")
    private int port;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Channel channel;

    public static void main(String[] args) {
        Server server = new Server();
        server.bossThreadCount = 1;
        server.host = "localhost";
        server.port = 9090;
        server.workerThreadCount = 1;

        try {
            server.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        bossGroup = new NioEventLoopGroup(this.bossThreadCount);
        workerGroup = new NioEventLoopGroup(this.workerThreadCount);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addFirst(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4)).addLast(new KryoDecoder());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = bootstrap.bind(host, port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            channel = f.channel();
            channel.closeFuture().sync();

            System.out.println("pass");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() throws Exception {
        channel.close();
    }
}
