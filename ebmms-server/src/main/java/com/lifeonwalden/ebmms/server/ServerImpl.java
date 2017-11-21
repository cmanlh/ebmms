package com.lifeonwalden.ebmms.server;

import com.lifeonwalden.ebmms.common.codec.RequestDecoder;
import com.lifeonwalden.ebmms.common.codec.ResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl<T> implements Server {
    private final static Logger logger = LogManager.getLogger(ServerImpl.class);

    private int bossThreadCount;

    private int workerThreadCount;

    private int workerBackLogSize;

    private String host;

    private int port;

    private SimpleChannelInboundHandler<T> msgProcessor;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Channel channel;

    public ServerImpl(int bossThreadCount, int workerThreadCount, String host, int port, int workerBackLogSize, SimpleChannelInboundHandler<T> msgProcessor) throws InterruptedException {
        this.bossThreadCount = bossThreadCount;
        this.workerThreadCount = workerThreadCount;
        this.host = host;
        this.port = port;
        this.workerBackLogSize = workerBackLogSize;
        this.msgProcessor = msgProcessor;

        bossGroup = new NioEventLoopGroup(this.bossThreadCount);
        workerGroup = new NioEventLoopGroup(this.workerThreadCount);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8), new RequestDecoder())
                                .addLast(new LengthFieldPrepender(8), new ResponseEncoder())
                                .addLast(msgProcessor);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, this.workerBackLogSize)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // Bind and start to accept incoming connections.
        ChannelFuture f = bootstrap.bind(this.host, this.port).sync();
        channel = f.channel();
    }

    @Override
    public void close() {
        channel.close();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
